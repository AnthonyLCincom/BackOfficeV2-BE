package com.lcincom.bo.repository;

import com.lcincom.bo.model.Form;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FormRepository extends ReactiveMongoRepository<Form, String> {

    @Query(value = "{'isActive': true}")
    Flux<Form> findByAllAndIsActive();
}
