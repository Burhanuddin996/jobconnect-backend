package com.jobconnect.dto;

import jakarta.validation.constraints.NotBlank;

public class SkillRequest {
    @NotBlank(message = "Skill name is required")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
