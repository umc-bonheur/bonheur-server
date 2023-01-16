package com.bonheur.domain.image.service;

import com.bonheur.domain.file.dto.FileUploadResponse;
import com.bonheur.domain.image.model.Image;
import com.bonheur.util.FileUploadUtil;
import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    private final FileUploadUtil fileUploadUtil;

    //이미지 생성
    @Override
    @Transactional
    public void upLoadImages(Board board, List<MultipartFile> images) throws IOException {
        Long order = 1L;
        for(MultipartFile image : images){
            if(!image.isEmpty()){
                FileUploadResponse fileUploadResponse = fileUploadUtil.uploadFile("image", image);
                imageRepository.save(Image.newImage(fileUploadResponse.getFileUrl(),fileUploadResponse.getFilePath(), order, board));
                order+=1;
            }
        }
    }
}
