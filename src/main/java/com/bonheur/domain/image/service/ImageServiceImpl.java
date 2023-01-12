package com.bonheur.domain.image.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.image.model.Image;
import com.bonheur.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    private final BoardRepository boardRepository;

    //이미지 생성
    @Transactional
    @Override
    public void createImage(Long boardId, List<Image> images){
        Board board = boardRepository.findBoardById(boardId);

        for(Image image : images){
            Image image1 = new Image().builder()
                    .url(image.getUrl())
                    .order(image.getOrder())
                    .board(board)
                    .build();

            imageRepository.save(image1);
        }
    }
}
