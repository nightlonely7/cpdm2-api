package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "StepOutcomeEntity")
@Table(name = "step_outcome")
@Data
public class StepOutcomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "step_id", referencedColumnName = "id", nullable = false)
    private ProcessStepEntity step;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "next_step_id", referencedColumnName = "id")
    private ProcessStepEntity nextStep;

    @Basic
    @Column(name = "last_step", nullable = false)
    private Boolean lastStep;

    @Basic
    @Column(name = "main", nullable = false)
    private Boolean main;
}
