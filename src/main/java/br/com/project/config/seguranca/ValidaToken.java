package br.com.project.config.seguranca;


import br.com.project.config.exception.BusinessException;
import br.com.project.config.exception.EnumBusinessExceptionMessage;
import br.com.project.utils.JwtUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ContainerRequestContext;
import java.util.Optional;

@Slf4j
public class ValidaToken {

    private ValidaToken() {
    }

    public static void valida(ContainerRequestContext request) {
        Optional<String> authorization = Optional.ofNullable(request.getHeaderString("Authorization"));

        if (authorization.isPresent()) {
            validateToken(authorization.get(), request);
        }
        throw new BusinessException(EnumBusinessExceptionMessage.UNAUTHORIZED);
    }

    private static void validateToken(String authorization, ContainerRequestContext request) {
        String token = authorization.replace("Bearer", "").trim();
        try {
            if (!JwtUtil.validaToken(token)) {
                throw new BusinessException(EnumBusinessExceptionMessage.UNAUTHORIZED);
            }
        } catch (JWTVerificationException e) {
            log.error("client-id não possui permissão ", e);
            throw new BusinessException(EnumBusinessExceptionMessage.FORBIDDEN);
        } catch (Exception e) {
            log.error("Não foi possível validar o token ", e);
            throw new BusinessException(EnumBusinessExceptionMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
