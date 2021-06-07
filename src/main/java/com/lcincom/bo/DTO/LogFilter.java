package com.lcincom.bo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class LogFilter {
    private String userName;
    private String type;
    private Date fromDate;
    private Date toDate;
    private String keyword;

    private int pageNo;
    private int recordsPerPage;
    private String sortByCols;
    private String orderBy;

}
