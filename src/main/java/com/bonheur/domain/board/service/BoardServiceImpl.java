package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.image.model.Image;
import com.bonheur.domain.image.repository.ImageRepository;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    //게시글 생성
    @Transactional
    @Override
    public CreateBoardResponse create(CreateBoardRequest createBoardReq, List<Image> images, List<BoardTag> tags){
        // 파일 처리를 위한 Board 객체 생성
        Long id = createBoardReq.getMember_id();
        Member member = memberRepository.findMemberById(id);

        Board board = new Board(
                createBoardReq.getContents(),
                member
        );

        Long boardId = boardRepository.save(board).getId();

        if(!images.isEmpty()){

        }

        if(!tags.isEmpty()){

        }

        CreateBoardResponse createBoardResponse = new CreateBoardResponse().builder()
                .boardId(id)
                .build();


        return createBoardResponse;
    }
}
