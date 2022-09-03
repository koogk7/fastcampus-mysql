package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostWriteService {
    final private PostRepository postRepository;

    public void create(PostCommand command) {

    }

    public void bulkCreate() {

    }
}
