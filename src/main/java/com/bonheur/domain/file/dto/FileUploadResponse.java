package com.bonheur.domain.file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileUploadResponse {
    private String fileUrl; // 파일 접근 URL
    private String filePath;  // 삭제 시 필요한 path
}
