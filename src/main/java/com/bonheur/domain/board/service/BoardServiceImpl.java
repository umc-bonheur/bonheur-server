package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.*;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.image.model.Image;
import com.bonheur.domain.image.service.ImageService;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final TagService tagService;
    private final ImageService imageService;

    //게시글 생성
    @Override
    @Transactional
    public CreateBoardResponse createBoard(Long memberId, CreateBoardRequest request, List<MultipartFile> images) throws IOException {
        Member member = memberRepository.findMemberById(memberId);

        Board requestBoard = request.toEntity(member);
        Board board = boardRepository.save(requestBoard);
        if (request.getTags() != null) {
            tagService.createBoardTags(board, request.getTags());
        }
        if (!images.isEmpty()) {
            imageService.uploadImages(board, images);
        }
        return CreateBoardResponse.newResponse(board.getId());
    }

    @Override
    @Transactional
    public UpdateBoardResponse updateBoard(Long boardId, UpdateBoardRequest request, List<MultipartFile> images) throws IOException{
        Optional<Board> board = boardRepository.findById(boardId);

        if(board.isPresent()){
            if(!request.getContents().isEmpty()){
                board.get().update(request.getContents());
            }   //게시글 수정

            tagService.updateBoardTags(board.get(), request.getTags()); //게시글 태그 수정

            imageService.updateImages(board.get(), images); //이미지 수정
        }
        return UpdateBoardResponse.newResponse(boardId);
    }



    @Override
    public GetBoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findBoardByIdWithTagAndImage(boardId);
        return GetBoardResponse.of(board.getId(), board.getContents(),
                board.getImages().stream().map(Image::getUrl).collect(Collectors.toList()),
                board.getBoardTags().stream().map(boardTag -> boardTag.getTag().getName()).collect(Collectors.toList()), board.getCreatedAt().toString());
    }
}
