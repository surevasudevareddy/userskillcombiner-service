package com.fse.userskillcombiner.model;

import lombok.Data;

@Data
public class Skill {

    private Long id;

    private String skillName;

    private String skillType;

    private int expertiseLevel;

    private Long userId;
}
