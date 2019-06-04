package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.cpdm.exceptions.ModelNotValidException;
import vn.edu.fpt.cpdm.forms.documents.DocumentCreateForm;
import vn.edu.fpt.cpdm.forms.documents.files.DocumentFileCreateForm;
import vn.edu.fpt.cpdm.forms.process.FeedbackCreateForm;
import vn.edu.fpt.cpdm.models.documents.DocumentDetail;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;
import vn.edu.fpt.cpdm.models.documents.files.DocumentFileDetail;
import vn.edu.fpt.cpdm.services.DocumentFileService;
import vn.edu.fpt.cpdm.services.DocumentService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/documents")
@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentFileService documentFileService;

    @Autowired
    public DocumentController(DocumentService documentService,
                              DocumentFileService documentFileService) {
        this.documentService = documentService;
        this.documentFileService = documentFileService;
    }

    @GetMapping("/search/executing")
    public ResponseEntity<Page<DocumentSummary>> findAllExecutingDocuments(@PageableDefault Pageable pageable) {

        Page<DocumentSummary> documentSummaries = documentService.findAllExecutingDocuments(pageable);

        return ResponseEntity.ok(documentSummaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDetail> findDetailById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(documentService.findDetailById(id));
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<List<DocumentFileDetail>> findFileDetails(@PathVariable("id") Integer documentId) {
        return ResponseEntity.ok(documentFileService.findAllDetailByDocumentId(documentId));
    }

    @GetMapping("/search/creates")
    public ResponseEntity<Page<DocumentSummary>> findAllCreatedDocuments(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(documentService.findAllSummary(pageable));
    }

    @PostMapping
    public ResponseEntity<DocumentDetail> createDocument(@Valid @RequestBody DocumentCreateForm documentCreateForm,
                                                         BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new ModelNotValidException(message);
        }

        return ResponseEntity.ok(documentService.create(documentCreateForm));
    }

    @PostMapping("/{id}/files")
    public ResponseEntity<DocumentFileDetail> createDocumentFile(
            @PathVariable("id") Integer documentId,
            @Valid DocumentFileCreateForm documentFileCreateForm,
            BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new ModelNotValidException(message);
        }

        return ResponseEntity.ok(documentFileService.create(documentId, documentFileCreateForm));
    }

    @PatchMapping("/{id}/put_into_process")
    public ResponseEntity<DocumentDetail> putDocumentIntoProcess(@PathVariable("id") Integer documentId,
                                                                 @RequestParam("processId") Integer processId) {

        return ResponseEntity.ok(documentService.putIntoProcess(documentId, processId));
    }

    @PatchMapping("/{id}/forward_process")
    public ResponseEntity forwardProcessDocument(@PathVariable("id") Integer documentId,
                                                 @RequestParam("outcomeId") Integer outcomeId,
                                                 @Valid @RequestBody FeedbackCreateForm feedbackCreateForm,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new ModelNotValidException(message);
        }
        documentService.forwardProcess(documentId, outcomeId, feedbackCreateForm);
        return ResponseEntity.ok().build();
    }
}
