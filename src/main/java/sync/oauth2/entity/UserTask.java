package sync.oauth2.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_task",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "user_task_uk",
            columnNames = {"user_id", "task_id"}
        )
    }
)
public class UserTask {
    @EmbeddedId
    private UserTaskId id;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;
}