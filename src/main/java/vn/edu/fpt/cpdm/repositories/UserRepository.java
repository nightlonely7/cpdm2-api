package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DepartmentEntity;
import vn.edu.fpt.cpdm.entities.RoleEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.models.users.UserBasic;
import vn.edu.fpt.cpdm.models.users.UserSummary;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserBasic> findBasicByUsername(String username);
    List<UserSummary> findAllBy();

    boolean existsByUsername(String username);

    Optional<UserBasic> findByIdAndAvailableTrue(Integer id);
    List<UserEntity> findAllByDepartment(DepartmentEntity departmentEntity);
    Optional<UserEntity> findByRole(RoleEntity roleEntity);
    Optional<UserEntity> findByRoleAndDepartment(RoleEntity roleEntity, DepartmentEntity departmentEntity);
    List<UserBasic> findAllByRole_NameAndAvailableTrue(String roleName);
    Page<UserSummary> findAllByRole_NameNot(String roleName, Pageable pageable);
    Page<UserSummary> findAllByRole_NameAndDepartment_Id(String roleName, Integer departmentId, Pageable pageable);
    List<UserSummary> findAllByDepartment_IdAndAvailableTrue(Integer departmentId);
}
