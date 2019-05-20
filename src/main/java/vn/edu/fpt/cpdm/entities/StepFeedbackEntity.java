package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "StepFeedback")
@Table(name = "step_feedback")
@Data
public class StepFeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "step_outcome_id", referencedColumnName = "id")
    private StepOutcomeEntity outcome;

    @Basic
    @Column(name = "feedback")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private DocumentEntity document;

    @Basic
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Basic
    @Column(name = "completed_time")
    private LocalDateTime completedTime;
}