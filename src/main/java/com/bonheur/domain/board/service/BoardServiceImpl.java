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

import com.bonheur.domain.boardtag.model.BoardTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final TagService tagService;
    private final ImageService imageService;

    // # 게시글 전체 조회
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public Slice<GetBoardsResponse> getAllBoards(Long lastBoardId, Long memberId, Pageable pageable) {
        return boardRepository.findAllWithPaging(lastBoardId, memberId, pageable)
                .map(board -> {
                    if (board.getImages().isEmpty()) {
                        return GetBoardsResponse.withoutImage(board.getContents(), getBoardTagsName(board.getBoardTags()),
                                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));
                    }   else {
                        return GetBoardsResponse.of(board.getContents(), getBoardTagsName(board.getBoardTags()), board.getImages().get(0).getUrl(),
                                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));
                    }
                });
    }

    // # Tag Name을 String List로 받아오기
    @Transactional(readOnly = true)
    public List<String> getBoardTagsName(List<BoardTag> boardTags) {
        List<String> tagsName = new ArrayList<>();

        for (BoardTag tag : boardTags) {
            String tagName = tag.getTag().getName();
            tagsName.add(tagName);
        }

        return tagsName;
    }

    // # 게시글 삭제 (boardTag, Image의 cascadeType을 all로 설정해뒀기 때문에 게시글 삭제 시, boardTags와 images 삭제
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional
    public DeleteBoardResponse deleteBoard(Long memberId, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));
        Long writer = board.getMember().getId();
        if (writer.equals(memberId)) {
            imageService.deleteImagesIns3(board); // s3, 테이블에서 이미지 삭제
            boardRepository.delete(board); // board 삭제

            return DeleteBoardResponse.builder()
                    .boardId(boardId)
                    .build();
        } else {
            return DeleteBoardResponse.builder()
                    .boardId(null)
                    .build();
        }
    }

    // # 게시글 조회 - by 해시태그
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public Slice<GetBoardsResponse> getBoardsByTag(Long lastBoardId, Long memberId, List<Long> tagIds, Pageable pageable) {
        return boardRepository.findByTagWithPaging(lastBoardId, memberId, tagIds, pageable)
                .map(board -> {
                    if (board.getImages().isEmpty()) {
                        return GetBoardsResponse.withoutImage(board.getContents(), getBoardTagsName(board.getBoardTags()),
                                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));
                    }   else {
                        return GetBoardsResponse.of(board.getContents(), getBoardTagsName(board.getBoardTags()), board.getImages().get(0).getUrl(),
                                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));
                    }
                });
    }

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
