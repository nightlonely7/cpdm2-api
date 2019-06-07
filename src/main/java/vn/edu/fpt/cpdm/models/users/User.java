package vn.edu.fpt.cpdm.models.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aspectj.lang.annotation.DeclareAnnotation;
import vn.edu.fpt.cpdm.models.departments.DepartmentSummary;
import vn.edu.fpt.cpdm.models.roles.RoleBasic;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private Integer id;

    private String userName;

    private String password;

    private String processRole;

    private DepartmentSummary department;

    private RoleBasic role;

    @NotNull
    private boolean available;
}
