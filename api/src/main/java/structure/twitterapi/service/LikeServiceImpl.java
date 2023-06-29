package structure.twitterapi.service;

import org.springframework.stereotype.Repository;
import structure.twitterapi.model.Like;
import structure.twitterapi.repository.LikeRepository;

import java.util.Optional;

@Repository
public class LikeServiceImpl implements LikeService {
    private final LikeRepository repository;

    public LikeServiceImpl(LikeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Like save(Like like) {
        return repository.save(like);
    }

    @Override
    public void delete(Like like) {
        repository.delete(like);
    }

    @Override
    public Optional<Like> findByUserAndPost(Long userId, Long postId) {
        return repository.findLikeByPostAndUser(userId, postId);
    }
}
