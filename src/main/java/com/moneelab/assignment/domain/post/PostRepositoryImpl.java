package com.moneelab.assignment.domain.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PostRepositoryImpl implements PostRepository {

    /**
     * store: In-memory DB for Post
     * sequence: To manage auto increment id values
     */
    private static ConcurrentHashMap<Long, Post> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    /**
     * making it Singleton
     */
    private PostRepositoryImpl() {}
    private static final PostRepositoryImpl instance = new PostRepositoryImpl();

    public static PostRepositoryImpl getInstance() {
        return instance;
    }


    /**
     * manipulating domain object
     */
    public synchronized Long save(Post post) {
        post.initId(++sequence);
        store.put(post.getId(), post);

        return post.getId();
    }

    public synchronized void update(Long postId, String updatedTitle, String updatedContent) {
        Post post = store.get(postId);
        post.update(updatedTitle, updatedContent);
    }

    public synchronized void deleteById(Long postId) {
        store.remove(postId);
    }

    public Optional<Post> findById(Long postId) {
        return Optional.ofNullable(store.get(postId));
    }

    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    public synchronized void clearAll() {
        store.clear();
    }
}
