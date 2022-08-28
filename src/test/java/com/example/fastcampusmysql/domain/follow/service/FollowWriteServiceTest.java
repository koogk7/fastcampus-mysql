package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.IntegrationTest;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.factory.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class FollowWriteServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowWriteService followWriteService;

    @DisplayName("본인 계정을 팔로우 할 수 없다")
    @Test
    public void testSelfFollow() {
        var member = saveMember();

        assertThrows(
                IllegalArgumentException.class,
                () -> followWriteService.create(member, member)
        );
    }

    @DisplayName("팔로우 생성 테스트")
    @Test
    public void testCreate() {
        var fromMember = saveMember();
        var toMember = saveMember();

        var result = followWriteService.create(fromMember, toMember);

        assertNotNull(result.getId());
        assertEquals(fromMember.getId(), result.getFromMemberId());
        assertEquals(toMember.getId(), result.getToMemberId());
    }

    @DisplayName("fromMember, toMember 중복 팔로우 테스트")
    @Test
    public void testDuplicatedFollow() {
        var fromMember = saveMember();
        var toMember = saveMember();

        followWriteService.create(fromMember, toMember);
        assertThrows(
                DuplicateKeyException.class,
                () -> followWriteService.create(fromMember, toMember)
        );
    }

    private Member saveMember() {
        var member = MemberFixtureFactory.create();
        return memberRepository.save(member);
    }
}