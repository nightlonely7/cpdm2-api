package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.forms.documents.files.DocumentFileCreateForm;

public interface DocumentFileService {
    void create(Integer documentId, DocumentFileCreateForm documentFileCreateForm);
}
