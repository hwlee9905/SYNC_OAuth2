package sync.oauth2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sync.oauth2.TaskService;
import sync.oauth2.global.ResponseMessage;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    @GetMapping("project/tasks/api/v1/getChildren")
    public ResponseMessage getOnlyChildrenTasks(Long taskId)  {
        return taskService.getOnlyChildrenTasks(taskId);
    }
    @GetMapping("/project/task/api/v1/users")
    public ResponseMessage getUserFromTask(@RequestParam Long taskId) {
        return taskService.getUserIdsFromTask(taskId);
    }

}
