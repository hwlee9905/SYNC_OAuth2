package sync.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sync.oauth2.dto.request.CreateTaskRequestDto;
import sync.oauth2.dto.response.GetMemberFromTaskResponseDto;
import sync.oauth2.dto.response.GetTaskResponseDto;
import sync.oauth2.entity.Project;
import sync.oauth2.entity.Task;
import sync.oauth2.entity.UserTask;
import sync.oauth2.global.ResponseMessage;
import sync.oauth2.repository.ProjectRepository;
import sync.oauth2.repository.TaskRepository;
import sync.oauth2.repository.UserTaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserTaskRepository userTaskRepository;
    @Transactional(rollbackFor = { Exception.class })
    public void createTask(CreateTaskRequestDto createTaskRequestDto) {
        Optional<Project> project = projectRepository.findById(createTaskRequestDto.getProjectId());
        //project id 존재하지 않는경우 예외처리 해야함 (추가)
        Optional<Task> parentTask = taskRepository.findById(createTaskRequestDto.getParentTaskId());
        Task task;

        if (parentTask.isPresent()) {
            if (parentTask.get().getDepth() == 2) {
                throw new IllegalArgumentException("Parent task cannot have a depth of 2.");
            }
            task = Task.builder().title(createTaskRequestDto.getTitle())
                    .description(createTaskRequestDto.getDescription())
                    .parentTask(parentTask.get())
                    .depth(parentTask.get().getDepth() + 1)
                    .endDate(createTaskRequestDto.getEndDate())
                    .startDate(createTaskRequestDto.getStartDate())
                    .status(createTaskRequestDto.getStatus())
                    .project(project.get()).build();
        } else {
            task = Task.builder().title(createTaskRequestDto.getTitle())
                    .depth(0)
                    .description(createTaskRequestDto.getDescription()).endDate(createTaskRequestDto.getEndDate())
                    .startDate(createTaskRequestDto.getStartDate()).status(createTaskRequestDto.getStatus())
                    .project(project.get()).build();
        }
        taskRepository.save(task);
    }
    @Transactional(rollbackFor = { Exception.class })
    public ResponseMessage getOnlyChildrenTasks(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            return ResponseMessage.builder()
                    .result(false)
                    .build();
        }else{
            Task task = taskOptional.get();
            GetTaskResponseDto result = GetTaskResponseDto.fromEntityOnlyChildrenTasks(task);
            return ResponseMessage.builder()
                    .result(true)
                    .value(result).build();
        }

    }

    public ResponseMessage getUserIdsFromTask(Long taskId) {
        List<UserTask> userTasks = userTaskRepository.findByTaskId(taskId);
        GetMemberFromTaskResponseDto result = GetMemberFromTaskResponseDto.builder()
            .userIds(userTasks.stream()
                .map(userTask -> userTask.getId().getUserId())
                .collect(Collectors.toList()))
            .build();
        if (userTasks.isEmpty()) {
            return ResponseMessage.builder()
                .result(false)
                .message("해당 업무에는 배정된 담당자가 없습니다.")
                .build();
        }
        return ResponseMessage.builder()
            .result(true)
            .value(result)
            .build();
    }
}