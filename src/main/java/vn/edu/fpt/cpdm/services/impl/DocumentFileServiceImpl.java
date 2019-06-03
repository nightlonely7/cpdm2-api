package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.DocumentEntity;
import vn.edu.fpt.cpdm.entities.DocumentFileEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.exceptions.EntityNotFoundException;
import vn.edu.fpt.cpdm.forms.documents.files.DocumentFileCreateForm;
import vn.edu.fpt.cpdm.models.documents.files.DocumentFileDetail;
import vn.edu.fpt.cpdm.repositories.DocumentFileRepository;
import vn.edu.fpt.cpdm.repositories.DocumentRepository;
import vn.edu.fpt.cpdm.services.AuthenticationService;
import vn.edu.fpt.cpdm.services.DocumentFileService;
import vn.edu.fpt.cpdm.services.FileStorageService;

import java.time.LocalDateTime;

@Service
public class DocumentFileServiceImpl implements DocumentFileService {

    private final DocumentRepository documentRepository;
    private final DocumentFileRepository documentFileRepository;
    private final FileStorageService fileStorageService;
    private final AuthenticationService authenticationService;

    @Autowired
    public DocumentFileServiceImpl(DocumentRepository documentRepository,
                                   DocumentFileRepository documentFileRepository,
                                   FileStorageService fileStorageService,
                                   AuthenticationService authenticationService) {
        this.documentRepository = documentRepository;
        this.documentFileRepository = documentFileRepository;
        this.fileStorageService = fileStorageService;
        this.authenticationService = authenticationService;
    }

    @Override
    public DocumentFileDetail create(Integer documentId, DocumentFileCreateForm documentFileCreateForm) {
        DocumentEntity documentEntity = documentRepository.findById(documentId).orElseThrow(
                () -> new EntityNotFoundException(documentId, "Document")
        );
        UserEntity creator = authenticationService.getCurrentLoggedUser();
        DocumentFileEntity documentFileEntity = new DocumentFileEntity();
        documentFileEntity.setCreator(creator);
        documentFileEntity.setDocument(documentEntity);
        documentFileEntity.setFilename(documentFileCreateForm.getFilename());

        String storedFilename = documentFileCreateForm.getFile().getOriginalFilename() +
                "_" + LocalDateTime.now();
        documentFileEntity.setStoredFilename(storedFilename);

        documentFileEntity.setDescription(documentFileCreateForm.getDescription());
        DocumentFileEntity savedDocumentFileEntity = documentFileRepository.save(documentFileEntity);
        DocumentFileDetail savedDocumentFileDetail = documentFileRepository.
                findDetailByIdAndAvailableTrue(savedDocumentFileEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException(savedDocumentFileEntity.getId(), "Document File"));
        fileStorageService.store(documentFileCreateForm.getFile(), documentFileCreateForm.getFilename());
        return savedDocumentFileDetail;
    }
}
