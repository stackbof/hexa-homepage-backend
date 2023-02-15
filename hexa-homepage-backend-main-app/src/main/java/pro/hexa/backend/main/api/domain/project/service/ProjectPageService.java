package pro.hexa.backend.main.api.domain.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.repository.ProjectRepository;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.project_tech_stack.repository.ProjectTechStackRepository;
import pro.hexa.backend.main.api.domain.project.dto.ProjectDto;
import pro.hexa.backend.main.api.domain.project.dto.ProjectListResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectTechStackResponse;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectPageService {

    private final ProjectRepository projectRepository;
    private final ProjectTechStackRepository projectTechStackRepository;

    public ProjectListResponse getProjectListResponse(String searchText, List<String> status, String sort, List<String> includeTechStack,
                                                      List<String> excludeTechStack, Integer year, Integer pageNum, Integer page) {
        List<Project> projectList = projectRepository.findForProjectListByQuery(searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page);
        List<ProjectDto> projects = projectList.stream()
                .map(project -> {
                    ProjectDto projectDto = new ProjectDto();
                    projectDto.fromProject(project);
                    return projectDto;
                })
                .collect(Collectors.toList());

        return ProjectListResponse.builder()
                .projects(projects)
                .page(page)
                .maxPage(3) // 임시로 넣어놨습니다
                .build();
    }

    public ProjectResponse getProjectResponse(Long projectId) {
        ProjectResponse projectResponse = new ProjectResponse();

        Optional.ofNullable(projectRepository.findForProjectByQuery(projectId))
            .ifPresent(projectResponse::fromProject);

        return projectResponse;
    }

    public ProjectTechStackResponse getProjectTechStackResponse() {
        List<ProjectTechStack> techStackList = projectTechStackRepository.findTechStackByQuery();

        return ProjectTechStackResponse
            .builder()
            .techStackList(
                techStackList
                    .stream()
                    .map(ProjectTechStack::getContent)
                    .collect(Collectors.toList())
            )
            .build();
    }
}
