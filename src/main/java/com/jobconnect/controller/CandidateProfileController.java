package com.jobconnect.controller;

import com.jobconnect.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;
import com.jobconnect.dto.ApiResponse;
import com.jobconnect.dto.CandidateProfileRequest;
import com.jobconnect.dto.CandidateProfileResponse;
import com.jobconnect.security.SecurityUtil;
import com.jobconnect.service.CandidateProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;
    private final FileStorageService fileStorageService;

    public CandidateProfileController(CandidateProfileService candidateProfileService,
                                       FileStorageService fileStorageService) {
        this.candidateProfileService = candidateProfileService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<CandidateProfileResponse>> create(@Valid @RequestBody CandidateProfileRequest request) {
        CandidateProfileResponse created = candidateProfileService.createProfile(SecurityUtil.getCurrentUserEmail(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(201, "Candidate profile created", created));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<CandidateProfileResponse>> getMine() {
        CandidateProfileResponse profile = candidateProfileService.getMyProfile(SecurityUtil.getCurrentUserEmail());
        return ResponseEntity.ok(ApiResponse.of(200, "Candidate profile fetched", profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<CandidateProfileResponse>> updateMine(@Valid @RequestBody CandidateProfileRequest request) {
        CandidateProfileResponse updated = candidateProfileService.updateMyProfile(SecurityUtil.getCurrentUserEmail(), request);
        return ResponseEntity.ok(ApiResponse.of(200, "Candidate profile updated", updated));
    }

    @PostMapping("/profile/resume")
    public ResponseEntity<ApiResponse<CandidateProfileResponse>> uploadResume(@RequestParam("file") MultipartFile file) {
        String resumeUrl = fileStorageService.storeResume(file);
        CandidateProfileResponse updated = candidateProfileService.updateResumeUrl(SecurityUtil.getCurrentUserEmail(), resumeUrl);
        return ResponseEntity.ok(ApiResponse.of(200, "Resume uploaded", updated));
    }

    @PostMapping("/{candidateId}/skills/{skillId}")
    public ResponseEntity<ApiResponse<CandidateProfileResponse>> addSkill(@PathVariable Long candidateId, @PathVariable Long skillId) {
        CandidateProfileResponse updated = candidateProfileService.addSkill(candidateId, skillId);
        return ResponseEntity.ok(ApiResponse.of(200, "Skill added", updated));
    }

    @DeleteMapping("/{candidateId}/skills/{skillId}")
    public ResponseEntity<ApiResponse<CandidateProfileResponse>> removeSkill(@PathVariable Long candidateId, @PathVariable Long skillId) {
        CandidateProfileResponse updated = candidateProfileService.removeSkill(candidateId, skillId);
        return ResponseEntity.ok(ApiResponse.of(200, "Skill removed", updated));
    }
}