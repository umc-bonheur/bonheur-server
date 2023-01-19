package com.bonheur.domain.member.service;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;
import com.bonheur.domain.member.model.dto.FindByTagResponse;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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

        Calendar today = Calendar.getInstance();
        Long dayOfMonth = Long.valueOf(today.getActualMaximum(Calendar.DATE));
        response.updateDayOfMonth(dayOfMonth);

        return response;
    }

    @Override
    @Transactional
    public List<FindByTagResponse> findByTag(Long memberId) {
        return memberRepository.findByTag(memberId);
    }
}
