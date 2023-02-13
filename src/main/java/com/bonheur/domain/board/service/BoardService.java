package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BoardService {
    Slice<GetBoardsResponse> getAllBoards(Long memberId, GetBoardsRequest request, Pageable pageable);

    DeleteBoardResponse deleteBoard(Long memberId, Long boardId);

    Slice<GetBoardsResponse> getBoardsByTag(Long memberId, GetBoardsRequest getBoardsRequest, GetBoardByTagRequest tagRequest, Pageable pageable);

    CreateBoardResponse createBoard(Long memberId, CreateBoardRequest request, List<MultipartFile> images) throws IOException;

    UpdateBoardResponse updateBoard(Long memberId, Long boardId, UpdateBoardRequest request, List<MultipartFile> images) throws IOException;

    GetBoardResponse getBoard(Long memberId, Long boardId);

    GetBoardsGroupsResponse getBoardsGroups(Slice<GetBoardsResponse> getBoardsResponseSlice, String orderType);

    List<GetCalendarResponse> getCalendar(Long memberId, int year, int month);

    Slice<GetBoardsResponse> getBoardsByDate(Long memberId, GetBoardsRequest request, String localDate, Pageable pageable);

    Long getNumOfBoardsByDate(Long memberId, String localDate);
}