package vn.edu.fpt.cpdm.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.forms.users.UserCreateForm;
import vn.edu.fpt.cpdm.forms.users.UserUpdateForm;
import vn.edu.fpt.cpdm.models.users.User;
import vn.edu.fpt.cpdm.models.users.UserBasic;
import vn.edu.fpt.cpdm.models.users.UserSummary;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserBasic create(UserCreateForm userCreateForm);

    UserBasic update(Integer id, UserUpdateForm userUpdateForm);

    UserBasic updatePassword();

    UserBasic findBasicByUsername(String username);

    UserBasic findBasicById(Integer id);

    List<UserBasic> findAllExecutor();

    UserBasic findManagerByDepartmentId(Integer departmentId);

    List<UserBasic> findAllDirector();

    Page<UserSummary> findAllUserForDirector(Pageable pageable);

    Page<UserSummary> findAllStaffForManager(Pageable pageable);

    User findUserByUsername(String username);

    User active(Integer id);

    User deActive(Integer id);
}
