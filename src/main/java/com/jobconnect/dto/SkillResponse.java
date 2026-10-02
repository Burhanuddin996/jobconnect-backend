package com.jobconnect.dto;

import com.jobconnect.entity.Skill;

public class SkillResponse {
    private Long id;
    private String name;

    public static SkillResponse from(Skill skill) {
        SkillResponse dto = new SkillResponse();
        dto.id = skill.getId();
        dto.name = skill.getName();
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
