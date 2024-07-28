package sync.oauth2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sync.oauth2.ProjectService;
import sync.oauth2.global.ResponseMessage;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    final ProjectService projectService;
    @PostMapping("/project/api/v1/find")
    public ResponseMessage findProject(Long projectId)  {
        return projectService.findProject(projectId);
    }
//    @PostMapping("/project/api/v1/get")
//    public List<GetProjectsResponseDto> getProjects(@RequestBody GetProjectsRequestDto getProjectsRequestDto)  {
//        return projectService.getProjects(getProjectsRequestDto);
//    }
    @GetMapping("/project/api/v1")
    public ResponseMessage getProjects(@RequestParam List<Long> projectIds)  {
        return projectService.getProjects(projectIds);
    }
}
