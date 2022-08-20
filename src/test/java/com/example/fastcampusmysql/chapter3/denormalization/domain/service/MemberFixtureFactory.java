package com.example.fastcampusmysql.chapter3.denormalization.domain.service;

import com.example.fastcampusmysql.chapter3.denormalization.domain.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import static org.jeasy.random.FieldPredicates.named;

public class MemberFixtureFactory {
    public static Member create() {
        var parameter = new EasyRandomParameters()
                .excludeField(named("id"))
                .stringLengthRange(1, 10)
                .randomize(Long.class, new LongRangeRandomizer(1L, 100L));
        return new EasyRandom(parameter).nextObject(Member.class);
    }
}