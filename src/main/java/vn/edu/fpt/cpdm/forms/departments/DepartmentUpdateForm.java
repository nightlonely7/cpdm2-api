package vn.edu.fpt.cpdm.forms.departments;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DepartmentUpdateForm {
    String code;
    String name;
    String description;
    Boolean available;
}
