package com.bonheur.domain.boardtag.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.boardtag.repository.BoardTagRepository;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardTagServiceImpl implements BoardTagService{
    private final BoardTagRepository boardTagRepository;
    private final TagRepository tagRepository;
    @Override
    @Transactional
    public void createBoardTags(Board board, List<Long> tagsIds){    //게시글과 해시태그 맵핑(연결)
        for(Long tagId : tagsIds) {
            Optional<Tag> tag = tagRepository.findById(tagId);
            tag.orElseThrow(() -> new RuntimeException("존재하지 않은 태그입니다."));
            boardTagRepository.save(BoardTag.newBoardTag(board, tag.get()));
        }
    }

    @Override
    @Transactional
    public void updateBoardTags(Board board, List<Long> tagIds){
        List<BoardTag> boardTags = boardTagRepository.findAllByBoard(board); //기존 게시글 태그들

        if(boardTags != null){
            for(BoardTag boardTag : boardTags){
                boardTagRepository.delete(boardTag);    //기존 게시글 태그 삭제
            }
        }

        if(tagIds != null) {
            createBoardTags(board, tagIds);    //게시글 새로운 태그 추가
        }
    }
}
