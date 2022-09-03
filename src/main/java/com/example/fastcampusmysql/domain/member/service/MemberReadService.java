package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    private final MemberRepository memberRepository;

    public MemberDto getMember(Long memberId) {
        var member = memberRepository.findById(memberId).orElseThrow();
        return toDto(member);
    }

    public List<MemberDto> getMembers(List<Long> memberIds) {
        var members = memberRepository.findAllByIdIn(memberIds);
        return members.stream()
                .map(this::toDto)
                .toList();
    }

    private MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getNickname(), member.getEmail(), member.getBirthday());
    }
}
