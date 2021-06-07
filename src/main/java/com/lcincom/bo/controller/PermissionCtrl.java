package com.lcincom.bo.controller;

import com.lcincom.bo.model.Form;
import com.lcincom.bo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionCtrl {

    @Autowired
    PermissionService permissionService;

    @GetMapping
    List<Form> getPermissionByLoginId(@RequestParam String loginId) {
        return permissionService.getPermissionByLoginId(loginId);
    }

}
