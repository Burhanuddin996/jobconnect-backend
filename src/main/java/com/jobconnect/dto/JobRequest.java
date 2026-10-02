package com.jobconnect.dto;

import com.jobconnect.entity.JobType;
import jakarta.validation.constraints.NotBlank;

public class JobRequest {
    @NotBlank(message = "Job title is required")
    private String title;
    private String description;
    private String location;
    private Double salaryMin;
    private Double salaryMax;
    private Integer experienceMin;
    private JobType jobType;
    private Boolean active;

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

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
