package com.exam.backend.repository;

import com.exam.backend.entity.IndividualStudentSlotData;
import com.exam.backend.entity.IndividualStudent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividualStudentRepository extends CrudRepository<IndividualStudent, String> {

    @Query(nativeQuery = true, value = "SELECT max(RIGHT(RollNo, 4)) from IndividualStudent;")
    Integer findLastRunningRollNumber();

    IndividualStudent findByRollNo(String rollNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetIndividualStudentSlotByTheme where RollNo = :rollNumber and mode = :mode")
    List<IndividualStudentSlotData> getSlotDataForIndvStudents(String rollNumber, String mode);

    IndividualStudent findByRollNoAndDemoExam(String rollNo, String examTheme);

}
