package structure.twitterapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import structure.twitterapi.model.Like;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.repository.LikeRepository;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository repository;

    @Override
    public Like save(Like like) {
        return repository.save(like);
    }

    @Override
    public void delete(Like like) {
        repository.delete(like);
    }

    @Override
    public Like get(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find like by id: " + id));
    }

    @Override
    public Optional<Like> findByUserAndPost(UserAccount user, Post post) {
        return repository.findByUserAndPost(user, post);
    }
}
