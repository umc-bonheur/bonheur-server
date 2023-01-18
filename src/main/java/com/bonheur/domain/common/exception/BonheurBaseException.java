package com.bonheur.domain.common.exception;

import com.bonheur.domain.common.exception.dto.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BonheurBaseException extends RuntimeException{
    private final ErrorCode errorCode;

    protected BonheurBaseException(String message,ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return errorCode.getStatus();
    }
}
