package com.bonheur.domain.mypage.controller;

import com.bonheur.domain.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MyPageController {
    private final MyPageService myPageService;
}
