package com.bonheur.domain.image.service;

import com.bonheur.domain.file.dto.FileUploadResponse;
import com.bonheur.domain.image.model.Image;
import com.bonheur.util.FileUploadUtil;
import com.bonheur.domain.board.model.Board;
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
    public void uploadImages(Board board, List<MultipartFile> images) throws IOException {
        Long order = 1L;    //이미지 순서
        for(MultipartFile image : images){
            if(!image.isEmpty()){
                FileUploadResponse fileUploadResponse = fileUploadUtil.uploadFile("image", image);  //s3에 이미지 업로드
                imageRepository.save(Image.newImage(fileUploadResponse.getFileUrl(),fileUploadResponse.getFilePath(), order, board));
                order+=1;
            }
        }
    }

    @Override
    @Transactional
    public void updateImages(Board board, List<MultipartFile> images) throws IOException{
        List<Image> oldImages = imageRepository.findAllByBoard_Id(board.getId()); //기존 이미지 테이블에 있는 게시글 관련 이미지들

        if(oldImages != null){
            for(Image image : oldImages){
                fileUploadUtil.deleteFile(image.getPath()); //s3에서 기존의 이미지 삭제
                imageRepository.delete(image);          //기존의 이미지 삭제
            }
        }
        uploadImages(board, images);            //새로운 이미지 업로드
    }
}
