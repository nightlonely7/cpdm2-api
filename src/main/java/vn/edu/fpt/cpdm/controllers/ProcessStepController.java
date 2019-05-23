package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.cpdm.models.processes.StepOutcomeSummary;
import vn.edu.fpt.cpdm.services.StepOutcomeService;

import java.util.List;

@RequestMapping("/steps")
@RestController
public class ProcessStepController {

    private final StepOutcomeService stepOutcomeService;

    @Autowired
    public ProcessStepController(StepOutcomeService stepOutcomeService) {
        this.stepOutcomeService = stepOutcomeService;
    }

    @GetMapping("/{id}/outcomes")
    public ResponseEntity<List<StepOutcomeSummary>> findAllOutcome(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(stepOutcomeService.findAllSummaryByStep_Id(id));
    }

}
