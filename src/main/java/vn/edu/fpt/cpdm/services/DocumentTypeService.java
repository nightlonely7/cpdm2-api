package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.models.NameOnlyModel;

import java.util.List;

public interface DocumentTypeService {

    List<NameOnlyModel> findAll();
}
