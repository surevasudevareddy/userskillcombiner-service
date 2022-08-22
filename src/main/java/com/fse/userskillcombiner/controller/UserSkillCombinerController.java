package com.fse.userskillcombiner.controller;

import com.fse.userskillcombiner.client.SkillClient;
import com.fse.userskillcombiner.client.UserClient;
import com.fse.userskillcombiner.client.UserProfileKafkaClient;
import com.fse.userskillcombiner.model.Skill;
import com.fse.userskillcombiner.model.User;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.StringDecoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userSkillCombiner")
public class UserSkillCombinerController {
    @Qualifier("user-client")
    UserClient userClient;
    SkillClient skillClient;
    UserProfileKafkaClient userProfileKafkaClient;
    @Autowired
    public UserSkillCombinerController(UserClient userClient,SkillClient skillClient,UserProfileKafkaClient userProfileKafkaClient){
        this.userClient = userClient;
        this.skillClient = skillClient;
        this.userProfileKafkaClient = userProfileKafkaClient;
    }
    @GetMapping(value = "/userAndSkillsByUserName/{name}")
    public User getUserSkillByUserName(@PathVariable("name") String name){
        User user = userClient.getUserByName(name);
        user.setSkill(skillClient.getUserSkillByUserId(user.getId()));
        return user;
    }
    @GetMapping(value = "/userAndSkillsByUserId/{userId}")
    public User getUserSkillByUserId(@PathVariable("userId") Long userId){
        //UserClient userClient = Feign.builder().contract(new SpringMvcContract()).encoder(new SpringFormEncoder()).decoder(new StringDecoder()).target(UserClient.class,"http://localhost:8081/userService/");
        User user = userClient.getUserById(userId);
        user.setSkill(skillClient.getUserSkillByUserId(userId));
        return user;
    }
    @DeleteMapping("/deleteUserSkillsByUserId/{userId}")
    public void deleteUserSkillByUserId(@PathVariable("userId") Long userId){
        userClient.deleteUserById(userId);
        skillClient.deleteSkillByUserId(userId);
    }
    @PostMapping("/saveUserAndSkills")
    public User saveUserAndSkills(@RequestBody User user){
        User userl = User.builder()
                .associateId(user.getAssociateId())
                .createTs(user.getCreateTs())
                .email(user.getEmail())
                .name(user.getName())
                .updateTs(user.getUpdateTs())
                .mobile(user.getMobile())
                .build();
        userl = userClient.saveUser(userl);

        User finalUserl = userl;
        List<Skill> skillList = user.getSkill().stream().map(skl -> {
            skl.setUserId(finalUserl.getId());
             return skillClient.saveSkill(skl);
        }).collect(Collectors.toList());

        userl.setSkill(skillList);

        //Post user to Kafka messaging by calling service
        this.userProfileKafkaClient.postUserProfile(userl);

        return userl;
    }
    @PostMapping("/updateUserAndSkills")
    public User updateUserAndSkills(@RequestBody User user){
        User userl = User.builder()
                .id(user.getId())
                .associateId(user.getAssociateId())
                .createTs(user.getCreateTs())
                .email(user.getEmail())
                .name(user.getName())
                .updateTs(new Date())
                .mobile(user.getMobile())
                .build();
        userl = userClient.saveUser(userl);

        //User finalUserl = userl;
        List<Skill> skillList = user.getSkill().stream().map(skl -> {
            //skl.setUserId(finalUserl.getId());
            return skillClient.saveSkill(skl);
        }).collect(Collectors.toList());

        userl.setSkill(skillClient.getUserSkillByUserId(userl.getId()));

        //Post user to Kafka messaging by calling service
        this.userProfileKafkaClient.postUserProfile(userl);

        return userl;
    }
    @GetMapping(value = "/welcome")
    public String sayHellow(){
        return "Hellow Spring boot";
    }
}
