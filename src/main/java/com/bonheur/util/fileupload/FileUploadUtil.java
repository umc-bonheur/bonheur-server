package com.bonheur.util.fileupload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadUtil {

    // AWS S3 bucket 이름
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // AWS S3 서버와 통신하는 Client 객체
    private final AmazonS3Client amazonS3Client;


    /**
     * @param category 파일의 유형
     * @param multipartFile 넘겨받은 파일
     * @return 업로드된 파일의 접근 URL
     */
    public String saveUploadFile(String category, MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "파일을 선택해주세요.";
        }

        // 파일명
        String fileName = createFileName(category, multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        // S3에 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /**
     * 파일명 생성
     * @param category 파일의 유형
     * @param originalFileName 파일의 이름
     * @return 작명된 파일 이름
     */
    private String createFileName(String category, String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(".");
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String random = String.valueOf(UUID.randomUUID());

        return category + "/" + fileName + "_" + random + fileExtension;
    }

}
