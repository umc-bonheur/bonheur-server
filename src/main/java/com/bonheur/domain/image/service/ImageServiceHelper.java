package com.bonheur.domain.image.service;

import com.bonheur.domain.common.exception.InvalidException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bonheur.domain.common.exception.dto.ErrorCode.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageServiceHelper {
    public static void validateImageCount(List<MultipartFile> images, Long limit) {
        Long imagesCount = images.stream().filter(image -> !image.isEmpty()).count();
        if(imagesCount > limit){
            throw new InvalidException("업로드 가능한 파일 개수를 초과했습니다", E400_INVALID_FILE_COUNT_TOO_MANY);
        }
    }
}
