package com.bonheur.util.fileupload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 파일 업로드 테스트용 컨트롤러
 * - category 파일의 유형을 의미합니다. category를 image로 할 경우 S3 상에서 image 폴더에 들어가게 됩니다.
 * - multipartFile 저장할 파일
 * @return 생성된 파일 URL
 */
@RequiredArgsConstructor
@RestController
public class FileController {
    private final FileUploadUtil fileUploadUtil;

    @PostMapping("/test/file/upload")
    public String uploadFile(@RequestParam("category") String category, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return fileUploadUtil.saveUploadFile(category, multipartFile);
    }
}