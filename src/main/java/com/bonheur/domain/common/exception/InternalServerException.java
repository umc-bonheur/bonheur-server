package com.bonheur.domain.common.exception;

import com.bonheur.domain.common.exception.dto.ErrorCode;

public class InternalServerException extends BonheurBaseException {

    public InternalServerException(String message) {
        super(message, ErrorCode.E500_INTERNAL_SERVER);
    }

    public InternalServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
