package com.lcincom.bo.controller;

import com.lcincom.bo.model.Group;
import com.lcincom.bo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupCtrl {

    @Autowired
    GroupRepository groupRepository;

    @GetMapping
    Flux<Group> getGroups() {
        return groupRepository.findByAllAndIsActive();
    }

    @PostMapping
    Mono<Group> save(@RequestBody Group group) {
        return groupRepository.save(group);
    }

    @GetMapping("/search-like-group-code")
    Flux<Group> searchLikeName(@RequestParam String searchName) {
        return groupRepository.findByGroupCodeLikeIgnoreCase(searchName,
                 PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "id")));
    }

    @GetMapping("/by-group-codes")
    Flux<Group> getGroupsByGroupCodes(@RequestParam List<String> groupCodes) {
        return groupRepository.findByGroupCodeIn(groupCodes);
    }

}
