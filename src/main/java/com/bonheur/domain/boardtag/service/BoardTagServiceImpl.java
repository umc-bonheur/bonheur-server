package com.bonheur.domain.boardtag.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.boardtag.repository.BoardTagRepository;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.repository.TagRepository;
import com.bonheur.domain.tag.service.TagServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardTagServiceImpl implements BoardTagService{
    private final BoardTagRepository boardTagRepository;
    private final TagRepository tagRepository;
    @Override
    @Transactional
    public void createBoardTags(Long memberId, Board board, List<Long> tagIds){    //게시글과 해시태그 맵핑(연결)
        for(Long tagId : tagIds) {
            Tag tag = TagServiceHelper.getTagByMemberId(tagRepository, memberId, tagId);
            boardTagRepository.save(BoardTag.newBoardTag(board, tag));
        }
    }

    @Override
    @Transactional
    public void updateBoardTags(Long memberId, Board board, List<Long> tagIds){
        List<BoardTag> boardTags = boardTagRepository.findAllByBoard(board);
        if(!boardTags.isEmpty()){
            boardTagRepository.deleteAllInBatch(boardTags);    //기존 게시글 태그 삭제
        }

        if(!tagIds.isEmpty()) {
            createBoardTags(memberId, board, tagIds);    //게시글 새로운 태그 추가
        }
    }
}