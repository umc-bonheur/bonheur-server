package com.bonheur.domain.boardtag.controller;

import com.bonheur.domain.boardtag.service.BoardTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardTagController {
    private final BoardTagService boardTagService;
}
