package com.fse.userskillcombiner.client;

import com.fse.userskillcombiner.config.Config;
import com.fse.userskillcombiner.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-client", url = "${fegin.user.url}",configuration = Config.class)
public interface UserClient {
    @GetMapping(value="/user/{id}")
    public User getUserById(@PathVariable Long id);
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id);
}
