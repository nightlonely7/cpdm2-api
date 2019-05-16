package vn.edu.fpt.cpdm.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.fpt.cpdm.forms.documents.DocumentCreateForm;
import vn.edu.fpt.cpdm.forms.process.FeedbackCreateForm;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;

import java.util.List;

public interface DocumentService {

    Page<DocumentSummary> findAllSummary(Pageable pageable);

    void create(DocumentCreateForm documentCreateForm);

    void putIntoProcess(Integer documentId);

    void forwardProcess(Integer documentId, Integer outcomeId, FeedbackCreateForm feedbackCreateForm);

    List<DocumentSummary> findAllExecutingDocuments();
}
