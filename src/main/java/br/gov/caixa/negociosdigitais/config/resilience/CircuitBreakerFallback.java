package br.gov.caixa.negociosdigitais.config.resilience;

import br.gov.caixa.negociosdigitais.config.exception.EnumBusinessExceptionMessage;
import br.gov.caixa.negociosdigitais.model.resource.out.ResponseError;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import javax.ws.rs.core.Response;

public class CircuitBreakerFallback implements FallbackHandler<Response> {

    @Override
    public Response handle(ExecutionContext executionContext) {
        Integer code = Integer.parseInt(EnumBusinessExceptionMessage.GATEWAY_TIMEOUT.getCode());
        ResponseError respError = new ResponseError();
        respError.setCode(code);
        respError.setTitle(EnumBusinessExceptionMessage.GATEWAY_TIMEOUT.getTitle());
        respError.setDetail(EnumBusinessExceptionMessage.GATEWAY_TIMEOUT.getDetail());

        return Response
                .status(code)
                .entity(respError)
                .build();
    }
}
