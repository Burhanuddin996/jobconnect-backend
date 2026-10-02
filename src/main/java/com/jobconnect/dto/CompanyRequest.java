package com.jobconnect.dto;

import jakarta.validation.constraints.NotBlank;

public class CompanyRequest {
    @NotBlank(message = "Company name is required")
    private String companyName;
    private String description;
    private String website;
    private String location;
    private String industry;

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
