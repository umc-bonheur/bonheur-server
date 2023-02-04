package com.bonheur.domain.common.exception;

import com.bonheur.domain.common.exception.dto.ErrorCode;

public class NotFoundException extends BonheurBaseException {

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotFoundException(String message) {
        super(message, ErrorCode.E404_NOT_EXISTS);
    }

}
