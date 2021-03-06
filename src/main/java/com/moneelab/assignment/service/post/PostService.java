package com.moneelab.assignment.service.post;

import com.moneelab.assignment.dto.post.PostRequest;
import com.moneelab.assignment.dto.post.PostResponse;
import com.moneelab.assignment.exception.NotExistException;

import java.util.List;

public interface PostService {
    Long save(PostRequest postRequest, Long authorId) throws NotExistException;

    void update(Long postId, PostRequest postRequest) throws NotExistException;

    void delete(Long postId) throws NotExistException;

    PostResponse findById(Long postId) throws NotExistException;

    List<PostResponse> findAll();
}
