package br.gov.caixa.negociosdigitais.config.exception.handler;

import br.gov.caixa.negociosdigitais.config.exception.EnumBusinessExceptionMessage;
import br.gov.caixa.negociosdigitais.model.resource.out.ResponseError;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class GenericExceptionHandler implements ExceptionMapper<Exception> {


    @Override
    public Response toResponse(Exception e) {

        log.error("Erro tratamento generico ", e);

        Integer code = Integer.parseInt(EnumBusinessExceptionMessage.INTERNAL_SERVER_ERROR.getCode());

        ResponseError respError = new ResponseError();
        respError.setCode(code);
        respError.setTitle(EnumBusinessExceptionMessage.INTERNAL_SERVER_ERROR.getTitle());
        respError.setDetail(EnumBusinessExceptionMessage.INTERNAL_SERVER_ERROR.getDetail());

        return Response
                .status(code)
                .entity(respError)
                .build();

    }
}
