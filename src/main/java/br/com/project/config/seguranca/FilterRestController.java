package br.com.project.config.seguranca;

import br.com.project.config.exception.BusinessException;
import br.com.project.config.exception.EnumBusinessExceptionMessage;
import br.com.project.config.arquitetura.AuditService;
import br.com.project.config.arquitetura.HealthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Slf4j
@Provider
public class FilterRestController implements ContainerRequestFilter, ContainerResponseFilter {

    @Context
    UriInfo info;

    @Inject
    HealthCheckService healthCheckService;

    @Inject
    AuditService auditService;

    @ConfigProperty(name = "audit.status.not.audit")
    Integer[] listStatusNotAudit;

    @ConfigProperty(name = "audit.app.enabled")
    boolean auditAppEnabled;

    @ConfigProperty(name = "html.injection.enabled")
    boolean htmlInjectionEnabled;

    @ConfigProperty(name = "jwt.valida.enabled")
    boolean jwtValidaEnabled;

    @Override
    public void filter(ContainerRequestContext request) {

        request.setProperty("startTime", Instant.now());
        String body = "";
        if (request.hasEntity()) {
            try (InputStream entityStream = request.getEntityStream()) {
                byte[] allBytes = entityStream.readAllBytes();
                body = new String(allBytes, StandardCharsets.UTF_8);
                request.setEntityStream(IOUtils.toInputStream(body, StandardCharsets.UTF_8));
            } catch (Exception e) {
                log.error("Erro leitura body ", e);
            }
        }
        request.setProperty("body", body);

        // ============= VALIDADORES ======================= \\

        // valida se recursos estão disponíveis healthcheck (banco de dados)
        if (!healthCheckService.statusDatabase())
            throw new BusinessException(EnumBusinessExceptionMessage.SEVICE_UNAVAILABLE_DATABASE);

        if (htmlInjectionEnabled) { // Valida dados maliciosos
            HtmlInjection.validation(request);
        }

        if (jwtValidaEnabled) {
            ValidaToken.valida(request);
        }
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {

        // ============= Trilha de auditoria ========== \\
        for (Integer value : listStatusNotAudit) {
            if (response.getStatus() == value) {
                return;
            }
        }

        if (auditAppEnabled) {
            auditService.sendAudit(request, response, info);
        }

    }

}
