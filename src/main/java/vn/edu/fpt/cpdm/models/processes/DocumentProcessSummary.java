package vn.edu.fpt.cpdm.models.processes;

import java.time.LocalDateTime;

public interface DocumentProcessSummary {
    Integer getId();
    String getName();
    String getDescription();
    LocalDateTime getCreatedTime();
}
