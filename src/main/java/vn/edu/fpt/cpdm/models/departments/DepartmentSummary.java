package vn.edu.fpt.cpdm.models.departments;

public interface DepartmentSummary {
    Integer getId();
    String getCode();
    String getName();
    String getDescription();
    Boolean getAvailable();
}
