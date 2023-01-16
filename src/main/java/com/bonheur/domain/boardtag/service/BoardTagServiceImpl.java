package com.bonheur.domain.boardtag.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.boardtag.repository.BoardTagRepository;
import com.bonheur.domain.tag.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardTagServiceImpl implements BoardTagService{
    private final BoardTagRepository boardTagRepository;

    @Override
    @Transactional
    public List<Long> createBoardTags(Board board, List<Tag> tags){
        List<Long> boardTagsId = new ArrayList<>();
        for(Tag tag : tags){
            boardTagsId.add(boardTagRepository.save(BoardTag.newBoardTag(board, tag)).getId());
        }
        return boardTagsId;
    }
}
