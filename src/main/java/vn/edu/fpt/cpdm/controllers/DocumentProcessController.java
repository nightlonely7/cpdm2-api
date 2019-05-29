package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.cpdm.exceptions.ModelNotValidException;
import vn.edu.fpt.cpdm.forms.process.DocumentProcessCreateForm;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessDetail;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessSummary;
import vn.edu.fpt.cpdm.services.DocumentProcessService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;

@RequestMapping("/document_processes")
@RestController
public class DocumentProcessController {

    private final DocumentProcessService documentProcessService;

    @Autowired
    public DocumentProcessController(DocumentProcessService documentProcessService) {
        this.documentProcessService = documentProcessService;
    }

    @GetMapping
    public ResponseEntity<Page<DocumentProcessSummary>> findAllSumary(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(documentProcessService.findAllSummary(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentProcessDetail> findDetailById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(documentProcessService.findDetailById(id));
    }

    @PostMapping
    public ResponseEntity<DocumentProcessDetail> createDocumentProcess(@Valid @RequestBody DocumentProcessCreateForm documentProcessCreateForm,
                                                                       BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new ModelNotValidException(message);
        }
        DocumentProcessDetail documentProcessDetail = documentProcessService.create(documentProcessCreateForm);
        return ResponseEntity.ok(documentProcessDetail);
    }
}
