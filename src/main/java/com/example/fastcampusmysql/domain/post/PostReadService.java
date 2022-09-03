package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

}
