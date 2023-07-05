package structure.twitterapi.service;

import structure.twitterapi.model.Like;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import java.util.Optional;

public interface LikeService {
    Like save(Like like);

    void delete(Like like);

    Like get(Long id);

    Optional<Like> findByUserAndPost(UserAccount user, Post post);
}
