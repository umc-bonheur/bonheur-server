package com.bonheur.domain.boardtag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardTagServiceImpl implements BoardTagService{
    private final BoardTagService boardTagService;
}
