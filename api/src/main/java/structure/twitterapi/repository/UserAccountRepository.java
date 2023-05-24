package structure.twitterapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import structure.twitterapi.model.UserAccount;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Query("SELECT u FROM users u JOIN FETCH u.role WHERE u.username = :username")
    Optional<UserAccount> findByUsername(@Param("username") String username);
}
