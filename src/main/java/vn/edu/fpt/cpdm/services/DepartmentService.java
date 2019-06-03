package vn.edu.fpt.cpdm.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.fpt.cpdm.entities.DepartmentEntity;
import vn.edu.fpt.cpdm.forms.departments.DepartmentCreateForm;
import vn.edu.fpt.cpdm.forms.departments.DepartmentUpdateForm;
import vn.edu.fpt.cpdm.models.departments.DepartmentSummary;

import java.util.Optional;

public interface DepartmentService {
    Page<DepartmentSummary> findAllSummary(Pageable pageable);
    DepartmentSummary findSummaryById(Integer id);
    DepartmentSummary create(DepartmentCreateForm departmentCreateForm);
    DepartmentSummary update(Integer id, DepartmentUpdateForm departmentUpdateForm);
}
