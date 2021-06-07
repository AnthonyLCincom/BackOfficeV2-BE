package com.lcincom.bo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Form {
    @Id
    private String id;
    private String formName;
    private String formCode;
    private List<String> actions;
    private Boolean isActive;



}
