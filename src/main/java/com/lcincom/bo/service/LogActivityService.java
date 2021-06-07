package com.lcincom.bo.service;

import com.lcincom.bo.DTO.LogFilter;

import java.util.Map;

public interface LogActivityService {
    Map<String, Object> filter(LogFilter log);
}