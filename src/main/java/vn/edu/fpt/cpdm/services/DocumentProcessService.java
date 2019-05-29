package vn.edu.fpt.cpdm.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.fpt.cpdm.forms.process.DocumentProcessCreateForm;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessDetail;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessSummary;

public interface DocumentProcessService {

    DocumentProcessDetail findDetailById(Integer id);

    DocumentProcessDetail create(DocumentProcessCreateForm documentProcessCreateForm);

    Page<DocumentProcessSummary> findAllSummary(Pageable pageable);
}
