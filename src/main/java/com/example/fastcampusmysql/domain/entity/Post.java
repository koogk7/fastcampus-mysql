package com.example.fastcampusmysql.domain.entity;

import java.time.LocalDate;

public class Post {
    final private Long id;

    final private Long memberId;

    private String contents;

    private LocalDate createdDate;

    public Post(Long id, Long memberId, String contents, LocalDate createdDate) {
        this.id = id;
        this.memberId = memberId;
        this.contents = contents;
        this.createdDate = createdDate;
    }
}
