package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.IntegrationTest;
import com.example.fastcampusmysql.factory.MemberFixtureFactory;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@IntegrationTest
class MemberReadServiceTest {
    @Autowired
    private MemberReadService service;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 조회 테스트")
    @Test
    public void testGetMember() {
        var member = MemberFixtureFactory.create();
        var id = memberRepository.save(member).getId();

        var result = service.getMember(id);

        Assertions.assertEquals(id, result.id());
    }


    @DisplayName("회원 조회 실패")
    @Test
    public void testNotFound() {
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.getMember(-1L)
        );
    }

}