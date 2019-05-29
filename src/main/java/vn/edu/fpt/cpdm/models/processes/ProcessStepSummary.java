package vn.edu.fpt.cpdm.models.processes;

import vn.edu.fpt.cpdm.models.users.UserBasic;

public interface ProcessStepSummary {
    Integer getId();
    String getName();
    UserBasic getExecutor();
}
