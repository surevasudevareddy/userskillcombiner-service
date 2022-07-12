package com.fse.userskillcombiner.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor()
@ToString
@EqualsAndHashCode
public class User {

    private Long id;
    private String name;
    private String associateId;
    private String email;
    private long mobile;
    private Date createTs;
    private Date updateTs;
    private List<Skill> skill;

}
