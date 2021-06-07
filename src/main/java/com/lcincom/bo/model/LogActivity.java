package com.lcincom.bo.model;

import com.lcincom.bo.enums.LogType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "log_activity", type = "_doc")
public class LogActivity {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String userName;

    @Field(type = FieldType.Keyword)
    private LogType type;

    private String access;

    @Field(type = FieldType.Text)
    private String oldData;

    @Field(type = FieldType.Text)
    private String newData;

    @Field(type = FieldType.Date)
    private Date createdTime;

}

