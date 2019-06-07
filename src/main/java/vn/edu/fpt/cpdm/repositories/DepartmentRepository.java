package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DepartmentEntity;
import vn.edu.fpt.cpdm.models.departments.DepartmentSummary;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Integer> {
    Page<DepartmentSummary> findAllBy(Pageable pageable);
    List<DepartmentSummary> findAllBy();
    Boolean existsByCode(String code);
    Boolean existsByName(String name);
    DepartmentSummary findSummaryById(Integer id);
    DepartmentEntity findByName(String name);
}
