package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DepartmentEntity;
import vn.edu.fpt.cpdm.models.departments.DepartmentSummary;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Integer> {
    Page<DepartmentSummary> findAllBy(Pageable pageable);
    Boolean existsByCode(String code);
    Boolean existsByName(String name);
    DepartmentSummary findSummaryById(Integer id);
    DepartmentEntity findByName(String name);
}
