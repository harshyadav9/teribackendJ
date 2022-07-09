package com.exam.backend.repository;

import com.exam.backend.entity.InternationalStudants;
import com.exam.backend.entity.SchoolSlotData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InternationalStudantsRepository extends CrudRepository<InternationalStudants, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetExamSlotByTheme where schoolId = :schoolId and mode = :mode")
    List<SchoolSlotData> getData(String schoolId, String mode);

    List<InternationalStudants> findByIdSchoolIdAndExamTheme(String schoolId, String examTheme);

    List<InternationalStudants> findByIdSchoolIdAndDemoExam(String schoolId, String examTheme);
}
