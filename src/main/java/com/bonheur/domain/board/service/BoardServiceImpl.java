package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.image.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long create(CreateBoardRequest createBoardReq, List<Image> images, List<BoardTag> tags){
        // 파일 처리를 위한 Board 객체 생성
        Board board = new Board(
                createBoardReq.getContents(),
                createBoardReq.getMember()
        );

        //List<Image> imageList =

        return boardRepository.save(board).getId();
    }
}
