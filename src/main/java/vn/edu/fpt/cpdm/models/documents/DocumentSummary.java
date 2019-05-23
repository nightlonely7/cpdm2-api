package vn.edu.fpt.cpdm.models.documents;

import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DocumentSummary {

    Integer getId();
    String getCode();
    String getTitle();
    OutsiderSummary getOutsider();
    String getSummary();
    LocalDate getArrivalDate();
    LocalDate getEffectiveDate();
    LocalDate getEffectiveEndDate();
    LocalDateTime getCreatedTime();
    Boolean getProcessed();
    Boolean getStartedProcessing();
}
