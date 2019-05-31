package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "DocumentFile")
@Table(name = "document_file")
@Data
public class DocumentFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private DocumentEntity document;

    @ManyToOne
    @JoinColumn(name = "step_feedback_id", referencedColumnName = "id")
    private StepFeedbackEntity stepFeedback;

    @Basic
    @Column(name = "filename", nullable = false, unique = true)
    private String filename;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;
}
