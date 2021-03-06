package vn.edu.fpt.cpdm.services;

import org.springframework.core.io.Resource;
import vn.edu.fpt.cpdm.forms.documents.files.DocumentFileCreateForm;
import vn.edu.fpt.cpdm.models.documents.files.DocumentFileDetail;

import java.util.List;

public interface DocumentFileService {
    DocumentFileDetail create(Integer documentId, DocumentFileCreateForm documentFileCreateForm);

    List<DocumentFileDetail> findAllDetailByDocumentId(Integer documentId);

    Resource download(Integer id);
}
