package com.lcincom.bo.repository;

import com.lcincom.bo.model.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface GroupRepository extends ReactiveMongoRepository<Group, String> {

    Flux<Group> findByGroupCodeIn(@RequestParam("groupCodes") @Param("groupCodes") List<String> groupCodes);

    @Query(value = "{isActive: true}", fields="{ 'groupCode' : 1, 'groupName' : 1}")
    Flux<Group> findByAllAndIsActive();

    @Query(fields="{ 'groupCode' : 1, 'groupName' : 1}")
    Flux<Group> findByGroupCodeLikeIgnoreCase(String searchName, Pageable pageable);

    Mono<Group> findById(String groupId);
}