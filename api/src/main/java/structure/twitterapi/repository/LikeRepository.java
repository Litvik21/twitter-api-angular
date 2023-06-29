package structure.twitterapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import structure.twitterapi.model.Like;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM likes l " +
            "WHERE l.user.id = :userId AND EXISTS (" +
            "SELECT p FROM posts p WHERE p.id = :postId)")
    Optional<Like> findLikeByPostAndUser(@Param("userId") Long userId, @Param("postId") Long postId);
}
