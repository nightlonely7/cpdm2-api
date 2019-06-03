package vn.edu.fpt.cpdm.forms.outsiders;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OutsiderUpdateForm {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String contactData;
}
