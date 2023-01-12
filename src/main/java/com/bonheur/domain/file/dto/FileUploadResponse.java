package com.bonheur.domain.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이미지 업로드시 반환되는 DTO입니다.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private String fileUrl; // 파일 접근 URL
    private String filePath;  // 삭제 시 필요한 path
}
