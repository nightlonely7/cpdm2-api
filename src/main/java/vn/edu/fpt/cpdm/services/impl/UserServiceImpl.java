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
import vn.edu.fpt.cpdm.exceptions.NotFoundException;
import vn.edu.fpt.cpdm.forms.users.UserCreateForm;
import vn.edu.fpt.cpdm.forms.users.UserUpdateForm;
import vn.edu.fpt.cpdm.models.users.UserBasic;
import vn.edu.fpt.cpdm.repositories.DepartmentRepository;
import vn.edu.fpt.cpdm.repositories.RoleRepository;
import vn.edu.fpt.cpdm.repositories.UserRepository;
import vn.edu.fpt.cpdm.services.AuthenticationService;
import vn.edu.fpt.cpdm.services.UserService;

import javax.management.relation.Role;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationService authenticationService, RoleRepository roleRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
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

        DepartmentEntity departmentEntity = departmentRepository.findById(userCreateForm.getDepartmentId()).orElseThrow(
                () -> new EntityNotFoundException(userCreateForm.getDepartmentId(), "Department")
        );

        userEntity.setDepartment(departmentEntity);

        RoleEntity role = roleRepository.findByName("ROLE_STAFF").orElseThrow(
                () -> new EntityNotFoundException("ROLE_STAFF", "Role")
        );

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

        DepartmentEntity department = departmentRepository.findById(userUpdateForm.getDepartmentId()).orElseThrow(
                () -> new EntityNotFoundException(userUpdateForm.getDepartmentId(), "Department")
        );

        RoleEntity roleManager = roleRepository.findById(userUpdateForm.getRoleId()).orElseThrow(
                () -> new EntityNotFoundException(id, "Role")
        );
        RoleEntity roleStaff = roleRepository.findByName("ROLE_STAFF").orElseThrow(
                () -> new EntityNotFoundException("ROLE_STAFF", "Role")
        );
        UserEntity oldManager = userRepository.findByRoleAndDepartment(roleManager, department).orElseThrow(
                () -> new NotFoundException("abcxyz")
        );
        oldManager.setRole(roleStaff);
        userEntity.setRole(roleManager);
        userEntity.setDepartment(department);
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

    @Override
    public UserBasic findManagerByDepartmentId(Integer departmentId) {
        RoleEntity roleEntity = roleRepository.findByName("ROLE_MANAGER").orElseThrow(
                () -> new EntityNotFoundException("ROLE_MANAGER", "Role")
        );
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElseThrow(
                () -> new EntityNotFoundException(departmentId, "Department")
        );
        UserEntity oldManager = userRepository.findByRoleAndDepartment(roleEntity, departmentEntity).orElseThrow(
                () -> new NotFoundException("ABCXYZ")
        );
        return userRepository.findByIdAndAvailableTrue(oldManager.getId()).orElseThrow(
                () -> new EntityNotFoundException(oldManager.getId(), "User")
        );
    }
}
