package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.forms.process.DocumentProcessCreateForm;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessSummary;

import java.util.List;

public interface DocumentProcessService {

    void create(DocumentProcessCreateForm documentProcessCreateForm);

    List<DocumentProcessSummary> findAllSummary();
}
