package com.lcincom.bo.controller;

import com.lcincom.bo.model.GroupUsers;
import com.lcincom.bo.repository.GroupUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/group-users")
public class GroupUsersCtrl {

    @Autowired
    GroupUsersRepository groupUsersRepository;

    @GetMapping
    Flux<GroupUsers> getAllGroupUsers() {
        return groupUsersRepository.findAll();
    }

    @PostMapping
    Mono<GroupUsers> save(@RequestBody GroupUsers groupUsers) {
        return groupUsersRepository.save(groupUsers);
    }

    @GetMapping("/by-groupId")
    Mono<GroupUsers> getGroupUsers(@RequestParam String groupId) {
        return groupUsersRepository.findByGroupId(groupId);
    }

    @GetMapping("/by-loginId")
    Flux<GroupUsers> getGroupUsersByLoginId(@RequestParam String loginId) {
        return groupUsersRepository.findByUsers_LoginId(loginId);
    }

}
