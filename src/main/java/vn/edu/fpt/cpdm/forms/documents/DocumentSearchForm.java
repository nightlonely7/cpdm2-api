package vn.edu.fpt.cpdm.forms.documents;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DocumentSearchForm {
    private String code;
    private String title;
    private String summary;
    private String decree;
    private String detail;
    private String outsiderCode;
    private String outsiderName;
    private String outsiderContactData;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdTimeFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdTimeTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastModifiedTimeFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastModifiedTimeTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate arrivalDateFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate arrivalDateTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate effectiveDateFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate effectiveDateTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate effectiveEndDateFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate effectiveEndDateTo;
    private Boolean processed;
    private Boolean startedProcessing;
}
