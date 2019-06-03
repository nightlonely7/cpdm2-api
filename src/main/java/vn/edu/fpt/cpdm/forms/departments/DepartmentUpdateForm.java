package vn.edu.fpt.cpdm.forms.departments;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DepartmentUpdateForm {
    @NotBlank
    String code;
    @NotBlank
    String name;
    String description;
}
