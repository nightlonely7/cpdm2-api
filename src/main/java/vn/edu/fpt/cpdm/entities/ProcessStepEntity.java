package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "ProcessStep")
@Table(name = "process_step")
@Data
public class ProcessStepEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private UserEntity executor;

    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "id")
    private DocumentProcessEntity process;

}
