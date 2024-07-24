package sync.oauth2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
    Authentication findByUserId(String userId);
}
