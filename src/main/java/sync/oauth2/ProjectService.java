package sync.oauth2;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sync.oauth2.dto.request.CreateProjectRequestDto;
import sync.oauth2.dto.response.GetProjectsResponseDto;
import sync.oauth2.entity.Project;
import sync.oauth2.global.ResponseMessage;
import sync.oauth2.repository.ProjectRepository;
import sync.oauth2.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final TaskRepository taskRepository;
	@Transactional(rollbackFor = { Exception.class })
	public Project createProject(CreateProjectRequestDto projectCreateRequestDto) {
		Project project = Project.builder()
				.description(projectCreateRequestDto.getDescription())
				.subTitle(projectCreateRequestDto.getSubTitle())
				.startDate(projectCreateRequestDto.getStartDate())
				.endDate(projectCreateRequestDto.getEndDate())
				.title(projectCreateRequestDto.getTitle()).build();
		return projectRepository.save(project);
	}
	@Transactional(rollbackFor = { Exception.class })
	public ResponseMessage findProject(Long projectId) {
		try {
			Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(projectId)));

			return new ResponseMessage("프로젝트 조회 성공", true, project.getId());
		} catch (EntityNotFoundException e) {
			return new ResponseMessage("해당 프로젝트는 존재하지 않습니다.", false, e.getMessage());
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public ResponseMessage getProjects(List<Long> projectIds) {
		List<GetProjectsResponseDto> result = projectIds.stream()
				.map(projectRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(project -> {
					int totalTasks = taskRepository.countByProjectIdAndDepth(project.getId());
					int completedTasks = taskRepository.countByProjectIdAndDepthAndStatus(project.getId());
					float progress = totalTasks > 0 ? (float) completedTasks / totalTasks : 0;
					return new GetProjectsResponseDto(
							project.getId(),
							project.getTitle(),
							project.getDescription(),
							project.getStartDate(),
							project.getEndDate(),
							progress
					);
				})
				.collect(Collectors.toList());
		return new ResponseMessage("프로젝트 조회 완료", true, result);
	}

}
