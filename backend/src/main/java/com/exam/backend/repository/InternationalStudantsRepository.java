package com.exam.backend.repository;

import com.exam.backend.entity.InternationalStudant;
import com.exam.backend.entity.SchoolSlotData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InternationalStudantsRepository extends CrudRepository<InternationalStudant, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetExamSlotByTheme where schoolId = :schoolId and mode = :mode")
    List<SchoolSlotData> getData(String schoolId, String mode);

    List<InternationalStudant> findByIdSchoolIdAndExamTheme(String schoolId, String examTheme);

    List<InternationalStudant> findByIdSchoolIdAndDemoExam(String schoolId, String examTheme);
}
