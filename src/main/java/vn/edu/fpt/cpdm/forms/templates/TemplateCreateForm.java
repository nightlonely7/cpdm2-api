package vn.edu.fpt.cpdm.forms.templates;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TemplateCreateForm {

    @NotBlank
    private String name;

    @NotBlank
    private String template;
}
