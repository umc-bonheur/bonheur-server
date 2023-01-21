package com.bonheur.domain.tag.service;

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
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;

    @Override
    @Transactional
    public void createBoardTags(Board board, List<String> tags){
        for(String tag : tags) {
            Optional<Tag> oldTag = tagRepository.findTagByName(tag);    //기존 tag 테이블에 동일한 태그가 있는 경우
            if (oldTag.isEmpty()){  //기존 tag 테이블에 동일한 태그가 없는 경우
                Tag tag1 = Tag.newTag(tag);
                tagRepository.save(tag1);
                boardTagRepository.save(BoardTag.newBoardTag(board, tag1));
            }
            else{
                boardTagRepository.save(BoardTag.newBoardTag(board, oldTag.get()));
            }
        }
    }

    @Override
    @Transactional
    public void updateBoardTags(Board board, List<String> boardTagsList){
        List<BoardTag> boardTags = boardTagRepository.findAllByBoard(board); //기존 게시글 태그들

        //기존 게시글 태그 삭제
        if(boardTags != null){
            for(BoardTag boardTag : boardTags){
                boardTagRepository.delete(boardTag);
                if(boardTagRepository.findAllByTag(boardTag.getTag()).isEmpty()){  //태그 테이블에 있는 태그를 사용하는 게시글 태그가 없는 경우
                    tagRepository.delete(boardTag.getTag());
                }
            }
        }

        //새로운 게시글 태그 추가
        if(boardTagsList != null) {
            createBoardTags(board, boardTagsList);
        }
    }
}
