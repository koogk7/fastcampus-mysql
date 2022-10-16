package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {
    final private TimelineRepository timelineRepository;

    public void deliveryToTimeLine(Long postId, List<Long> toMemberIds) {
        var timeLines = toMemberIds.stream()
                .map((memberId) -> toTimeLine(postId, memberId))
                .toList();
        timelineRepository.bulkInsert(timeLines);
    }

    private Timeline toTimeLine(Long postId, Long memberId) {
        return Timeline
                .builder()
                .memberId(memberId)
                .postId(postId)
                .build();
    }
}
