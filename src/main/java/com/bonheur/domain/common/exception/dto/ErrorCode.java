package com.bonheur.domain.common.exception.dto;

import com.bonheur.domain.common.http.HttpStatusCode;
import lombok.Getter;

import static com.bonheur.domain.common.http.HttpStatusCode.*;

@Getter
public enum ErrorCode {
    /**
     * 400 Bad Request (잘못된 요청)
     */
    E400_INVALID(BAD_REQUEST, false, "BR000", "잘못된 요청입니다"),
    E400_INVALID_ENCODING_ID(BAD_REQUEST, false, "BR001", "잘못된 ID가 입력되었습니다"),
    E400_INVALID_AUTH_TOKEN(BAD_REQUEST, false, "BR002", "만료되거나 유효하지 않은 소셜 인증 토큰입니다"),
    E400_INVALID_UPLOAD_FILE_EXTENSION(BAD_REQUEST, false, "BR008", "잘못된 파일 확장자입니다"),
    E400_INVALID_FILE_SIZE_TOO_LARGE(BAD_REQUEST, true, "BR009", "업로드 가능한 파일 크기를 초과했습니다"),
    E400_INVALID_FILE_COUNT_TOO_MANY(BAD_REQUEST, true, "BR010", "업로드 가능한 파일 개수를 초과했습니다"),
    E400_INVALID_FORMAT_ORDER_TYPE(BAD_REQUEST, false, "BR011", "게시글 정렬 순서 입력이 잘못되었습니다."),
    E400_INVALID_LAST_BOARD_ID(BAD_REQUEST, true, "BR012", "더 이상 불러올 글이 없습니다."),

    E400_MISSING_PARAMETER(BAD_REQUEST, false, "BR100", "필수 파라미터가 입력되지 않았습니다"),
    E400_MISSING_AUTH_TOKEN_PARAMETER(BAD_REQUEST, false, "BR105", "인증 토큰을 입력해주세요"),
    E400_MISSING_FILE(BAD_REQUEST, false, "BR106", "파일을 업로드해주세요"),
    E400_MISSING_UPLOAD_FILE_NAME(BAD_REQUEST, false, "BR107", "잘못된 파일입니다\n파일의 이름이 없습니다."),


    /**
     * 401 UnAuthorized (인증 실패)
     */
    E401_UNAUTHORIZED(UNAUTHORIZED, false, "UA000", "세션이 만료되었습니다. 다시 로그인 해주세요"),


    /**
     * 403 Forbidden (권한 등의 이유로 허용하지 않는 요청)
     */
    E403_FORBIDDEN(FORBIDDEN, false, "FB000", "허용하지 않는 요청입니다"),
    E403_FORBIDDEN_BOARD(FORBIDDEN, false, "FB001", "해당 회원이 만든 게시글이 아닙니다"),


    /**
     * 404 Not Found (존재하지 않는 리소스)
     */
    E404_NOT_EXISTS(NOT_FOUND, false, "NF000", "존재하지 않습니다"),
    E404_NOT_EXISTS_MEMBER(NOT_FOUND, false, "NF001", "탈퇴하거나 존재하지 않는 회원입니다"),
    E404_NOT_EXISTS_BOARD(NOT_FOUND, false, "NF002", "존재하지 않는 게시글입니다"),
    E404_NOT_EXISTS_TAG(NOT_FOUND, false, "NF004", "존재하지 않는 태그입니다"),
    E404_NOT_EXISTS_FAQ(NOT_FOUND, false, "NF005", "삭제되거나 존재하지 않는 FAQ입니다"),
    E404_NOT_EXISTS_SIGNUP_REGISTRATION(NOT_FOUND, false, "NF010", "해당하는 가입 신청은 존재하지 않습니다"),
    E404_NOT_EXISTS_ADMIN(NOT_FOUND, false, "NF011", "해당하는 관리자는 존재하지 않습니다"),


    /**
     * 405 Method Not Allowed
     */
    E405_METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, false, "MN000", "허용되지 않은 HTTP 메소드입니다"),


    /**
     * 406 Not Acceptable
     */
    E406_NOT_ACCEPTABLE(NOT_ACCEPTABLE, false, "NA000", "허용되지 않은 Content-Type 입니다"),


    /**
     * 409 Conflict (중복되는 리소스)
     */
    E409_DUPLICATE(CONFLICT, false, "CF000", "이미 존재합니다"),
    E409_DUPLICATE_NICKNAME(CONFLICT, false, "CF001", "이미 사용중인 닉네임입니다.\n다른 닉네임을 이용해주세요"),
    E409_DUPLICATE_MEMBER(CONFLICT, false, "CF002", "이미 회원가입하셨습니다.\n해당 계정으로 로그인 해주세요"),
    E409_DUPLICATE_EMAIL(CONFLICT, false, "CF003", "이미 존재하는 이메일 입니다"),
    E409_ALREADY_CONNECTED_SOCIAL(CONFLICT, false, "CF004", "이미 소셜 계정에 연결된 회원입니다"),


    /**
     * 415 Unsupported Media Type
     */
    E415_UNSUPPORTED_MEDIA_TYPE(UNSUPPORTED_MEDIA_TYPE, false, "UM000", "지원 하지 않는 MediaType 입니다/"),


    /**
     * 429 Too Many Requests (RateLimit)
     */
    E429_TOO_MANY_REQUESTS(TOO_MANY_REQUESTS, true, "TM000", "일시적으로 많은 요청이 들어왔습니다\n잠시후 다시 이용해주세요"),


    /**
     * 500 Internal Server Exception (서버 내부 에러)
     */
    E500_INTERNAL_SERVER(INTERNAL_SERVER, true, "IS000", "예상치 못한 에러가 발생하였습니다\n잠시 후 다시 시도해주세요!"),


    /**
     * 501 Not Implemented (현재 지원하지 않는 요청)
     */
    E501_NOT_SUPPORTED(NOT_IMPLEMENTED, false, "II000", "지원하지 않는 요청입니다"),
    E501_NOT_SUPPORTED_UPLOAD_FILE(NOT_IMPLEMENTED, false, "II001", "해당 서비스에서 업로드할 수 없는 파일 타입 입니다"),
    E501_NOT_SUPPORTED_FAQ_CATEGORY(NOT_IMPLEMENTED, false, "II002", "해당 서비스에서 지원하지 않는 FAQ 카테고리 입니다"),
    E501_NOT_SUPPORTED_PUSH_PLATFORM(NOT_IMPLEMENTED, false, "II004", "해당 디바이스에서 지원하지 않는 푸시 플랫폼 입니다"),


    /**
     * 502 Bad Gateway (외부 시스템의 Bad Gateway)
     */
    E502_BAD_GATEWAY(BAD_GATEWAY, true, "BG000", "일시적인 에러가 발생하였습니다\n잠시 후 다시 시도해주세요!"),


    /**
     * 503 Service UnAvailable
     */
    E503_SERVICE_UNAVAILABLE(SERVICE_UNAVAILABLE, false, "SU000", "해당 기능은 현재 사용할 수 없습니다"),
    E503_SERVICE_UNAVAILABLE_UNDER_INSPECTION(SERVICE_UNAVAILABLE, false, "SU001", "현재 점검중인 기능입니다\n불편을 드려 죄송합니다!"),
    ;

    private final HttpStatusCode statusCode;
    private final boolean sendNotification;
    private final String code;
    private final String message;

    ErrorCode(HttpStatusCode statusCode, boolean sendNotification, String code, String message) {
        this.statusCode = statusCode;
        this.sendNotification = sendNotification;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return statusCode.getStatus();
    }
}