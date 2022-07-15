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

    @Query(nativeQuery = true, value = "SELECT max(RIGHT(RollNo, 4)) from IndividualStudent where country = :country and state = :state")
    Integer findLastRunningRollNumber(String country, String state);

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetIndividualStudentSlotByTheme where RollNo = :rollNumber and mode = :mode")
    List<IndividualStudentSlotData> getSlotDataForIndvStudents(String rollNumber, String mode);

    @Query(nativeQuery = true, value = "Update IndividualStudent set City =:city, Gender = :gender, Add1 = :add1, pin = :pin, School=:school, section=:section, " +
            " class=:standard, PGEmail=:pgEmail, PGMobile=:pgMobile, PGName = :pgName, ExamLevel = :examLevel, DemoExam =:demoExam, examTheme = :examTheme where RollNo = :rollNo")
    @Modifying
    int updateIndividualStudentData(String rollNo, String city, String gender, String add1, String pin,
                                                                String school, String section, String standard, String pgEmail,
                                                                String pgMobile, String pgName, String examLevel, String demoExam, String examTheme);

    IndividualStudent findByRollNoAndDemoExam(String rollNo, String examTheme);

}
