package com.example.fastcampusmysql.chapter3.denormalization.domain;

import java.time.LocalDate;

public record RegisterMemberCommand(String email, String nickname, LocalDate birthday) {}
