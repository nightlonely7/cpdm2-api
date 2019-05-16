package vn.edu.fpt.cpdm.forms.documents;

import lombok.Data;

@Data
public class DocumentCreateForm {

    private String title;
    private String detail;
    private Integer processId;
}
