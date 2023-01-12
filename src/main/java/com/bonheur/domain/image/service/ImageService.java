package com.bonheur.domain.image.service;

import com.bonheur.domain.image.model.Image;

import java.util.List;

public interface ImageService {
    //이미지 생성
    void createImage(Long boardId, List<Image> images);
}
