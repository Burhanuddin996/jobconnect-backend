package com.jobconnect.controller;

import com.jobconnect.dto.ApiResponse;
import com.jobconnect.dto.SkillRequest;
import com.jobconnect.dto.SkillResponse;
import com.jobconnect.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SkillResponse>> create(@Valid @RequestBody SkillRequest request) {
        SkillResponse created = skillService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(201, "Skill created", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.of(200, "Skills fetched", skillService.findAll()));
    }
}
