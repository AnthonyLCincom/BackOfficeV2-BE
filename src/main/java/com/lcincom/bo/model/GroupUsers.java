package com.lcincom.bo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@Document(collection = "group-users")
public class GroupUsers {
    @Id
    private String id;
    private String groupId;
    List<User> users;
}

