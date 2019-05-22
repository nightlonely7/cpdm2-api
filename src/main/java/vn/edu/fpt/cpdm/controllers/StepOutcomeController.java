package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.cpdm.services.StepOutcomeService;

@RequestMapping("/outcomes")
@RestController
public class StepOutcomeController {

    private final StepOutcomeService stepOutcomeService;

    @Autowired
    public StepOutcomeController(StepOutcomeService stepOutcomeService) {
        this.stepOutcomeService = stepOutcomeService;
    }

}
