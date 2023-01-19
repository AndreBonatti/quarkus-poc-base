package br.gov.caixa.negociosdigitais.config.exception.handler;

import br.gov.caixa.negociosdigitais.config.exception.BusinessException;
import br.gov.caixa.negociosdigitais.model.resource.out.ResponseError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException e) {

        ResponseError respError = new ResponseError();
        respError.setCode(Integer.parseInt(e.getStatusMessage().getCode()));
        respError.setTitle(e.getStatusMessage().getTitle());

        if (e.getMsgm().isEmpty()) {
            respError.setDetail(e.getStatusMessage().getDetail());
        } else {
            respError.setDetail(e.getMsgm());
        }

        return Response
                .status(respError.getCode())
                .entity(respError)
                .build();
    }
}
