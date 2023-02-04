package com.bonheur.domain.common.exception;

import com.bonheur.domain.common.exception.dto.ErrorCode;

public class NotImplementedException extends BonheurBaseException {

    public NotImplementedException(String message) {
        super(message, ErrorCode.E501_NOT_SUPPORTED);
    }

    public NotImplementedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
