package com.example.fastcampusmysql.chapter3.denormalization.domain.service;


import com.example.fastcampusmysql.chapter3.denormalization.domain.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.chapter3.denormalization.domain.entity.Member;
import com.example.fastcampusmysql.chapter3.denormalization.domain.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.chapter3.denormalization.domain.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.chapter3.denormalization.domain.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class MemberWriteServiceTest {
    @Autowired
    private MemberWriteService service;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    @DisplayName("회원정보 등록 테스트")
    @Test
    public void testRegister() {
        var command = new RegisterMemberCommand(
                "pnu@fastcampus.com",
                "pnu",
                LocalDate.now(),
                1L
        );

        var member = service.register(command);

        assertEquals(command, member);
    }

    @DisplayName("회원정보 이름 변경 테스트")
    @Test
    public void testChangeName() {
        Member saved = saveMember();
        var expected = "chair";

        service.changeNickname(saved.getId(), expected);

        var result = memberRepository.findById(saved.getId()).orElseThrow();
        Assertions.assertEquals(expected, result.getNickname());
    }

    @DisplayName("회원정보 이름 변경시 변경 이름의 히스토리가 저장된다")
    @Test
    public void testToSaveHistoryWhenChangeName() {
        Member member = saveMember();
        var nameToChange = "chair";

        service.changeNickname(member.getId(), nameToChange);
        var results = memberNicknameHistoryRepository.findAllByMemberId(member.getId());

        Assertions.assertEquals(1, results.size());
        MemberNicknameHistory memberNicknameHistory = results.get(0);
        Assertions.assertEquals(member.getId(), memberNicknameHistory.getMemberId());
        Assertions.assertEquals(nameToChange, memberNicknameHistory.getNickname());
    }


    private Member saveMember() {
        var member = MemberFixtureFactory.create();
        return memberRepository.save(member);
    }

    private void assertEquals(RegisterMemberCommand command, Member member) {
        Assertions.assertNotNull(member.getId());
        Assertions.assertNotNull(member.getCreatedAt());

        Assertions.assertEquals(command.email(), member.getEmail());
        Assertions.assertEquals(command.nickname(), member.getNickname());
        Assertions.assertEquals(command.birthday(), member.getBirthday());
    }

}