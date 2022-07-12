package com.exam.backend.repository;

import com.exam.backend.entity.InternationalStudant;
import com.exam.backend.entity.SchoolSlotData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InternationalStudantsRepository extends CrudRepository<InternationalStudant, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetExamSlotByTheme where schoolId = :schoolId and mode = :mode")
    List<SchoolSlotData> getData(String schoolId, String mode);

    List<InternationalStudant> findByIdSchoolIdAndExamTheme(String schoolId, String examTheme);

    List<InternationalStudant> findByIdSchoolIdAndDemoExam(String schoolId, String examTheme);

    @Query(nativeQuery = true, value = "Update InternationalStudants set paymentStatus = 1 where schoolId = :schoolId")
    @Modifying
    void updatePaymentFlagForSchoolsPaid(String schoolId);

}
