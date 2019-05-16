package vn.edu.fpt.cpdm.models.documents;

import java.time.LocalDateTime;

public interface DocumentSummary {

    String getTitle();
    String getSummary();
    LocalDateTime getCreatedTime();
    LocalDateTime getLastModifiedTime();
}
