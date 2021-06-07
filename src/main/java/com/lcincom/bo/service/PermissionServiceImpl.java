package com.lcincom.bo.service;

import com.lcincom.bo.model.Form;
import com.lcincom.bo.model.GroupUsers;
import com.lcincom.bo.model.User;
import com.lcincom.bo.repository.GroupRepository;
import com.lcincom.bo.repository.GroupUsersRepository;
import com.lcincom.bo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupUsersRepository groupUsersRepository;

    @Autowired
    GroupRepository groupRepository;

    @Override
    public List<Form> getPermissionByLoginId(String loginId) {

        User user = userRepository.findByLoginId(loginId).block();

        if (null == user) {
            return new ArrayList<>();
        }

        List<GroupUsers> groupUsers = groupUsersRepository.findByUsers_LoginId(loginId).collectList().block();

        List<Form> forms = new ArrayList<>();
        if (null != user.getForms()) {
            forms.addAll(user.getForms());
        }

        groupUsers.forEach(g -> {
            forms.addAll(groupRepository.findById(g.getGroupId()).block().getForms());
        });

        return forms.stream().collect(toMap(Form::getFormCode, Function.identity(), (o1, o2) -> {
                    o2.getActions().forEach(e -> {
                        if (!o1.getActions().contains(e)) {
                            o1.getActions().add(e);
                        }
                    });
                    return o1;
                })).values().stream().map(f -> {
                    f.setId(null);
                    return f;
                }).collect(toList());
    }
}
