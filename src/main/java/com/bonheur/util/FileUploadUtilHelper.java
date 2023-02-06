package com.bonheur.util;

import com.bonheur.domain.common.exception.InvalidException;
import com.bonheur.domain.common.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E400_INVALID_UPLOAD_FILE_EXTENSION;
import static com.bonheur.domain.common.exception.dto.ErrorCode.E400_MISSING_FILE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUploadUtilHelper {

    static void validateFileExists(MultipartFile multipartFile){
        if (multipartFile.equals(null)) {
            throw new NotFoundException("파일을 확인해주세요.", E400_MISSING_FILE);
        }
    }

    static void validateFileExtension(String category, String originalFileName) {
        // 파일명에서 확장자 추출하기
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 이미지를 업로드한 경우
        // - 가능한 확장자 : jpg, png
        if (category.equals("image") && !(fileExtension.equals(".jpg") || fileExtension.equals(".png"))){
            throw new InvalidException("파일 확장자를 확인해주세요.", E400_INVALID_UPLOAD_FILE_EXTENSION);
        }
    }

}
