package com.bonheur.domain.member.service;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;
import com.bonheur.domain.member.model.dto.FindByTagResponse;
import com.bonheur.domain.member.model.dto.FindByTimeResponse;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long registerMember(CreateMemberRequest request) {
        if (memberRepository.existMemberBySocialInfo(request.getSocialId(), request.getSocialType())) {
            throw new RuntimeException("이미 가입한 유저 입니다.");
        }
        Member member = memberRepository.save(request.toEntity());
        return member.getId();
    }

    @Override
    @Transactional
    public FindAllMonthlyResponse findAllMonthly(Long memberId) {
        FindAllMonthlyResponse response = memberRepository.findAllMonthly(1L);

        Member findMember = memberRepository.findById(memberId).orElse(null);
        response.updateCountActiveDay(
                ChronoUnit.DAYS.between((findMember.getCreatedAt()), LocalDateTime.now().plusDays(1)));
        return response;
    }

    @Override
    @Transactional
    public List<FindByTagResponse> findByTag(Long memberId) {
        return memberRepository.findByTag(memberId);
    }

    @Override
    @Transactional
    public FindByTimeResponse findByTime(Long memberId) {
        Long morning = memberRepository.findByTime(memberId, "06", "12");
        Long afternoon = memberRepository.findByTime(memberId, "12", "18");
        Long evening = memberRepository.findByTime(memberId, "18", "20");
        Long night = memberRepository.findByTime(memberId, "20", "25");
        Long dawn = memberRepository.findByTime(memberId, "01", "06");

        FindByTimeResponse response = FindByTimeResponse.of(morning,afternoon,evening,night,dawn);
        return response;
    }
}