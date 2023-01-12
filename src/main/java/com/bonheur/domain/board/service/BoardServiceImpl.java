package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.image.service.ImageService;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;

    //게시글 생성
    @Transactional
    @Override
    public Long createBoard(CreateBoardRequest createBoardReq){
        // 파일 처리를 위한 Board 객체 생성
        Long id = createBoardReq.getMember_id();
        Member member = memberRepository.findMemberById(id);

        Board board = Board.builder()
                .contents(createBoardReq.getContents())
                .member(member)
                .build();

        return boardRepository.save(board).getId();
    }
}
