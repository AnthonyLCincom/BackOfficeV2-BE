package com.lcincom.bo.controller;

import com.lcincom.bo.model.User;
import com.lcincom.bo.repository.UserRepository;
import com.lcincom.bo.service.CreateAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CreateAgentService createAgentService;

    @GetMapping
    Flux<User> getAUsers() {
        return userRepository.findByAllAndIsActive();
    }

    @PostMapping
    Mono<User> save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("create-agent")
    Map<String, String>  createAgent(@RequestBody User user) {
       String statusCreateAgent = createAgentService.createNewAgentByLoginId(user.getLoginId());
        Map<String, String> result = new HashMap<String, String>() {{
            put("status", statusCreateAgent.contains(user.getLoginId()) ? "success" : "fail");
            put("message", statusCreateAgent);
        }};
        return result;
    }

    @GetMapping("check-existed")
    Map<String, Boolean> checkExistUser(@RequestParam String loginId) {
        Mono<User> user = userRepository.findByLoginId(loginId);
        Map<String, Boolean> result = new HashMap<String, Boolean>() {{
            put("isExisted", user.block() != null);
        }};
        return result;
    }

    @PostMapping("/save-list")
    Flux<User> saveList(@RequestBody List<User> users) {
        return userRepository.saveAll(users);
    }

    @GetMapping("/by-login-id")
    Flux<User> getUserBySearchName(@RequestParam String searchName) {
        return userRepository.findByLoginIdLikeIgnoreCase(searchName,
                PageRequest.of(0,5, Sort.by(Sort.Direction.ASC, "id")));
    }

}
