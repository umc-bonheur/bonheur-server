package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import com.bonheur.domain.board.model.dto.UpdateBoardRequest;
import com.bonheur.domain.board.model.dto.UpdateBoardResponse;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.boardtag.repository.BoardTagRepository;
import com.bonheur.domain.boardtag.service.BoardTagService;
import com.bonheur.domain.image.service.ImageService;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.repository.TagRepository;
import com.bonheur.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardTagRepository boardTagRepository;

    private final TagRepository tagRepository;
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
        return CreateBoardResponse.newResponse(id);
    }

    @Override
    @Transactional
    public UpdateBoardResponse updateBoard(Long boardId, UpdateBoardRequest request, List<MultipartFile> images){
        Optional<Board> board = boardRepository.findById(boardId);
        List<String> boardTagsList = request.getTags();
        LocalDateTime nowTime = LocalDateTime.now();

        if(board.isPresent()){
            if(!request.getContents().isEmpty()){
                board.get().update(request.getContents());
                board.get().updateUpdatedAt(nowTime);   //업데이트 시간 변경
            }   //게시글 수정
            if(boardTagsList != null){
                //해시태그가 기존과 동일한지를 확인한다.
                List<BoardTag> boardTags = boardTagRepository.findAllByBoard_Id(boardId);
                boolean tagCheck = false;   //기존 게시물 태그가 수정하는 태그 목록에 있는 경우 true
                for(BoardTag boardTag : boardTags){
                    if(boardTagsList != null){
                        for(String tags : boardTagsList){
                            if(boardTag.getTag().getName().equals(tags)){   //기존 게시물 태그가 수정하는 태그 목록에 있는 경우
                                tagCheck = true;
                                boardTagsList.remove(tags);
                                break;
                            }
                        }
                        if(!tagCheck){  //기존 게시물 태그가 수정하는 태그에 없는 경우
                            boardTagRepository.deleteById(boardTag.getId());
                            if(boardTagRepository.findAllByTag_Id(boardTag.getTag().getId()).isEmpty()){  //태그 테이블에 있는 태그를 사용하는 게시글 태그가 없는 경우
                                tagRepository.delete(boardTag.getTag());
                            }
                        }
                        tagCheck = false;
                    }
                }
                if(boardTagsList != null){
                    List<Tag> tags = tagService.createTags(boardTagsList);
                    boardTagService.createBoardTags(board.get(), tags);
                }
            }

            //이미지 업로드 수정 미구현
        }
        return UpdateBoardResponse.newResponse(boardId);
    }
}
