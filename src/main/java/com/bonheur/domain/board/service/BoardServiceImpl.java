package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.*;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.service.BoardTagService;
import com.bonheur.domain.image.model.Image;
import com.bonheur.domain.image.service.ImageService;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardTagService boardTagService;
    private final ImageService imageService;

    //게시글 생성
    @Override
    @Transactional
    public CreateBoardResponse createBoard(Long memberId, CreateBoardRequest request, List<MultipartFile> images) throws IOException {
        Member member = memberRepository.findMemberById(memberId);
        Board board = boardRepository.save(request.toEntity(member));

        if (!request.getTagIds().isEmpty()) {
            boardTagService.createBoardTags(board, request.getTagIds());   //게시글과 해시태그 연결
        }
        imageService.uploadImages(board, images);
        return CreateBoardResponse.of(board.getId());
    }

    @Override
    @Transactional
    public UpdateBoardResponse updateBoard(Long boardId, UpdateBoardRequest request, List<MultipartFile> images) throws IOException{
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("존재하지 않은 게시글입니다."));
        board.update(request.getContents());    //게시글 수정
        boardTagService.updateBoardTags(board, request.getTagIds()); //게시글 태그 수정
        imageService.updateImages(board, images); //이미지 수정
        return UpdateBoardResponse.of(boardId);
    }

    @Override
    @Transactional
    public GetBoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findBoardByIdWithTagAndImage(boardId);
        return GetBoardResponse.of(board.getId(), board.getContents(),
                board.getImages().stream().map(Image::getUrl).collect(Collectors.toList()),
                board.getBoardTags().stream().map(boardTag -> boardTag.getTag().getName()).collect(Collectors.toList()), board.getCreatedAt().toString());
    }
}
