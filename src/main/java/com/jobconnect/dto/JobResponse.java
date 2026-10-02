package com.jobconnect.dto;

import com.jobconnect.entity.Job;
import com.jobconnect.entity.JobType;

import java.time.LocalDateTime;

public class JobResponse {
    private Long id;
    private Long companyId;
    private String companyName;
    private String title;
    private String description;
    private String location;
    private Double salaryMin;
    private Double salaryMax;
    private Integer experienceMin;
    private JobType jobType;
    private LocalDateTime postedDate;
    private boolean active;

    public static JobResponse from(Job job) {
        JobResponse dto = new JobResponse();
        dto.id = job.getId();
        dto.companyId = job.getCompany().getId();
        dto.companyName = job.getCompany().getCompanyName();
        dto.title = job.getTitle();
        dto.description = job.getDescription();
        dto.location = job.getLocation();
        dto.salaryMin = job.getSalaryMin();
        dto.salaryMax = job.getSalaryMax();
        dto.experienceMin = job.getExperienceMin();
        dto.jobType = job.getJobType();
        dto.postedDate = job.getPostedDate();
        dto.active = job.isActive();
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getSalaryMin() { return salaryMin; }
    public void setSalaryMin(Double salaryMin) { this.salaryMin = salaryMin; }

    public Double getSalaryMax() { return salaryMax; }
    public void setSalaryMax(Double salaryMax) { this.salaryMax = salaryMax; }

    public Integer getExperienceMin() { return experienceMin; }
    public void setExperienceMin(Integer experienceMin) { this.experienceMin = experienceMin; }

    public JobType getJobType() { return jobType; }
    public void setJobType(JobType jobType) { this.jobType = jobType; }

    public LocalDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
