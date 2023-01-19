/*
 * Fóton Informática S.A.
 * Criação : 14 de mar. de 2021
 */

package br.gov.caixa.negociosdigitais.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumBusinessExceptionMessage {

    BAD_REQUEST("400", "Bad Request", ConstantExceptionMessage.BAD_REQUEST_400),
    BAD_REQUEST_HTML_INJECTION("400", "Bad Request", ConstantExceptionMessage.BAD_REQUEST_HTML_INJECTION_400),
    UNAUTHORIZED("401", "Unauthorized", ConstantExceptionMessage.UNAUTHORIZED_401),
    FORBIDDEN("403", "Forbidden", ConstantExceptionMessage.FORBIDDEN_403),
    NOT_FOUND("404", "Not Found", ConstantExceptionMessage.NOT_FOUND_404),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed", ConstantExceptionMessage.METHOD_NOT_ALLOWED_405),
    NOT_ACCEPTABLE("406", "Not Acceptable", ConstantExceptionMessage.NOT_ACCEPTABLE_406),
    GONE("410", "Gone", ConstantExceptionMessage.GONE_410),
    UNSUPPORTED_MEDIA_TYPE("415", "Unsupported Media Type", ConstantExceptionMessage.UNSUPPORTED_MEDIA_TYPE_415),
    UNPROCESSABLE_ENTITY("422", "Unprocessable Entity", ConstantExceptionMessage.UNPROCESSABLE_ENTITY_422),
    TOO_MANY_REQUESTS("429", "Too Many Requests", ConstantExceptionMessage.TOO_MANY_REQUESTS_429),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error", ConstantExceptionMessage.INTERNAL_SERVER_ERROR_500),
    SEVICE_UNAVAILABLE("503", "Service Unavailable", ConstantExceptionMessage.SEVICE_UNAVAILABLE_503),
    SEVICE_UNAVAILABLE_DATABASE("503", "Service Unavailable", ConstantExceptionMessage.SEVICE_UNAVAILABLE_DATABASE_503),
    GATEWAY_TIMEOUT("504", "Gateway Timeout", ConstantExceptionMessage.GATEWAY_TIMEOUT_504);

    private String code;
    private String title;
    private String detail;

}
