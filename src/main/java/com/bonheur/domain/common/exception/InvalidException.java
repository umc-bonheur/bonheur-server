package com.bonheur.domain.common.exception;

import com.bonheur.domain.common.exception.dto.ErrorCode;

public class InvalidException extends BonheurBaseException {

    public InvalidException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InvalidException(String message) {
        super(message, ErrorCode.E400_INVALID);
    }

}