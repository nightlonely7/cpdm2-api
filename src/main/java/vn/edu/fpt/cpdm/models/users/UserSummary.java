package vn.edu.fpt.cpdm.models.users;

import lombok.AllArgsConstructor;
import vn.edu.fpt.cpdm.models.departments.DepartmentSummary;

public interface UserSummary extends UserBasic{
    DepartmentSummary getDepartment();
    String getProcessRole();
    Boolean getAvailable();
}
