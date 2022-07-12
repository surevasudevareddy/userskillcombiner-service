package com.fse.userskillcombiner.controller;

import com.fse.userskillcombiner.client.SkillClient;
import com.fse.userskillcombiner.client.UserClient;
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

import java.util.List;

@RestController
@RequestMapping("/userSkillCombiner")
public class UserSkillCombinerController {
    @Qualifier("user-client")
    UserClient userClient;
    SkillClient skillClient;
    @Autowired
    public UserSkillCombinerController(UserClient userClient,SkillClient skillClient){
        this.userClient = userClient;
        this.skillClient = skillClient;
    }
    @GetMapping(value = "/userSkillsByUserId/{userId}")
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
    @GetMapping(value = "/welcome")
    public String sayHellow(){
        return "Hellow Spring boot";
    }
}
