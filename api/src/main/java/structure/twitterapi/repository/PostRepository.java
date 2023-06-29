package structure.twitterapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT distinct p FROM posts p LEFT JOIN FETCH p.likes WHERE p.user = :user")
    List<Post> findAllByUser(@Param("user") UserAccount user);
}

