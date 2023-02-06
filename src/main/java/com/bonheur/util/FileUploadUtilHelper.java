package com.bonheur.util;

import com.bonheur.domain.common.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E400_MISSING_FILE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUploadUtilHelper {
    static void validateFileExists(MultipartFile multipartFile){
        if (multipartFile.isEmpty()) {
            throw new NotFoundException("파일이 없습니다. 파일을 첨부해주세요.", E400_MISSING_FILE);
        }
    }
}
