package com.bonheur.domain.membertag.service;

import com.bonheur.domain.membertag.repository.MemberTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberTagServiceImpl implements MemberTagService {
    private final MemberTagRepository memberTagRepository;
}
