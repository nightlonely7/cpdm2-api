package vn.edu.fpt.cpdm.forms.departments;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class DepartmentCreateForm {
    @NotBlank
    String code;
    @NotBlank
    String name;
    String description;
}
