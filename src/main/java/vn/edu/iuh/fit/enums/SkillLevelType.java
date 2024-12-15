package vn.edu.iuh.fit.enums;


import lombok.Getter;

@Getter
public enum SkillLevelType {
        BEGINNER(1, "Beginner"),
        INTERMEDIATE(2, "Intermediate"),
        ADVANCED(3, "Advanced"),
        PROFESSIONAL(4, "Professional"),
        EXPERT(5, "Expert");

    private final int id;
    private final String name;

        SkillLevelType(int id, String name) {
            this.id = id;
            this.name = name;
        }


}


