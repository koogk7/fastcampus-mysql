package com.example.fastcampusmysql.chapter3.denormalization.domain.service;


import com.example.fastcampusmysql.chapter3.denormalization.domain.RegisterMemberCommand;
import com.example.fastcampusmysql.chapter3.denormalization.domain.entity.Member;
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

    @DisplayName("회원정보 등록 테스트")
    @Test
    public void testRegister() {
        var command = new RegisterMemberCommand(
                "pnu@fastcampus.com",
                "pnu",
                LocalDate.now()
        );

        var member = service.register(command);

        assertEquals(command, member);
    }

    private void assertEquals(RegisterMemberCommand command, Member member) {
        Assertions.assertNotNull(member.getId());
        Assertions.assertNotNull(member.getCreatedAt());

        Assertions.assertEquals(command.email(), member.getEmail());
        Assertions.assertEquals(command.nickname(), member.getNickname());
        Assertions.assertEquals(command.birthday(), member.getBirthday());
    }

}