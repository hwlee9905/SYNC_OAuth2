package sync.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sync.oauth2.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
