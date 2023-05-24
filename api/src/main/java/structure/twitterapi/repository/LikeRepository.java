package structure.twitterapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import structure.twitterapi.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
