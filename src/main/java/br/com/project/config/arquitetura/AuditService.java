package br.com.project.config.arquitetura;

import br.com.project.model.internal.audit.AuditDto;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.UriInfo;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@RequestScoped
public class AuditService {

    public void sendAudit(ContainerRequestContext request, ContainerResponseContext response, UriInfo info) {

        var auditDto =
                AuditDto.builder()
                        .traceId(UUID.randomUUID().toString())
                        .build();

        auditDto.setReqBaseUrl(info.getAbsolutePath().getPath());
        auditDto.setReqHeaders(request.getHeaders());
        auditDto.setReqMethod(request.getMethod());

        String query = request.getUriInfo().getRequestUri().getQuery();
        auditDto.setReqUrl(request.getUriInfo().getPath() + (query != null ? "?" + query : ""));

        Instant startTime = (Instant) request.getProperty("startTime");

        if (startTime != null) {
            auditDto.setReqTime(startTime.toString());
            long duration = Duration.between(startTime, Instant.now()).toMillis();
            auditDto.setReqDuration(duration);
        }

        String body = (String) request.getProperty("body");
        auditDto.setReqBody(body);

        // ============================ \\

        auditDto.setRspStatus(String.valueOf(response.getStatus()));
        auditDto.setRspHeaders(response.getHeaders());
        auditDto.setRspBody(response.getEntity());

        // ============================ \\
        // pode/deve ser enviado para stream/queue para processamento/armazenamento posterior
        log.info(auditDto.toString());
    }
}
