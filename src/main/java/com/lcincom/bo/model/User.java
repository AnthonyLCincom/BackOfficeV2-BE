package com.lcincom.bo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@Builder
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String loginId;
    private Boolean isActive;
    List<Form> forms;
}

