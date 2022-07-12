package com.fse.userskillcombiner.client;

import com.fse.userskillcombiner.config.Config;
import com.fse.userskillcombiner.model.Skill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "skill-client", url = "${fegin.skill.url}",configuration = Config.class)
public interface SkillClient {
    @GetMapping(value = "/skillsByUserId/{userId}")
    public List<Skill> getUserSkillByUserId(@PathVariable Long userId);
    @DeleteMapping("/delete/{id}")
    public void deleteSkillByUserId(@PathVariable Long userId);
}
