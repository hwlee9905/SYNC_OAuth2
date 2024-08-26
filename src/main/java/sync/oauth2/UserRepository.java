package sync.oauth2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sync.oauth2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByAuthenticationUserId(String userId);

}
