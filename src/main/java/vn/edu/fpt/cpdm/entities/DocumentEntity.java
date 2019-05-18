package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Document")
@Table(name = "document")
@Data
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "outsider_id", referencedColumnName = "id", nullable = false)
    private OutsiderEntity outsider;

    @Basic
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "summary")
    private String summary;

    @Basic
    @Column(name = "decree")
    private String decree;

    @Basic
    @Lob
    @Column(name = "detail")
    private String detail;

    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "id")
    private DocumentProcessEntity process;

    @ManyToOne
    @JoinColumn(name = "current_step_id", referencedColumnName = "id")
    private ProcessStepEntity currentStep;

    @Basic
    @Column(name = "started_processing_time")
    private LocalDateTime startedProcessingTime;

    @Basic
    @Column(name = "last_processed_time")
    private LocalDateTime lastProcessedTime;

    @Basic
    @Column(name = "started_processing", nullable = false)
    private Boolean startedProcessing;

    @Basic
    @Column(name = "processed", nullable = false)
    private Boolean processed;

    @Basic
    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Basic
    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Basic
    @Column(name = "effective_end_date")
    private LocalDate effectiveEndDate;

    @Basic
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Basic
    @Column(name = "last_modified_time", nullable = false)
    private LocalDateTime lastModifiedTime;

    @PrePersist
    public void prePersist() {
        this.startedProcessing = Boolean.FALSE;
        this.processed = Boolean.FALSE;
        this.createdTime = LocalDateTime.now();
        this.lastModifiedTime = LocalDateTime.now();
    }
}
