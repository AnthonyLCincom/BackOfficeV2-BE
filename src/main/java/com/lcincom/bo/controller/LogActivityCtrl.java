package com.lcincom.bo.controller;

import com.lcincom.bo.DTO.LogFilter;
import com.lcincom.bo.model.LogActivity;
import com.lcincom.bo.repository.LogActivityRepository;
import com.lcincom.bo.service.LogActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/log-activity")
public class LogActivityCtrl {

    @Autowired
    LogActivityRepository logRepository;

    @Autowired
    LogActivityService logActivityService;

    @PostMapping
    LogActivity save(@RequestBody LogActivity log) {
        return logRepository.save(log);
    }

    @GetMapping
    Iterable<LogActivity> get() {
        return logRepository.findAll();
    }

    @PostMapping("/filter")
    Map<String, Object> filter(@RequestBody LogFilter log) {
       return logActivityService.filter(log);
    }

}
