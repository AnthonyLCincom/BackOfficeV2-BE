package com.lcincom.bo.service;

import com.lcincom.bo.model.Form;
import com.lcincom.bo.model.User;
import com.lcincom.bo.repository.FormRepository;
import com.lcincom.bo.repository.GroupRepository;
import com.lcincom.bo.repository.GroupUsersRepository;
import com.lcincom.bo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class CreateAgentServiceImpl implements CreateAgentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupUsersRepository groupUsersRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FormRepository formRepository;

    private String ACCESS_CONTROL_FORM = "AC";

    @Override
    public String createNewAgentByLoginId(String loginId) {

        Mono<User> user = userRepository.findByLoginId(loginId);

        if (null != user.block()) {
            return "Tên đăng nhập đại lý đã tồn tại.";
        }

        List<String> actions = Arrays.asList("VIEW", "ADD", "EDIT", "DELETE");

        Flux<Form> forms = formRepository.findByAllAndIsActive();

        Flux<Form> formsForAgent = forms
                .filter(e -> !e.getFormCode().equals(ACCESS_CONTROL_FORM))
                .map(f -> {
                    f.setActions(actions);
                    return f;
                });

        User newUser = User.builder()
                .loginId(loginId)
                .isActive(true)
                .forms(formsForAgent.collectList().block())
                .build();

        Mono<User> createAgent = userRepository.save(newUser);
        if (createAgent.block() == null) {
            return "Tạo đại lý bị lỗi.";
        }

        return ("Tạo đại lý " + loginId +" thành công.");
    }
}
