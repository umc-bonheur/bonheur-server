package com.bonheur.domain.common.exception;

import com.bonheur.domain.common.exception.dto.ErrorCode;

public class ForbiddenException extends BonheurBaseException {

    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ForbiddenException(String message) {
        super(message, ErrorCode.E403_FORBIDDEN);
    }

}
