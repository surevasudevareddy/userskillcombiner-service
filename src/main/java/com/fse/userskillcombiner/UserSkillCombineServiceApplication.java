package com.fse.userskillcombiner;

import com.fse.userskillcombiner.controller.UserSkillCombinerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
public class UserSkillCombineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSkillCombineServiceApplication.class, args);
	}

}
