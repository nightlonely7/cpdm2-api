package vn.edu.fpt.cpdm.forms.documents;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class DocumentCreateForm {

    @NotNull
    private String code;

    @NotNull
    private Integer outsiderId;

    @NotNull
    private String title;

    private String summary;

    private String decree;

    private String detail;

    @NotNull
    private LocalDate arrivalDate;

    private LocalDate effectiveDate;

    private LocalDate effectiveEndDate;
}
