package com.bonheur.domain.image.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.image.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    //이미지 업로드
    void uploadImages(Board board, List<MultipartFile> images) throws IOException;
    void updateImages(Board board, List<MultipartFile> images) throws IOException;

    List<Image> deleteImagesIns3(Board board);
}