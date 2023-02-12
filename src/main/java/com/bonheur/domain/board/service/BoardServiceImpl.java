package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.*;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.service.BoardTagService;
import com.bonheur.domain.common.exception.NotFoundException;
import com.bonheur.domain.image.model.Image;
import com.bonheur.domain.image.service.ImageService;
import com.bonheur.domain.image.service.ImageServiceHelper;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.domain.member.service.MemberServiceHelper;
import com.bonheur.domain.tag.repository.TagRepository;
import com.bonheur.domain.tag.service.TagServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.bonheur.domain.boardtag.model.BoardTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E404_NOT_EXISTS_WRITTEN_BOARD;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardTagService boardTagService;
    private final TagRepository tagRepository;
    private final ImageService imageService;

    // # 게시글 전체 조회
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public Slice<GetBoardsResponse> getAllBoards(Long memberId, GetBoardsRequest request, Pageable pageable) {
        BoardServiceHelper.isValidRequest(memberId, boardRepository, request);

        return boardRepository.findAllWithPaging(request.getLastBoardId(), memberId, request.getOrderType(), pageable)
                .map(board -> GetBoardsResponse.of(board.getId(), board.getContents(), getBoardTagsName(board.getBoardTags()),
                        board.getImages().isEmpty() ? null : board.getImages().get(0).getUrl(),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일")),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("a hh:mm").withLocale(Locale.forLanguageTag("en"))))
                );
    }

    @Override
    @Transactional
    // # 날짜별 그룹화
    public GetBoardsGroupsResponse getBoardsGroups(Slice<GetBoardsResponse> getBoardsResponseSlice, String orderType) {
        List<GetBoardsResponse> getBoardsResponsesOfSlice = getBoardsResponseSlice.getContent();
        return GetBoardsGroupsResponse.of(getBoardsResponsesOfSlice.stream().
                collect(Collectors.groupingBy(GetBoardsResponse::getCreatedAtDate)), orderType, getBoardsResponseSlice.isLast());
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
        Board board = BoardServiceHelper.getExistBoard(boardRepository, boardId);
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
    public Slice<GetBoardsResponse> getBoardsByTag(Long memberId, GetBoardsRequest getBoardsRequest, GetBoardByTagRequest tagRequest, Pageable pageable) {
        BoardServiceHelper.isValidRequest(memberId, boardRepository, getBoardsRequest);
        TagServiceHelper.isExistTag(tagRepository, memberId, tagRequest.getTagIds());

        return boardRepository.findByTagWithPaging(getBoardsRequest.getLastBoardId(), memberId, tagRequest.getTagIds(), getBoardsRequest.getOrderType(), pageable)
                .map(board -> GetBoardsResponse.of(board.getId(), board.getContents(), getBoardTagsName(board.getBoardTags()),
                        board.getImages().isEmpty() ? null : board.getImages().get(0).getUrl(),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일")),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("a hh:mm").withLocale(Locale.forLanguageTag("en"))))
                );
    }

    // # 게시글 조회 - by 날짜
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public Slice<GetBoardsResponse> getBoardsByDate(Long memberId, GetBoardsRequest request, String localDate, Pageable pageable) {
        BoardServiceHelper.isValidRequest(memberId, boardRepository, request);
        LocalDate date = BoardServiceHelper.isValidDateFormat(localDate);

        return boardRepository.findByCreatedAtWithPaging(request.getLastBoardId(), memberId, date, request.getOrderType(), pageable)
                .map(board -> GetBoardsResponse.of(board.getId(), board.getContents(), getBoardTagsName(board.getBoardTags()),
                        board.getImages().isEmpty() ? null : board.getImages().get(0).getUrl(),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일")),
                        board.getCreatedAt().format(DateTimeFormatter.ofPattern("a hh:mm").withLocale(Locale.forLanguageTag("en"))))
                );
    }

    // # 게시글 조회 - by 날짜 count
    @Override
    @Transactional(readOnly = true)
    public Long getNumOfBoardsByDate(Long memberId, String localDate) {
        LocalDate date = BoardServiceHelper.isValidDateFormat(localDate);
        Long numOfBoardsByDate = boardRepository.getNumOfBoardsByDate(memberId, date);
        if (numOfBoardsByDate == 0)
            throw new NotFoundException("작성된 게시글이 없습니다.", E404_NOT_EXISTS_WRITTEN_BOARD);
        return numOfBoardsByDate;
    }

    // # 캘린더 조회
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public List<GetCalendarResponse> getCalendar(Long memberId, int year, int month) {

        // 해당 달의 마지막 날 계산
        int lastDay = BoardServiceHelper.getLastDay(year, month);

        List<GetCalendarResponse> getCalendarList = new ArrayList<>();
        int idx = 0;
        while (++idx <= lastDay) {
            getCalendarList.add(GetCalendarResponse.of(idx, false)); // day : false 로 초기화
        }

        List<Integer> createdDate = boardRepository.getCalendar(memberId, year, month, lastDay); // dd | count(*) 형식

        for (Integer created : createdDate) {
            getCalendarList.set(created - 1, GetCalendarResponse.of(created, true));
        }

        return getCalendarList;
    }

    @Override
    @Transactional
    public CreateBoardResponse createBoard(Long memberId, CreateBoardRequest request, List<MultipartFile> images) throws IOException {
        Member member = MemberServiceHelper.getExistMember(memberRepository, memberId);
        ImageServiceHelper.validateImageCount(images, 5L);
        Board board = boardRepository.save(request.toEntity(member));

        if (!request.getTagIds().isEmpty()) {
            boardTagService.createBoardTags(memberId, board, request.getTagIds());   //게시글과 해시태그 연결
        }
        imageService.uploadImages(board, images);
        return CreateBoardResponse.of(board.getId());
    }

    @Override
    @Transactional
    public UpdateBoardResponse updateBoard(Long memberId, Long boardId, UpdateBoardRequest request, List<MultipartFile> images) throws IOException{
        MemberServiceHelper.validateMemberExists(memberRepository, memberId);
        ImageServiceHelper.validateImageCount(images, 5L);

        Board board = BoardServiceHelper.getBoardByMemberId(memberId, boardRepository, boardId);
        board.update(request.getContents());    //게시글 수정
        boardTagService.updateBoardTags(memberId, board, request.getTagIds()); //게시글 태그 수정
        imageService.updateImages(board, images); //이미지 수정
        return UpdateBoardResponse.of(boardId);
    }

    @Override
    @Transactional
    public GetBoardResponse getBoard(Long memberId, Long boardId) {
        Board board = BoardServiceHelper.findBoardByMemberId(boardRepository, memberId, boardId);
        return GetBoardResponse.of(board.getId(), board.getContents(),
                board.getImages().stream().map(Image::getUrl).collect(Collectors.toList()),
                board.getBoardTags().stream().map(boardTag -> boardTag.getTag().getName()).collect(Collectors.toList()),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd E요일 ").withLocale(Locale.KOREA))
        + board.getCreatedAt().format(DateTimeFormatter.ofPattern("ahh:mm").withLocale(Locale.ENGLISH)));
    }
}
