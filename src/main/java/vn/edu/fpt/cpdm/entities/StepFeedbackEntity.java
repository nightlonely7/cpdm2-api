package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "StepFeedbackEntity")
@Table(name = "step_feedback")
@Data
public class StepFeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "step_outcome_id", referencedColumnName = "id", nullable = false)
    private StepOutcomeEntity outcome;

    @Basic
    @Column(name = "feedback")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private DocumentEntity document;

    @Basic
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Basic
    @Column(name = "completed_time", nullable = false)
    private LocalDateTime completedTime;
}
