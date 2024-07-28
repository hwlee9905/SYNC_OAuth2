package sync.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sync.oauth2.entity.UserTask;
import sync.oauth2.entity.UserTaskId;

import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId> {
    List<UserTask> findByTaskId(Long taskId);
}
