package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowWriteService {
    final private FollowRepository followRepository;

    // TODO: Member 엔티티 의존성 제거하기
    public Follow create(Member fromMember, Member toMember) {
        if (fromMember.getId().equals(toMember.getId())) {
            throw new IllegalArgumentException("From, To 회원이 동일합니다");
        }

        var follow = Follow
                .builder()
                .fromMemberId(fromMember.getId())
                .toMemberId(toMember.getId())
                .build();
        return followRepository.save(follow);
    }
}
