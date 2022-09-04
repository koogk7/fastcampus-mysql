package com.example.fastcampusmysql.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

public record DailyPostCountRequest (Long memberId, LocalDate firstDate, LocalDate lastDate) {
}
