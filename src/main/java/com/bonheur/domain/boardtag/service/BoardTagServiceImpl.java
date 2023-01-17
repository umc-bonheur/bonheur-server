package com.bonheur.domain.boardtag.service;

import com.bonheur.domain.boardtag.repository.BoardTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardTagServiceImpl implements BoardTagService{
    private final BoardTagRepository boardTagRepository;
}
