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
import com.bonheur.domain.boardtag.model.BoardTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardTagService boardTagService;
    private final ImageService imageService;

    // # 게시글 전체 조회
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public Slice<GetBoardsResponse> getAllBoards(Long lastBoardId, Long memberId, Pageable pageable) {
        return boardRepository.findAllWithPaging(lastBoardId, memberId, pageable)
                .map(board -> GetBoardsResponse.of(board.getContents(), getBoardTagsName(board.getBoardTags()),
                        board.getImages().isEmpty() ? null : board.getImages().get(0).getUrl(),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("a hh:mm").withLocale(Locale.forLanguageTag("en"))))
                );
    }

    @Override
    @Transactional
    // # 날짜별 그룹화
    public GetBoardsGroupsResponse getBoardsGroups(Slice<GetBoardsResponse> getBoardsResponseSlice) {
        List<GetBoardsResponse> getBoardsResponsesOfSlice = getBoardsResponseSlice.getContent();
        return GetBoardsGroupsResponse.of(getBoardsResponsesOfSlice.stream().collect(Collectors.groupingBy(GetBoardsResponse::getCreatedAtDate)));
    }

    // # Tag Name을 String List로 받아오기
    @Transactional(readOnly = true)
    public List<String> getBoardTagsName(List<BoardTag> boardTags) {
        return boardTags.stream().map(
                boardTag -> boardTag.getTag().getName())
                .collect(Collectors.toList());
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

            return DeleteBoardResponse.of(boardId);
        } else {
            return DeleteBoardResponse.of(null);
        }
    }

    // # 게시글 조회 - by 해시태그
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public Slice<GetBoardsResponse> getBoardsByTag(Long lastBoardId, Long memberId, List<Long> tagIds, Pageable pageable) {
        return boardRepository.findByTagWithPaging(lastBoardId, memberId, tagIds, pageable)
                .map(board -> GetBoardsResponse.of(board.getContents(), getBoardTagsName(board.getBoardTags()),
                        board.getImages().isEmpty() ? null : board.getImages().get(0).getUrl(),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("a hh:mm").withLocale(Locale.forLanguageTag("en"))))
                );
    }

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
