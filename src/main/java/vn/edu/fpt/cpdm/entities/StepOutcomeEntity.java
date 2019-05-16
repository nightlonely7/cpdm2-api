package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "StepOutcome")
@Table(name = "step_outcome")
@Data
public class StepOutcomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "step_id", referencedColumnName = "id")
    private ProcessStepEntity step;

    @Basic
    @Column(name = "summary")
    private String summary;

    @Basic
    @Column(name = "action")
    private String action;

    @ManyToOne
    @JoinColumn(name = "next_step_id", referencedColumnName = "id")
    private ProcessStepEntity nextStep;

    @Basic
    @Column(name = "last_step")
    private Boolean lastStep;
}
