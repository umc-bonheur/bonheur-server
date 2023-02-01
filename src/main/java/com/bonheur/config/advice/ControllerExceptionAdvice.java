package com.bonheur.config.advice;

import com.bonheur.domain.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E400_INVALID_FILE_SIZE_TOO_LARGE;
import static com.bonheur.domain.common.exception.dto.ErrorCode.E500_INTERNAL_SERVER;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     *  최대 허용한 파일 크기 초과하는 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    private ApiResponse<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(E400_INVALID_FILE_SIZE_TOO_LARGE);
    }


    /**
     * 500 Internal Server
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ApiResponse<Object> handleException(Exception exception, HttpServletRequest request) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(E500_INTERNAL_SERVER);
    }
}
