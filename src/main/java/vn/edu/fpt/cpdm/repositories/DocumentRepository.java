package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.fpt.cpdm.entities.DocumentEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.forms.documents.DocumentSearchForm;
import vn.edu.fpt.cpdm.models.documents.DocumentDetail;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {

    @Query("select document from DocumentEntity document where " +
            "(:#{#documentSearchForm.code} is null or document.code like %:#{#documentSearchForm.code}%) and " +
            "(:#{#documentSearchForm.title} is null or document.title like %:#{#documentSearchForm.title}%) and " +
            "(:#{#documentSearchForm.summary} is null or document.summary like %:#{#documentSearchForm.summary}%) and " +
            "(:#{#documentSearchForm.decree} is null or document.decree like %:#{#documentSearchForm.decree}%) and " +
            "(:#{#documentSearchForm.detail} is null or document.detail like %:#{#documentSearchForm.detail}%) and " +
            "(:#{#documentSearchForm.outsiderCode} is null or document.outsider.code like %:#{#documentSearchForm.outsiderCode}%) and " +
            "(:#{#documentSearchForm.outsiderName} is null or document.outsider.name like %:#{#documentSearchForm.outsiderName}%) and " +
            "(:#{#documentSearchForm.outsiderContactData} is null or document.outsider.contactData like %:#{#documentSearchForm.outsiderContactData}%) and " +
            "(:#{#documentSearchForm.createdTimeFrom} is null or document.createdTime >= :#{#documentSearchForm.createdTimeFrom}) and " +
            "(:#{#documentSearchForm.createdTimeTo} is null or document.createdTime <= :#{#documentSearchForm.createdTimeTo}) and " +
            "(:#{#documentSearchForm.lastModifiedTimeFrom} is null or document.lastModifiedTime >= :#{#documentSearchForm.lastModifiedTimeFrom}) and " +
            "(:#{#documentSearchForm.lastModifiedTimeTo} is null or document.lastModifiedTime <= :#{#documentSearchForm.lastModifiedTimeTo}) and " +
            "(:#{#documentSearchForm.arrivalDateFrom} is null or document.arrivalDate >= :#{#documentSearchForm.arrivalDateFrom}) and " +
            "(:#{#documentSearchForm.arrivalDateTo} is null or document.arrivalDate <= :#{#documentSearchForm.arrivalDateTo}) and " +
            "(:#{#documentSearchForm.effectiveDateFrom} is null or document.effectiveDate >= :#{#documentSearchForm.effectiveDateFrom}) and " +
            "(:#{#documentSearchForm.effectiveDateTo} is null or document.effectiveDate <= :#{#documentSearchForm.effectiveDateTo}) and " +
            "(:#{#documentSearchForm.effectiveEndDateFrom} is null or document.effectiveEndDate >= :#{#documentSearchForm.effectiveEndDateFrom}) and " +
            "(:#{#documentSearchForm.effectiveEndDateTo} is null or document.effectiveEndDate <= :#{#documentSearchForm.effectiveEndDateTo}) and " +
            "(:#{#documentSearchForm.processed} is null or document.processed = :#{#documentSearchForm.processed}) and " +
            "(:#{#documentSearchForm.startedProcessing} is null or document.startedProcessing = :#{#documentSearchForm.startedProcessing}) and " +
            "(document.internal = false) and " + "(document.currentStep.executor = :executor) and " + "(document.available = true)")
    Page<DocumentSummary> findAllByCurrentStep_Executor(@Param("documentSearchForm") DocumentSearchForm documentSearchForm,
                                                        @Param("executor") UserEntity executor, Pageable pageable);


    @Query("select document from DocumentEntity document where " +
            "(:#{#documentSearchForm.code} is null or document.code like %:#{#documentSearchForm.code}%) and " +
            "(:#{#documentSearchForm.title} is null or document.title like %:#{#documentSearchForm.title}%) and " +
            "(:#{#documentSearchForm.summary} is null or document.summary like %:#{#documentSearchForm.summary}%) and " +
            "(:#{#documentSearchForm.decree} is null or document.decree like %:#{#documentSearchForm.decree}%) and " +
            "(:#{#documentSearchForm.detail} is null or document.detail like %:#{#documentSearchForm.detail}%) and " +
            "(:#{#documentSearchForm.outsiderCode} is null or document.outsider.code like %:#{#documentSearchForm.outsiderCode}%) and " +
            "(:#{#documentSearchForm.outsiderName} is null or document.outsider.name like %:#{#documentSearchForm.outsiderName}%) and " +
            "(:#{#documentSearchForm.outsiderContactData} is null or document.outsider.contactData like %:#{#documentSearchForm.outsiderContactData}%) and " +
            "(:#{#documentSearchForm.createdTimeFrom} is null or document.createdTime >= :#{#documentSearchForm.createdTimeFrom}) and " +
            "(:#{#documentSearchForm.createdTimeTo} is null or document.createdTime <= :#{#documentSearchForm.createdTimeTo}) and " +
            "(:#{#documentSearchForm.lastModifiedTimeFrom} is null or document.lastModifiedTime >= :#{#documentSearchForm.lastModifiedTimeFrom}) and " +
            "(:#{#documentSearchForm.lastModifiedTimeTo} is null or document.lastModifiedTime <= :#{#documentSearchForm.lastModifiedTimeTo}) and " +
            "(:#{#documentSearchForm.arrivalDateFrom} is null or document.arrivalDate >= :#{#documentSearchForm.arrivalDateFrom}) and " +
            "(:#{#documentSearchForm.arrivalDateTo} is null or document.arrivalDate <= :#{#documentSearchForm.arrivalDateTo}) and " +
            "(:#{#documentSearchForm.effectiveDateFrom} is null or document.effectiveDate >= :#{#documentSearchForm.effectiveDateFrom}) and " +
            "(:#{#documentSearchForm.effectiveDateTo} is null or document.effectiveDate <= :#{#documentSearchForm.effectiveDateTo}) and " +
            "(:#{#documentSearchForm.effectiveEndDateFrom} is null or document.effectiveEndDate >= :#{#documentSearchForm.effectiveEndDateFrom}) and " +
            "(:#{#documentSearchForm.effectiveEndDateTo} is null or document.effectiveEndDate <= :#{#documentSearchForm.effectiveEndDateTo}) and " +
            "(:#{#documentSearchForm.processed} is null or document.processed = :#{#documentSearchForm.processed}) and " +
            "(:#{#documentSearchForm.startedProcessing} is null or document.startedProcessing = :#{#documentSearchForm.startedProcessing}) and " +
            "(document.internal = :internal) and " + "(document.available = true)")
    Page<DocumentSummary> findAllSummary(@Param("documentSearchForm") DocumentSearchForm documentSearchForm,
                                         @Param("internal") boolean internal,
                                         Pageable pageable);

    Optional<DocumentDetail> findDetailById(Integer id);

    boolean existsByCode(String code);
}
