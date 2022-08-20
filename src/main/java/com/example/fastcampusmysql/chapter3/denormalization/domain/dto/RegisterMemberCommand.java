package com.example.fastcampusmysql.chapter3.denormalization.domain.dto;

import java.time.LocalDate;

public record RegisterMemberCommand(String email, String nickname, LocalDate birthday, Long companyCode) {}
