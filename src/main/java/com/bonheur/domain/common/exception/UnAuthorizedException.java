package com.bonheur.domain.common.exception;


import com.bonheur.domain.common.exception.dto.ErrorCode;

public class UnAuthorizedException extends BonheurBaseException {

    public UnAuthorizedException(String message) {
        super(message, ErrorCode.E401_UNAUTHORIZED);
    }

}
