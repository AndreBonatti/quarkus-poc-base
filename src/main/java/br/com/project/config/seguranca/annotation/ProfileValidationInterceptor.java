package br.com.project.config.seguranca.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.security.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.json.JsonObject;
import java.util.Arrays;

@Slf4j
@ProfileValidation
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ProfileValidationInterceptor {

    @Inject
    JsonWebToken jwt;

    ObjectMapper objectMapper = new ObjectMapper();

    @AroundInvoke
    public Object validateProfile(InvocationContext context) throws Exception {
        String[] validProfiles = context.getMethod().getAnnotation(ProfileValidation.class).value();

        log.info("PERFIS VALIDOS {}", Arrays.toString(validProfiles));

        boolean isValidProfile = false;
        try {

            JsonObject realms = jwt.getClaim("realm_access");
            String rolesString = realms.get("roles").asJsonArray().toString();
            String[] roles = objectMapper.readValue(rolesString, String[].class);

            isValidProfile =
                    Arrays.stream(validProfiles)
                            .anyMatch(value -> Arrays.stream(roles)
                                    .anyMatch(value::equals));

            log.info("JWT ACCESS {}", Arrays.toString(roles));

        } catch (Exception ex) {
            log.error("Problema com a roles {}", ex.getMessage());
        }

        if (!isValidProfile) {
            throw new ForbiddenException("Access denied");
        }

        return context.proceed();
    }
}
