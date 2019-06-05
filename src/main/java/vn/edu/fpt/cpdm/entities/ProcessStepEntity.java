package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "ProcessStepEntity")
@Table(name = "process_step")
@Data
public class ProcessStepEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id", nullable = false)
    private UserEntity executor;

    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "id", nullable = false)
    private DocumentProcessEntity process;

    @OneToMany(mappedBy = "step")
    private List<StepOutcomeEntity> outcomes;

}
