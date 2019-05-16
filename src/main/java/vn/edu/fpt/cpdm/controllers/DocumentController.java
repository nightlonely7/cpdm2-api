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
import vn.edu.fpt.cpdm.forms.process.FeedbackCreateForm;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;
import vn.edu.fpt.cpdm.services.DocumentService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/documents")
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/executing")
    public ResponseEntity<List<DocumentSummary>> findAllExecutingDocuments() {

        List<DocumentSummary> documentSummaries = documentService.findAllExecutingDocuments();

        return ResponseEntity.ok(documentSummaries);
    }

    @GetMapping("/creates")
    public ResponseEntity<Page<DocumentSummary>> findAllCreatedDocuments(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(documentService.findAllSummary(pageable));
    }

    @PostMapping
    public ResponseEntity createDocument(@Valid @RequestBody DocumentCreateForm documentCreateForm,
                                         BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new ModelNotValidException(message);
        }
        documentService.create(documentCreateForm);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/put_into_process")
    public ResponseEntity putDocumentIntoProcess(@PathVariable("id") Integer documentId) {
        documentService.putIntoProcess(documentId);
        return ResponseEntity.ok().build();
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
