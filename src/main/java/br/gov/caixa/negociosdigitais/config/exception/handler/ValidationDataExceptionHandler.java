package br.gov.caixa.negociosdigitais.config.exception.handler;

import br.gov.caixa.negociosdigitais.config.exception.EnumBusinessExceptionMessage;
import br.gov.caixa.negociosdigitais.model.resource.out.ResponseError;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.atomic.AtomicReference;

@Provider
public class ValidationDataExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {

        ResponseError respError = new ResponseError();
        respError.setCode(Integer.parseInt(EnumBusinessExceptionMessage.BAD_REQUEST.getCode()));
        respError.setTitle(EnumBusinessExceptionMessage.BAD_REQUEST.getTitle());

        StringBuilder sbDetail = new StringBuilder();
        AtomicReference<String> field = new AtomicReference<>("");
        e.getConstraintViolations().forEach(constraintViolation -> {

            for (Path.Node path : constraintViolation.getPropertyPath()) {
                field.set(path.getName());
            }

            sbDetail.append(field.get()).append(": ")
                    .append(constraintViolation.getMessage()).append(". ");
        });

        respError.setDetail(sbDetail.toString().trim());

        return Response
                .status(respError.getCode())
                .entity(respError)
                .build();
    }
}
