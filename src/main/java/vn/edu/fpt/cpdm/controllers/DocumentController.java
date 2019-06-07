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
import vn.edu.fpt.cpdm.forms.documents.DocumentSearchForm;
import vn.edu.fpt.cpdm.forms.documents.DocumentUpdateForm;
import vn.edu.fpt.cpdm.forms.documents.files.DocumentFileCreateForm;
import vn.edu.fpt.cpdm.forms.process.FeedbackCreateForm;
import vn.edu.fpt.cpdm.models.documents.DocumentDetail;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;
import vn.edu.fpt.cpdm.models.documents.files.DocumentFileDetail;
import vn.edu.fpt.cpdm.models.processes.StepFeedbackSummary;
import vn.edu.fpt.cpdm.services.DocumentFileService;
import vn.edu.fpt.cpdm.services.DocumentService;
import vn.edu.fpt.cpdm.services.StepFeedbackService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/documents")
@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentFileService documentFileService;
    private final StepFeedbackService stepFeedbackService;

    @Autowired
    public DocumentController(DocumentService documentService,
                              DocumentFileService documentFileService,
                              StepFeedbackService stepFeedbackService) {
        this.documentService = documentService;
        this.documentFileService = documentFileService;
        this.stepFeedbackService = stepFeedbackService;
    }

    @GetMapping("/search/executing")
    public ResponseEntity<Page<DocumentSummary>> findAllExecutingDocuments(DocumentSearchForm documentSearchForm,
                                                                           @PageableDefault Pageable pageable) {
        Page<DocumentSummary> documentSummaries = documentService.findAllExecutingDocuments(documentSearchForm, pageable);

        return ResponseEntity.ok(documentSummaries);
    }

    @GetMapping
    public ResponseEntity<Page<DocumentSummary>> findAllCreatedDocuments(
            DocumentSearchForm documentSearchForm,
            @RequestParam(value = "internal", defaultValue = "false") boolean internal,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(documentService.findAllSummary(documentSearchForm, internal, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDetail> findDetailById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(documentService.findDetailById(id));
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<List<DocumentFileDetail>> findFileDetails(@PathVariable("id") Integer documentId) {
        return ResponseEntity.ok(documentFileService.findAllDetailByDocumentId(documentId));
    }

    @GetMapping("/{id}/feedback")
    public ResponseEntity<List<StepFeedbackSummary>> findFeedback(@PathVariable("id") Integer documentId) {
        return ResponseEntity.ok(stepFeedbackService.findAllByDocumentId(documentId));
    }

    @PostMapping
    public ResponseEntity<DocumentDetail> create(@Valid @RequestBody DocumentCreateForm documentCreateForm,
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

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDetail> update(@PathVariable("id") Integer id,
                                                 @Valid @RequestBody DocumentUpdateForm documentUpdateForm,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new ModelNotValidException(message);
        }

        return ResponseEntity.ok(documentService.update(id, documentUpdateForm));
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
