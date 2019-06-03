package vn.edu.fpt.cpdm.models.documents.files;

import vn.edu.fpt.cpdm.models.documents.DocumentSummary;
import vn.edu.fpt.cpdm.models.users.UserBasic;

import java.time.LocalDateTime;

public interface DocumentFileDetail {
    Integer getId();
    DocumentSummary getDocument();
    String getFilename();
    String getStoredFilename();
    String getDescription();
    UserBasic getCreator();
    LocalDateTime getCreatedTime();
    LocalDateTime getLastModifiedTime();
}
