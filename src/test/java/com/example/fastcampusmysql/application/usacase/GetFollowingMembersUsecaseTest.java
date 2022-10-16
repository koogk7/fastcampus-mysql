package com.example.fastcampusmysql.application.usacase;

import com.example.fastcampusmysql.IntegrationTest;
import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.factory.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class GetFollowingMembersUsecaseTest {
    @Autowired
    private GetFollowingMembersUsecase usacase;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowRepository followRepository;

    @DisplayName("팔로잉 회원 목록 조회")
    @Test
    public void testExecute() {
        var follow = Follow
                .builder()
                .fromMemberId(saveMember().getId())
                .toMemberId(saveMember().getId())
                .build();
        followRepository.save(follow);

        var result = usacase.execute(follow.getFromMemberId());
        System.out.println(result);
    }

    private Member saveMember() {
        var member = MemberFixtureFactory.create();
        return memberRepository.save(member);
    }
}
