package com.jobconnect.dto;

import com.jobconnect.entity.Company;

public class CompanyResponse {
    private Long id;
    private Long recruiterId;
    private String recruiterName;
    private String companyName;
    private String description;
    private String website;
    private String location;
    private String industry;

    public static CompanyResponse from(Company company) {
        CompanyResponse dto = new CompanyResponse();
        dto.id = company.getId();
        dto.recruiterId = company.getRecruiter().getId();
        dto.recruiterName = company.getRecruiter().getName();
        dto.companyName = company.getCompanyName();
        dto.description = company.getDescription();
        dto.website = company.getWebsite();
        dto.location = company.getLocation();
        dto.industry = company.getIndustry();
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRecruiterId() { return recruiterId; }
    public void setRecruiterId(Long recruiterId) { this.recruiterId = recruiterId; }

    public String getRecruiterName() { return recruiterName; }
    public void setRecruiterName(String recruiterName) { this.recruiterName = recruiterName; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
}
