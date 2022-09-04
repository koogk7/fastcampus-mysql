package com.example.fastcampusmysql.factory;

import com.example.fastcampusmysql.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LocalDateRangeRandomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class PostFixtureFactory {
    public static Post create() {
        EasyRandomParameters parameter = getEasyRandomParameters();
        return new EasyRandom(parameter).nextObject(Post.class);
    }

    public static Post create(Long memberId, LocalDate createdDate) {
        EasyRandomParameters parameter = getEasyRandomParameters();
        parameter
                .randomize(memberId(), () -> memberId)
                .randomize(createdDate(), new LocalDateRangeRandomizer(createdDate, createdDate));

        return new EasyRandom(parameter).nextObject(Post.class);
    }

    public static Post create(Long memberId, LocalDate start, LocalDate end) {
        EasyRandomParameters parameter = getEasyRandomParameters();
        parameter
                .randomize(memberId(), () -> memberId)
                .randomize(createdDate(), new LocalDateRangeRandomizer(start, end));

        return new EasyRandom(parameter).nextObject(Post.class);
    }

    public static EasyRandom get(Long memberId, LocalDate start, LocalDate end) {
        EasyRandomParameters parameter = getEasyRandomParameters();
        parameter
                .randomize(memberId(), () -> memberId)
                .randomize(createdDate(), new LocalDateRangeRandomizer(start, end));

        return new EasyRandom(parameter);
    }

    private static EasyRandomParameters getEasyRandomParameters() {
        return new EasyRandomParameters()
                .excludeField(named("id"))
                .stringLengthRange(1, 100)
                .randomize(Long.class, new LongRangeRandomizer(1L, 100000L));
    }

    private static Predicate<Field> memberId() {
        return named("memberId").and(ofType(Long.class)).and(inClass(Post.class));
    }

    private static Predicate<Field> createdDate() {
        return named("createdDate").and(ofType(LocalDate.class)).and(inClass(Post.class));
    }
}
