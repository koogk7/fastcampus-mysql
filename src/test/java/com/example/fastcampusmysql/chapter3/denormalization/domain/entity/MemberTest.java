package com.example.fastcampusmysql.chapter3.denormalization.domain.entity;

import com.example.fastcampusmysql.chapter3.denormalization.domain.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다")
    @Test
    public void testChangeNickName() {
        Member member = buildMember("chairman");
        var expected = "cat";

        member.changeNickname(expected);

        Assertions.assertEquals(expected, member.getNickname());
    }

    @DisplayName("회원 닉네임의 길이는 10자를 초과할 수 없다")
    @Test
    public void testNicknameMaxLength() {
        var member = buildMember("super");
        var overMaxLengthName = "superChairman";

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> member.changeNickname(overMaxLengthName)
        );
    }

    private Member buildMember(String nickname) {
        return Member.builder()
                .nickname(nickname)
                .email("pnu@fastcmapus.com")
                .birthday(LocalDate.now())
                .build();
    }
}