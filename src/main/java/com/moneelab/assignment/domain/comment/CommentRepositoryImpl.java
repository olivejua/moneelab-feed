package com.moneelab.assignment.domain.comment;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CommentRepositoryImpl implements CommentRepository {

    /**
     * store: In-memory DB for Comment
     * sequence: To manage auto increment id values
     */
    private static ConcurrentHashMap<Long, Comment> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    /**
     * making it Singleton
     */
    private CommentRepositoryImpl() {}
    private static final CommentRepositoryImpl instance = new CommentRepositoryImpl();

    public static CommentRepositoryImpl getInstance() {
        return instance;
    }

    /**
     * manipulating domain object
     */
    @Override
    public synchronized Long save(Comment comment) {
        comment.initId(++sequence);
        store.put(comment.getId(), comment);

        return comment.getId();
    }

    @Override
    public synchronized void update(Long commentId, String content) {
        Comment comment = store.get(commentId);
        comment.update(content);
    }

    @Override
    public synchronized void deleteById(Long commentId) {
        store.remove(commentId);
    }

    @Override
    public synchronized void deleteByPostId(Long postId) {
        store.values()
                .forEach(comment -> store.remove(comment.getId()));
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return Optional.ofNullable(store.get(commentId));
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return store.values().stream()
                .filter(comment -> comment.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

    @Override
    public void clearAll() {
        store.clear();
    }
}
