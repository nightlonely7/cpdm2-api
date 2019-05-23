package vn.edu.fpt.cpdm.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.fpt.cpdm.forms.documents.DocumentCreateForm;
import vn.edu.fpt.cpdm.forms.process.FeedbackCreateForm;
import vn.edu.fpt.cpdm.models.documents.DocumentDetail;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;

import java.util.List;

public interface DocumentService {

    Page<DocumentSummary> findAllSummary(Pageable pageable);

    DocumentDetail findDetailById(Integer id);

    DocumentDetail create(DocumentCreateForm documentCreateForm);

    DocumentDetail putIntoProcess(Integer documentId, Integer processId);

    void forwardProcess(Integer documentId, Integer outcomeId, FeedbackCreateForm feedbackCreateForm);

    Page<DocumentSummary> findAllExecutingDocuments(Pageable pageable);
}
