package com.fse.userskillcombiner.client;

import com.fse.userskillcombiner.config.Config;
import com.fse.userskillcombiner.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "user-client", url = "${fegin.user.url}",configuration = Config.class)
public interface UserClient {
    @GetMapping(value="/user/byName/{name}")
    public User getUserByName(@PathVariable String name);

    @GetMapping(value="/user/{id}")
    public User getUserById(@PathVariable Long id);

    @PostMapping(value = "/saveUser")
    public User saveUser(@RequestBody User user);
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id);
}
