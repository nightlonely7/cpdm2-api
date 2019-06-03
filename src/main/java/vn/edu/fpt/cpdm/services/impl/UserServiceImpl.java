package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.DepartmentEntity;
import vn.edu.fpt.cpdm.entities.RoleEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.exceptions.ConflictException;
import vn.edu.fpt.cpdm.exceptions.EntityNotFoundException;
import vn.edu.fpt.cpdm.forms.users.UserCreateForm;
import vn.edu.fpt.cpdm.forms.users.UserUpdateForm;
import vn.edu.fpt.cpdm.models.users.UserBasic;
import vn.edu.fpt.cpdm.repositories.UserRepository;
import vn.edu.fpt.cpdm.services.AuthenticationService;
import vn.edu.fpt.cpdm.services.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username '" + username + "' is not found!")
        );

    }

    @Override
    public UserBasic create(UserCreateForm userCreateForm) {
        if (userRepository.existsByUsername(userCreateForm.getUsername())) {
            throw new ConflictException("This username '" + userCreateForm.getUsername() + "' is already exists!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userCreateForm.getUsername());

        // encode password
        String encodedPassword = passwordEncoder.encode(userCreateForm.getPassword());
        userEntity.setPassword(encodedPassword);

        //fix
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(userCreateForm.getDepartmentId());


        userEntity.setDepartment(departmentEntity);

        //fix
        RoleEntity role = new RoleEntity();
        role.setId(4);


        userEntity.setRole(role);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userRepository.findByIdAndAvailableTrue(savedUserEntity.getId()).orElseThrow(
                () -> new EntityNotFoundException(savedUserEntity.getId(), "User")
        );
    }

    @Override
    public UserBasic update(Integer id, UserUpdateForm userUpdateForm) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id, "User")
        );
        DepartmentEntity department = new DepartmentEntity();

        //fix
        if (userUpdateForm.getDepartmentId() != null) {
            department.setId(userUpdateForm.getDepartmentId());
            userEntity.setDepartment(department);
        }

        //fix
        RoleEntity role = new RoleEntity();
        if (userUpdateForm.getRoleId() != null) {
            role.setId(userUpdateForm.getRoleId());
            userEntity.setRole(role);
        }
        userRepository.save(userEntity);

        return userRepository.findByIdAndAvailableTrue(id).orElseThrow(
                () -> new EntityNotFoundException(id, "User")
        );
    }

    @Override
    public UserBasic updatePassword() {
        return null;
    }

    @Override
    public UserBasic findBasicByUsername(String username) {
        return userRepository.findBasicByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username '" + username + "' is not found!")
        );
    }

    @Override
    public UserBasic findBasicById(Integer id) {
        return userRepository.findByIdAndAvailableTrue(id).orElseThrow(
                () -> new EntityNotFoundException(id, "User")
        );
    }

    @Override
    public List<UserBasic> findAllExecutor() {
        return userRepository.findAllBy();
    }
}
