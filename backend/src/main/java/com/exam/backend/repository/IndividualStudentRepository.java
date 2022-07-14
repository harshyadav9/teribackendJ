package com.exam.backend.repository;

import com.exam.backend.entity.IndividualStudent;
import com.exam.backend.entity.IndividualStudentSlotData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividualStudentRepository extends CrudRepository<IndividualStudent, String> {

    @Query(nativeQuery = true, value = "SELECT max(RIGHT(RollNo, 4)) from IndividualStudent")
    Integer findLastRunningRollNumber();

    IndividualStudent findByRollNo(String rollNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetIndividualStudentSlotByTheme where RollNo = :rollNumber and mode = :mode")
    List<IndividualStudentSlotData> getSlotDataForIndvStudents(String rollNumber, String mode);

    @Query(nativeQuery = true, value = "Update IndividualStudent set Add1=:add1, City =:city, Pin = :pin, School=:school,class=:standard, " +
            " Section=:section, PGEmail=:pgEmail, PGMobile=:pgMobile, ExamTheme=:examTheme, DemoExam=:demoExam, ExamLevel= :examLevel where RollNo = :rollNo")
    @Modifying
    int updateIndividualStudentData(String rollNo, String add1, String city, String pin, String school,
                                                                String standard, String section, String pgEmail, String pgMobile,
                                                                String examTheme, String demoExam, String examLevel);

    IndividualStudent findByRollNoAndDemoExam(String rollNo, String examTheme);

}
