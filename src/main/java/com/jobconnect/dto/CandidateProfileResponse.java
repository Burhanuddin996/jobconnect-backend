package com.jobconnect.dto;

import com.jobconnect.entity.CandidateProfile;

import java.util.Set;
import java.util.stream.Collectors;

public class CandidateProfileResponse {
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String headline;
    private String summary;
    private String location;
    private String education;
    private Integer experienceYears;
    private String resumeUrl;
    private String linkedinUrl;
    private Set<SkillResponse> skills;

    public static CandidateProfileResponse from(CandidateProfile profile) {
        CandidateProfileResponse dto = new CandidateProfileResponse();
        dto.id = profile.getId();
        dto.userId = profile.getUser().getId();
        dto.name = profile.getUser().getName();
        dto.email = profile.getUser().getEmail();
        dto.headline = profile.getHeadline();
        dto.summary = profile.getSummary();
        dto.location = profile.getLocation();
        dto.education = profile.getEducation();
        dto.experienceYears = profile.getExperienceYears();
        dto.resumeUrl = profile.getResumeUrl();
        dto.linkedinUrl = profile.getLinkedinUrl();
        dto.skills = profile.getSkills().stream().map(SkillResponse::from).collect(Collectors.toSet());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public Set<SkillResponse> getSkills() { return skills; }
    public void setSkills(Set<SkillResponse> skills) { this.skills = skills; }
}
