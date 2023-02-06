package com.bonheur.util;

import com.bonheur.domain.common.exception.InvalidException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E400_INVALID_UPLOAD_FILE_EXTENSION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUploadUtilHelper {

    static void validateFileExtension(String category, String fileContentType) {
        if (category.equals("image") && !fileContentType.startsWith("image")){
            throw new InvalidException("파일 확장자를 확인해주세요.", E400_INVALID_UPLOAD_FILE_EXTENSION);
        }
    }
}
