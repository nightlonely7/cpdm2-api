package vn.edu.fpt.cpdm.models.documents;

import vn.edu.fpt.cpdm.models.IdOnlyModel;
import vn.edu.fpt.cpdm.models.processes.ProcessStepSummary;

import java.time.LocalDateTime;

public interface DocumentDetail extends DocumentSummary {

    String getDecree();

    String getDetail();

    LocalDateTime getLastModifiedTime();

    ProcessStepSummary getCurrentStep();

    IdOnlyModel getProcess();

}


