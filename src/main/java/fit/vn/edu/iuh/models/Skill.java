package fit.vn.edu.iuh.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @Column(name = "skill_id", nullable = false)
    private Long id;

    @Column(name = "skill_name", nullable = false, length = 150)
    private String skillName;

    @Column(name = "skill_desc")
    private String skillDesc;

    @Column(name = "skill_type")
    private Byte skillType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillDesc() {
        return skillDesc;
    }

    public void setSkillDesc(String skillDesc) {
        this.skillDesc = skillDesc;
    }

    public Byte getSkillType() {
        return skillType;
    }

    public void setSkillType(Byte skillType) {
        this.skillType = skillType;
    }

}