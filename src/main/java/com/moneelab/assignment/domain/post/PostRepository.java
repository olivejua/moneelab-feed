package com.moneelab.assignment.domain.post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    void update(Post post);
    void deleteById(Long postId);
    Post findById(Long postId);
    List<Post> findAll();
    void clearAll();
}