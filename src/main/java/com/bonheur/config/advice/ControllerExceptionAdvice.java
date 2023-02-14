package com.bonheur.config.advice;

import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.common.exception.BonheurBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;

import static com.bonheur.domain.common.exception.dto.ErrorCode.*;
import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400 BadRequest
     * Dto Validation & Binding
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    private ApiResponse<Object> handleBadRequest(BindException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(joining("\n"));
        log.error("BindException: {}", errorMessage);
        return ApiResponse.error(E400_INVALID, errorMessage);
    }

    /**
     * 400 BadRequest
     * RequestParam 필수 필드가 입력되지 않은 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private ApiResponse<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn(e.getMessage());
        return ApiResponse.error(E400_MISSING_PARAMETER, String.format("필수 파라미터 (%s)를 입력해주세요", e.getParameterName()));
    }

    /**
     * 400 BadRequest
     * RequestPart 필수 필드가 입력되지 않은 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    private ApiResponse<Object> handleMissingServletRequestParameterException(MissingServletRequestPartException e) {
        log.warn(e.getMessage());
        return ApiResponse.error(E400_MISSING_PARAMETER, String.format("Multipart (%s)를 입력해주세요", e.getRequestPartName()));
    }

    /**
     * 400 BadRequest
     * 필수 Path Variable 가 입력되지 않은 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    private ApiResponse<Object> handleMissingPathVariableException(MissingPathVariableException e) {
        log.warn(e.getMessage());
        return ApiResponse.error(E400_MISSING_PARAMETER, String.format("Path (%s)를 입력해주세요", e.getVariableName()));
    }

    /**
     * 400 BAD_REQUEST
     *  최대 허용한 파일 크기 초과하는 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    private ApiResponse<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(E400_INVALID_FILE_SIZE_TOO_LARGE);
    }

    /**
     * 405 Method Not Allowed
     * 잘못된 HTTP method 호출 할 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ApiResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn(e.getMessage());
        return ApiResponse.error(E405_METHOD_NOT_ALLOWED);
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

    /**
     * Bonheur Custom Exception
     */
    @ExceptionHandler(BonheurBaseException.class)
    private ResponseEntity<ApiResponse<Object>> handleBaseException(BonheurBaseException exception, HttpServletRequest request) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(exception.getStatus())
                .body(ApiResponse.error(exception.getErrorCode()));
    }
}
