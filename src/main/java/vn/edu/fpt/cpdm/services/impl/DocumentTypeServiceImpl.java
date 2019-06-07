package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.models.NameOnlyModel;
import vn.edu.fpt.cpdm.repositories.DocumentTypeRepository;
import vn.edu.fpt.cpdm.services.DocumentTypeService;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    @Autowired
    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public List<NameOnlyModel> findAll() {
        return documentTypeRepository.findAllBy();
    }
}
