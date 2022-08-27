package com.example.fastcampusmysql.application.usacase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetFollowingMembersUsacase {
    final private MemberReadService memberReadService;
    final private FollowReadService followReadService;

    public List<Member> execute(Long memberId) {
        var follows = followReadService.getFollowings(memberId);
        var memberIds = follows
                .stream()
                .map(Follow::getToMemberId)
                .collect(Collectors.toList());
        return memberReadService.getMembers(memberIds);
    }
}
