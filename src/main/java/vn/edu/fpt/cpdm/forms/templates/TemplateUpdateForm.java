package vn.edu.fpt.cpdm.forms.templates;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TemplateUpdateForm {

    @NotBlank
    private String name;

    @NotBlank
    private String template;
}
