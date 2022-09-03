package com.example.fastcampusmysql.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {
    final private PostRepository postRepository;

    public List<Post> getPosts(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public List<DailyPostCount> getDailyPostCount(Long memberId, LocalDate start, LocalDate end) {
        return postRepository.groupByCreatedDate(new DailyPostCountRequest(memberId, start, end));
    }

    public List<Post> getPosts(Long memberId, LocalDate date) {
        return List.of();
    }

}
