package com.example.fastcampusmysql.domain.post;

import java.time.LocalDate;

public record DailyPostCount(Long memberId, LocalDate date, Long postCount) {

}