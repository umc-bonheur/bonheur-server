package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.DeleteBoardResponse;
import com.bonheur.domain.board.model.dto.GetBoardResponse;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.repository.TagRepository;
import com.bonheur.domain.tag.service.TagServiceImpl;
import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import com.bonheur.domain.board.model.dto.UpdateBoardRequest;
import com.bonheur.domain.board.model.dto.UpdateBoardResponse;
import com.bonheur.domain.image.service.ImageService;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final TagServiceImpl tagService;
    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final TagRepository tagRepository;

    // # 게시글 전체 조회
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public Slice<GetBoardResponse> getAllBoards(Long lastBoardId, Long memberId, Pageable pageable) {
        return boardRepository.findAllWithPaging(lastBoardId, memberId, pageable)
                .map(board -> {
                    if (board.getImages().isEmpty()) {
                        return GetBoardResponse.withoutImage(board.getContents(), getBoardTagsName(board.getBoardTags()));
                    }   else {
                        return GetBoardResponse.of(board.getContents(), getBoardTagsName(board.getBoardTags()), board.getImages().get(0).getUrl());
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
        if (writer == memberId) {
            boardRepository.delete(board);
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
    public Slice<GetBoardResponse> getBoardsByTag(Long lastBoardId, Long memberId, String tagName, Pageable pageable) {
        // tagName에 해당하는 tagId 받아오기
        Tag tag = tagRepository.findTagByName(tagName).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 태그입니다."));
        Long tagId = tag.getId();

        return boardRepository.findByTagWithPaging(lastBoardId, memberId, tagId, pageable)
                .map(board -> {
                    if (board.getImages().isEmpty()) {
                        return GetBoardResponse.withoutImage(board.getContents(), getBoardTagsName(board.getBoardTags()));
                    }   else {
                        return GetBoardResponse.of(board.getContents(), getBoardTagsName(board.getBoardTags()), board.getImages().get(0).getUrl());
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
}
