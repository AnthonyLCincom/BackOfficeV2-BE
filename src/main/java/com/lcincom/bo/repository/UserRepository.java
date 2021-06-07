package com.lcincom.bo.repository;

import com.lcincom.bo.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    @Query(value = "{isActive: true}")
    Flux<User> findByAllAndIsActive();

    Flux<User> findByLoginIdLikeIgnoreCase(String searchName, Pageable pageable);

    Mono<User> findByLoginId(String loginId);
}