package br.com.project.config.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final EnumBusinessExceptionMessage statusMessage;

    private final String msgm;

    public BusinessException(EnumBusinessExceptionMessage statusMessage) {
        super(statusMessage.getTitle());
        this.statusMessage = statusMessage;
        this.msgm = "";
    }

    public BusinessException(EnumBusinessExceptionMessage statusMessage, String msgm) {
        super(statusMessage.getTitle());
        this.statusMessage = statusMessage;
        this.msgm = msgm;
    }

    public EnumBusinessExceptionMessage getStatusMessage() {
        return statusMessage;
    }

}
