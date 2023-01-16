package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.service.BoardTagService;
import com.bonheur.domain.image.service.ImageService;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final TagService tagService;
    private final BoardTagService boardTagService;
    private final ImageService imageService;

    //게시글 생성
    @Override
    @Transactional
    public CreateBoardResponse createBoard(CreateBoardRequest request, List<MultipartFile> images) throws IOException {
        Long id = request.getMember_id();   //member id
        Optional<Member> member = memberRepository.findById(id);

        if(member.isPresent()) {
            Board requestBoard = request.toEntity(member.get());
            Board board = boardRepository.save(requestBoard);
            if (request.getTags() != null) {
                List<Tag> tags = tagService.createTags(request.getTags());
                boardTagService.createBoardTags(board, tags);
            }
            if (!images.isEmpty()) {
                imageService.upLoadImages(board, images);
            }
        }
        CreateBoardResponse createBoardResponse = CreateBoardResponse.newResponse(id);

        return createBoardResponse;
    }
}
