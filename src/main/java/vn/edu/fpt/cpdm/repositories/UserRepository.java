package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.models.users.UserBasic;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserBasic> findBasicByUsername(String username);
    List<UserBasic> findAllBy();

    boolean existsByUsername(String username);

    Optional<UserBasic> findByIdAndAvailableTrue(Integer id);
}
