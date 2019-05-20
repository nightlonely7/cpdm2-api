package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;
import vn.edu.fpt.cpdm.repositories.OutsiderRepository;
import vn.edu.fpt.cpdm.services.OutsiderService;

import java.util.List;

@Service
public class OutsiderServiceImpl implements OutsiderService {

    private final OutsiderRepository outsiderRepository;

    @Autowired
    public OutsiderServiceImpl(OutsiderRepository outsiderRepository) {
        this.outsiderRepository = outsiderRepository;
    }

    @Override
    public List<OutsiderSummary> findAllSummaryByNameContainsOrCodeContains(String name, String code) {
        return outsiderRepository.findAllSummaryByNameContainsOrCodeContains(name, code);
    }
}
