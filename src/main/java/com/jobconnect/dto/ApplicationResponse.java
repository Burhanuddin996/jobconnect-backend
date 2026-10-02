package com.jobconnect.dto;

import com.jobconnect.entity.Application;
import com.jobconnect.entity.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationResponse {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private Long candidateId;
    private String candidateName;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;

    public static ApplicationResponse from(Application application) {
        ApplicationResponse dto = new ApplicationResponse();
        dto.id = application.getId();
        dto.jobId = application.getJob().getId();
        dto.jobTitle = application.getJob().getTitle();
        dto.candidateId = application.getCandidate().getId();
        dto.candidateName = application.getCandidate().getUser().getName();
        dto.status = application.getStatus();
        dto.appliedAt = application.getAppliedAt();
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
}
