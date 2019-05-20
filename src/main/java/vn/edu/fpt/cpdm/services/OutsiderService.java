package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;

import java.util.List;

public interface OutsiderService {
    List<OutsiderSummary> findAllSummaryByNameContainsOrCodeContains(String name, String code);
}
