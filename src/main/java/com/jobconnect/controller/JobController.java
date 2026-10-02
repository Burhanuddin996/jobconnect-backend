package com.jobconnect.controller;

import com.jobconnect.dto.ApiResponse;
import com.jobconnect.dto.JobRequest;
import com.jobconnect.dto.JobResponse;
import com.jobconnect.entity.JobType;
import com.jobconnect.security.SecurityUtil;
import com.jobconnect.service.JobService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobResponse>> create(@RequestParam Long companyId, @Valid @RequestBody JobRequest request) {
        JobResponse created = jobService.create(SecurityUtil.getCurrentUserEmail(), companyId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(201, "Job created successfully", created));
    }

    // Milestone 11: GET /api/jobs?page=0&size=10&sort=postedDate,desc
    @GetMapping
    public ResponseEntity<ApiResponse<Page<JobResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate,desc") String sort) {

        Pageable pageable = buildPageable(page, size, sort);
        return ResponseEntity.ok(ApiResponse.of(200, "Jobs fetched", jobService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(200, "Job fetched", jobService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> update(@PathVariable Long id, @Valid @RequestBody JobRequest request) {
        JobResponse updated = jobService.update(SecurityUtil.getCurrentUserEmail(), id, request);
        return ResponseEntity.ok(ApiResponse.of(200, "Job updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        jobService.delete(SecurityUtil.getCurrentUserEmail(), id);
        return ResponseEntity.ok(ApiResponse.of(200, "Job deleted successfully", null));
    }

    // Milestone 6: GET /api/jobs/search?keyword=java&location=Hyderabad
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<JobResponse>>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate,desc") String sort) {

        Pageable pageable = buildPageable(page, size, sort);
        Page<JobResponse> results = jobService.search(keyword, location, null, null, null, pageable);
        return ResponseEntity.ok(ApiResponse.of(200, "Search results", results));
    }

    // Milestone 6: GET /api/jobs/filter?minSalary=300000&maxSalary=800000&jobType=FULL_TIME
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<JobResponse>>> filter(
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) JobType jobType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate,desc") String sort) {

        Pageable pageable = buildPageable(page, size, sort);
        Page<JobResponse> results = jobService.search(null, null, minSalary, maxSalary, jobType, pageable);
        return ResponseEntity.ok(ApiResponse.of(200, "Filter results", results));
    }

    private Pageable buildPageable(int page, int size, String sort) {
        String[] parts = sort.split(",");
        String property = parts[0];
        Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, size, Sort.by(direction, property));
    }
}
