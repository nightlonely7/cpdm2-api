package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.forms.documents.files.DocumentFileCreateForm;
import vn.edu.fpt.cpdm.models.documents.files.DocumentFileDetail;

public interface DocumentFileService {
    DocumentFileDetail create(Integer documentId, DocumentFileCreateForm documentFileCreateForm);
}
