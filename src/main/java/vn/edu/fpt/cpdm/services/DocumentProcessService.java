package vn.edu.fpt.cpdm.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.fpt.cpdm.forms.process.DocumentProcessCreateForm;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessSummary;

public interface DocumentProcessService {

    void create(DocumentProcessCreateForm documentProcessCreateForm);

    Page<DocumentProcessSummary> findAllSummary(Pageable pageable);
}
