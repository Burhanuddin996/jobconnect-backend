package com.jobconnect.controller;

import com.jobconnect.dto.ApiResponse;
import com.jobconnect.dto.ApplicationResponse;
import com.jobconnect.dto.ApplicationStatusUpdateRequest;
import com.jobconnect.security.SecurityUtil;
import com.jobconnect.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/jobs/{jobId}/apply")
    public ResponseEntity<ApiResponse<ApplicationResponse>> apply(@PathVariable Long jobId) {
        ApplicationResponse created = applicationService.apply(SecurityUtil.getCurrentUserEmail(), jobId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(201, "Application submitted", created));
    }

    @GetMapping("/applications/my")
    public ResponseEntity<ApiResponse<Page<ApplicationResponse>>> myApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ApplicationResponse> results = applicationService.getMyApplications(SecurityUtil.getCurrentUserEmail(), pageable);
        return ResponseEntity.ok(ApiResponse.of(200, "Your applications", results));
    }

    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> applicationsForJob(@PathVariable Long jobId) {
        List<ApplicationResponse> results = applicationService.getApplicationsForJob(SecurityUtil.getCurrentUserEmail(), jobId);
        return ResponseEntity.ok(ApiResponse.of(200, "Applicants for job " + jobId, results));
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<ApiResponse<ApplicationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(200, "Application fetched", applicationService.getById(id)));
    }

    @PutMapping("/applications/{id}/status")
    public ResponseEntity<ApiResponse<ApplicationResponse>> updateStatus(
            @PathVariable Long id, @Valid @RequestBody ApplicationStatusUpdateRequest request) {

        ApplicationResponse updated = applicationService.updateStatus(
                SecurityUtil.getCurrentUserEmail(), id, request.getStatus());
        return ResponseEntity.ok(ApiResponse.of(200, "Application status updated", updated));
    }
}
