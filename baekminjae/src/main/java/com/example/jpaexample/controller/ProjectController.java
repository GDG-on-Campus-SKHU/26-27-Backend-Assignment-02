package com.example.jpaexample.controller;

import com.example.jpaexample.dto.project.ProjectCreateRequestDto;
import com.example.jpaexample.dto.project.ProjectResponseDto;
import com.example.jpaexample.dto.project.ProjectRenameRequestDto;
import com.example.jpaexample.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    // 프로젝트 생성하기
    @PostMapping
    public ResponseEntity<ProjectResponseDto> saveProject(@RequestBody ProjectCreateRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.saveProject(request));
    }

    // 모든 프로젝트 불러오기
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProject() {
        return ResponseEntity.ok(projectService.getAllProject());
    }

    // 특정 프로젝트 불러오기
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProject(projectId));
    }

    // 특정 프로젝트 이름 바꾸기
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> renameProject(
            @PathVariable Long projectId,
            @RequestBody ProjectRenameRequestDto request
    ) {
        return ResponseEntity.ok(projectService.renameProject(projectId, request));
    }

    // 특정 프로젝트 삭제하기
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
