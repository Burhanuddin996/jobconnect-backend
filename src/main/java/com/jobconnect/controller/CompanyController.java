package com.jobconnect.controller;

import com.jobconnect.dto.ApiResponse;
import com.jobconnect.dto.CompanyRequest;
import com.jobconnect.dto.CompanyResponse;
import com.jobconnect.security.SecurityUtil;
import com.jobconnect.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> create(@Valid @RequestBody CompanyRequest request) {
        CompanyResponse created = companyService.create(SecurityUtil.getCurrentUserEmail(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(201, "Company created", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.of(200, "Companies fetched", companyService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(200, "Company fetched", companyService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
        CompanyResponse updated = companyService.update(SecurityUtil.getCurrentUserEmail(), id, request);
        return ResponseEntity.ok(ApiResponse.of(200, "Company updated", updated));
    }
}
