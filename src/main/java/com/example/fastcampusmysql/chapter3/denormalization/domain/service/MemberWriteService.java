package com.example.fastcampusmysql.chapter3.denormalization.domain.service;

import com.example.fastcampusmysql.chapter3.denormalization.domain.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.chapter3.denormalization.domain.entity.Member;
import com.example.fastcampusmysql.chapter3.denormalization.domain.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.chapter3.denormalization.domain.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.chapter3.denormalization.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member register(RegisterMemberCommand command) {
        var member = Member.builder()
                .email(command.email())
                .nickname(command.nickname())
                .birthday(command.birthday())
                .build();
        var savedMember = memberRepository.save(member);

        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname) {
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        var savedMember = memberRepository.save(member);

        saveMemberNicknameHistory(savedMember);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }

}
