package com.lcincom.bo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@Document(collection = "group")
public class Group {
    @Id
    private String id;
    private String groupCode;
    private String groupName;
    private Boolean isActive;
    List<Form> forms;

}
