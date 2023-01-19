package br.gov.caixa.negociosdigitais.utils;

import br.gov.caixa.negociosdigitais.config.exception.BusinessException;
import br.gov.caixa.negociosdigitais.config.exception.EnumBusinessExceptionMessage;
import br.gov.caixa.negociosdigitais.utils.enums.Operation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
public class OperationSQL {

    static final ObjectMapper objectMapper = new ObjectMapper();

    private OperationSQL() {
    }

    public static String queryBuild(String field, Operation operation) {
        var optKey = Optional.ofNullable(field);
        var optOperation = Optional.ofNullable(operation);

        if (!optKey.isPresent() || !optOperation.isPresent()) {
            log.error("filter params não presentes");
            throw new BusinessException(EnumBusinessExceptionMessage.BAD_REQUEST, "Campos de filter com problema");
        }

        String formato = "%s %s ?1";
        switch (operation) {
            case EQ:
                return String.format(formato, field, "=");
            case LIKE:
                return String.format(formato, "upper(" + field + ")", "LIKE");
        }
        return null;
    }

    public static Object paramsBuild(Class entity, String field, String value, Operation operation) {

        var optValue = Optional.ofNullable(value);
        if (!optValue.isPresent()) {
            log.error("filter params não presentes");
            throw new BusinessException(EnumBusinessExceptionMessage.BAD_REQUEST, "Campos de filter com problema");
        }

        var object = reflectionFieldObject(entity, field, optValue.get());

        switch (operation) {
            case EQ:
                return object;
            case LIKE:
                if (object instanceof String) {
                    return "%" + optValue.get().toUpperCase() + "%";
                }
                throw new BusinessException(EnumBusinessExceptionMessage.BAD_REQUEST, "Parametro inválido para operação " + Operation.LIKE + ". Somente campos do tipo caractere");
        }
        return null;
    }

    private static Object reflectionFieldObject(Class entity, String fieldName, String value) {
        try {
            var mapa = new HashMap();
            mapa.put(fieldName, value);
            var pessoas = objectMapper.convertValue(mapa, entity);

            Field field = pessoas.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(pessoas);
        } catch (Exception e) {
            log.error("converter information ", e);
            throw new BusinessException(EnumBusinessExceptionMessage.INTERNAL_SERVER_ERROR);
        }
    }

}
