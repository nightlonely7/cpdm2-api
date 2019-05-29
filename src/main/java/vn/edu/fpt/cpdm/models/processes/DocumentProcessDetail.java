package vn.edu.fpt.cpdm.models.processes;

import vn.edu.fpt.cpdm.models.IdOnlyModel;

import java.util.List;

public interface DocumentProcessDetail extends DocumentProcessSummary {

    IdOnlyModel getFirstStep();

    List<ProcessStepDetail> getSteps();
}
