package vn.edu.fpt.cpdm.forms.documents;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class DocumentCreateForm {

    @NotBlank
    private String code;

    private Integer typeId;

    @NotNull
    private Integer outsiderId;

    @NotBlank
    private String title;

    private String summary;

    private String decree;

    private String detail;

    private LocalDate arrivalDate;

    private LocalDate effectiveDate;

    private LocalDate effectiveEndDate;

    @NotNull
    private Boolean internal;
}
