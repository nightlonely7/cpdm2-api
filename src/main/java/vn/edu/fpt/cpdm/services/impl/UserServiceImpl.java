package vn.edu.fpt.cpdm.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.DepartmentEntity;
import vn.edu.fpt.cpdm.entities.RoleEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.exceptions.ConflictException;
import vn.edu.fpt.cpdm.exceptions.EntityIdNotFoundException;
import vn.edu.fpt.cpdm.exceptions.NotFoundException;
import vn.edu.fpt.cpdm.forms.users.UserCreateForm;
import vn.edu.fpt.cpdm.forms.users.UserUpdateForm;
import vn.edu.fpt.cpdm.models.users.User;
import vn.edu.fpt.cpdm.models.users.UserBasic;
import vn.edu.fpt.cpdm.models.users.UserSummary;
import vn.edu.fpt.cpdm.repositories.DepartmentRepository;
import vn.edu.fpt.cpdm.repositories.RoleRepository;
import vn.edu.fpt.cpdm.repositories.UserRepository;
import vn.edu.fpt.cpdm.services.AuthenticationService;
import vn.edu.fpt.cpdm.services.UserService;

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

        UserEntity creator = authenticationService.getCurrentLoggedUser();

        if (userRepository.existsByUsername(userCreateForm.getUsername())) {
            throw new ConflictException("This username '" + userCreateForm.getUsername() + "' is already exists!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userCreateForm.getUsername());
        userEntity.setProcessRole(userCreateForm.getProcessRole());

        // encode password
        String encodedPassword = passwordEncoder.encode(userCreateForm.getPassword());
        userEntity.setPassword(encodedPassword);

        userEntity.setDepartment(creator.getDepartment());

        final String ROLE_STAFF = "ROLE_STAFF";
        RoleEntity role = roleRepository.findByName(ROLE_STAFF).orElseThrow(
                () -> new EntityIdNotFoundException(ROLE_STAFF, "Role")
        );

        userEntity.setRole(role);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userRepository.findByIdAndAvailableTrue(savedUserEntity.getId()).orElseThrow(
                () -> new EntityIdNotFoundException(savedUserEntity.getId(), "User")
        );
    }

    @Override
    public UserBasic update(Integer id, UserUpdateForm userUpdateForm) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "User")
        );

        DepartmentEntity department = departmentRepository.findById(userUpdateForm.getDepartmentId()).orElseThrow(
                () -> new EntityIdNotFoundException(userUpdateForm.getDepartmentId(), "Department")
        );

        RoleEntity role = roleRepository.findById(userUpdateForm.getRoleId()).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Role")
        );
        RoleEntity roleManager = roleRepository.findById(userUpdateForm.getRoleId()).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Role")
        );

        String staff = "ROLE_STAFF";
        RoleEntity roleStaff = roleRepository.findByName(staff).orElseThrow(
                () -> new NotFoundException("Role with name '" + staff + "' is not found!")
        );
        UserEntity oldManager = userRepository.findByRoleAndDepartment(roleManager, department).orElseThrow(
                () -> new NotFoundException("abcxyz")
        );
        oldManager.setRole(roleStaff);
        userEntity.setRole(roleManager);
        userEntity.setDepartment(department);
        userRepository.save(userEntity);

        return userRepository.findByIdAndAvailableTrue(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "User")
        );
    }

    @Override
    public User active(Integer id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "User")
        );

        userEntity.setAvailable(true);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(savedUserEntity,User.class);
    }

    @Override
    public User deActive(Integer id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "User")
        );

        userEntity.setAvailable(false);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(savedUserEntity,User.class);
    }

    @Override
    public List<UserSummary> findAllSummaryByDepartment(Integer departmentId) {
        return userRepository.findAllByDepartment_IdAndAvailableTrue(departmentId);
    }

    @Override
    public UserBasic promo(Integer id, Integer departmentId) {
        UserBasic manager = this.findManagerByDepartmentId(departmentId);
        UserEntity managerEntity = userRepository.findById(manager.getId()).get();
        managerEntity.setRole(roleRepository.findByName("ROLE_STAFF").get());
        userRepository.save(managerEntity);
        UserEntity newManagerEntity = userRepository.findById(id).get();
        newManagerEntity.setRole(roleRepository.findByName("ROLE_MANAGER").get());
        UserEntity savedNewManagerEntity = userRepository.save(newManagerEntity);
        return userRepository.findBasicByUsername(savedNewManagerEntity.getUsername()).get();
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
                () -> new EntityIdNotFoundException(id, "User")
        );
    }

    @Override
    public List<UserSummary> findAllExecutor() {
        return userRepository.findAllBy();
    }

    @Override
    public UserBasic findManagerByDepartmentId(Integer departmentId) {
        RoleEntity roleEntity = roleRepository.findByName("ROLE_MANAGER").orElseThrow(
                () -> new EntityIdNotFoundException("ROLE_MANAGER", "Role")
        );
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElseThrow(
                () -> new EntityIdNotFoundException(departmentId, "Department")
        );
        UserEntity oldManager = userRepository.findByRoleAndDepartment(roleEntity, departmentEntity).orElseThrow(
                () -> new NotFoundException("ABCXYZ")
        );
        return userRepository.findByIdAndAvailableTrue(oldManager.getId()).orElseThrow(
                () -> new EntityIdNotFoundException(oldManager.getId(), "User")
        );
    }

    @Override
    public List<UserBasic> findAllDirector() {
        return userRepository.findAllByRole_NameAndAvailableTrue("ROLE_ADMIN");
    }

    @Override
    public Page<UserSummary> findAllUserForDirector(Pageable pageable) {
        return userRepository.findAllByRole_NameNot("ROLE_ADMIN", pageable);
    }

    @Override
    public Page<UserSummary> findAllStaffForManager(Pageable pageable) {
        UserEntity manager = authenticationService.getCurrentLoggedUser();
        return userRepository.findAllByRole_NameAndDepartment_Id("ROLE_STAFF",manager.getDepartment().getId(), pageable);
    }

    @Override
    public User findUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityIdNotFoundException(username, "User")
        );
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, User.class);
    }
}
