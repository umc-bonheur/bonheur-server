package com.bonheur.domain.tag.service;

import com.bonheur.domain.board.model.dto.DeleteBoardResponse;

import java.util.List;

public interface TagService {
    public List<DeleteBoardResponse> deleteTags();
}
