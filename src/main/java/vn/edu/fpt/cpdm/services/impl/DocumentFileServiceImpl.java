package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.DocumentEntity;
import vn.edu.fpt.cpdm.entities.DocumentFileEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.exceptions.EntityIdNotFoundException;
import vn.edu.fpt.cpdm.forms.documents.files.DocumentFileCreateForm;
import vn.edu.fpt.cpdm.models.documents.files.DocumentFileDetail;
import vn.edu.fpt.cpdm.repositories.DocumentFileRepository;
import vn.edu.fpt.cpdm.repositories.DocumentRepository;
import vn.edu.fpt.cpdm.services.AuthenticationService;
import vn.edu.fpt.cpdm.services.DocumentFileService;
import vn.edu.fpt.cpdm.services.FileStorageService;

import java.time.LocalDateTime;
import java.util.List;

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
                () -> new EntityIdNotFoundException(documentId, "Document")
        );
        UserEntity creator = authenticationService.getCurrentLoggedUser();
        DocumentFileEntity documentFileEntity = new DocumentFileEntity();
        documentFileEntity.setCreator(creator);
        documentFileEntity.setDocument(documentEntity);
        documentFileEntity.setFilename(documentFileCreateForm.getFilename());

        LocalDateTime now = LocalDateTime.now();
        String storedFilename = creator.getUsername() + "_" + now.toString()
                .replaceFirst("T", " ")
                .replaceFirst(":", "h")
                .replaceFirst(":", "m")
                .replaceFirst("\\.", "s")
                + "_" + documentFileCreateForm.getFile().getOriginalFilename();
        documentFileEntity.setStoredFilename(storedFilename);

        documentFileEntity.setDescription(documentFileCreateForm.getDescription());
        documentFileEntity.setCreatedTime(now);
        documentFileEntity.setLastModifiedTime(now);
        DocumentFileEntity savedDocumentFileEntity = documentFileRepository.save(documentFileEntity);
        DocumentFileDetail savedDocumentFileDetail = documentFileRepository.
                findDetailByIdAndAvailableTrue(savedDocumentFileEntity.getId())
                .orElseThrow(() -> new EntityIdNotFoundException(savedDocumentFileEntity.getId(), "Document File"));
        fileStorageService.store(documentFileCreateForm.getFile(), storedFilename);
        return savedDocumentFileDetail;
    }

    @Override
    public List<DocumentFileDetail> findAllDetailByDocumentId(Integer documentId) {
        DocumentEntity documentEntity = documentRepository.findById(documentId).orElseThrow(
                () -> new EntityIdNotFoundException(documentId, "Document")
        );
        return documentFileRepository.findAllDetailByDocumentAndAvailableTrue(documentEntity);
    }

    @Override
    public Resource download(Integer id) {
        DocumentFileEntity documentFileEntity = documentFileRepository.findById(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Document File")
        );
        Resource resource = fileStorageService.loadFileAsResource(documentFileEntity.getStoredFilename());
        return resource;
    }
}
