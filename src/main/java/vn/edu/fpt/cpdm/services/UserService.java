package vn.edu.fpt.cpdm.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.fpt.cpdm.forms.users.UserCreateForm;
import vn.edu.fpt.cpdm.forms.users.UserUpdateForm;
import vn.edu.fpt.cpdm.models.users.UserBasic;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserBasic create(UserCreateForm userCreateForm);

    UserBasic update(Integer id, UserUpdateForm userUpdateForm);

    UserBasic updatePassword();

    UserBasic findBasicByUsername(String username);

    UserBasic findBasicById(Integer id);

    List<UserBasic> findAllExecutor();

    UserBasic findManagerByDepartmentId(Integer departmentId);

    List<UserBasic> findAllDirector();
}
