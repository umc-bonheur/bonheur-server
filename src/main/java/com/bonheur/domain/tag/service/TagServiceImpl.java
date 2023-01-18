package com.bonheur.domain.tag.service;

import com.bonheur.domain.board.model.dto.DeleteBoardResponse;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.boardtag.repository.BoardTagRepository;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;

    // 게시글 삭제를 하고 나서 boardTag를 불러와서 tag_id가 없는 거면
    // 그냥 그 태그를 지우는 걸로

    @Override
    @Transactional
    public List<DeleteBoardResponse> deleteTags() {
        List<Tag> tags = tagRepository.findAll();
        List<BoardTag> boardTags = boardTagRepository.findAll();
        List<Long> tagId = new ArrayList<>();
        List<DeleteBoardResponse> deleteBoardResponses = new ArrayList<>();

        // boardTag에 있는 tag id 받아오기
        for (BoardTag boardTag : boardTags) {
            tagId.add(boardTag.getTag().getId());
        }

        for (Tag tag : tags) {
            if (boardTagRepository.findAllByTag(tag).isEmpty()) {
                deleteBoardResponses.add(new DeleteBoardResponse(tag.getName()));
                tagRepository.delete(tag);
            }
        }
        return deleteBoardResponses;
    }

}
