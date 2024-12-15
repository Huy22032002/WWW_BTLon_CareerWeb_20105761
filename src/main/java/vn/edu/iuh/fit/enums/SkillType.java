package vn.edu.iuh.fit.enums;

import lombok.Getter;

@Getter
public enum SkillType {
    SOFT_SKILL(1, "Soft Skill"),
    UNSPECIFIC(2, "Unspecified"),
    TECHNICAL_SKILL(3, "Technical Skill");

    private final int id;
    private final String description;

    SkillType(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
