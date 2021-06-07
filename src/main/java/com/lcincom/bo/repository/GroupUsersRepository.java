package com.lcincom.bo.repository;

import com.lcincom.bo.model.GroupUsers;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GroupUsersRepository extends ReactiveMongoRepository<GroupUsers, String> {

    Mono<GroupUsers> findByGroupId(String groupId);

    Flux<GroupUsers> findByUsers_LoginId(String loginId);
}