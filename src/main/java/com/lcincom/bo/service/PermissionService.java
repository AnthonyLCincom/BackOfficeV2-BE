package com.lcincom.bo.service;

import com.lcincom.bo.model.Form;

import java.util.List;

public interface PermissionService {
    List<Form> getPermissionByLoginId(String loginId);
}
