package com.bonheur.domain.boardtag.service;
import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.tag.model.Tag;

import java.util.List;

public interface BoardTagService {
    List<Long> createBoardTags(Board board, List<Tag> tags);
}
