package com.example.jpaexample.service;

import com.example.jpaexample.domain.Project;
import com.example.jpaexample.dto.project.ProjectCreateRequestDto;
import com.example.jpaexample.dto.project.ProjectRenameRequestDto;
import com.example.jpaexample.dto.project.ProjectResponseDto;
import com.example.jpaexample.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    // 프로젝트가 존재하는지 확인하기
    public Project validateProjectExists(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 프로젝트입니다."
                ));
    }

    @Transactional
    public ProjectResponseDto saveProject(ProjectCreateRequestDto request) {
        // 프로젝트 이름으로 Project 객체 생성
        Project project = Project.builder()
                .projectTitle(request.projectTitle())
                .build();

        // 저장소에 Project 객체 저장하기
        projectRepository.save(project);

        // Project 객체 반환하기
        return ProjectResponseDto.from(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getAllProject() {
        return projectRepository.findAll().stream()
                .map(ProjectResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getProject(Long projectId) {
        Project project = validateProjectExists(projectId);
        return ProjectResponseDto.from(project);
    }

    @Transactional
    public ProjectResponseDto renameProject(Long projectId, ProjectRenameRequestDto request) {
        Project project = validateProjectExists(projectId);

        try {
            project.rename(request.projectTitle());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e);
        }

        return ProjectResponseDto.from(project);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project project = validateProjectExists(projectId);
        projectRepository.delete(project);
    }
}
