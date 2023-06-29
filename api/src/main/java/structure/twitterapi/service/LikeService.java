package structure.twitterapi.service;

import structure.twitterapi.model.Like;

import java.util.Optional;

public interface LikeService {
    Like save(Like like);

    void delete(Like like);

    Optional<Like> findByUserAndPost(Long userId, Long postId);
}
