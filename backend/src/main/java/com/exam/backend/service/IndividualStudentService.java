package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudent;
import com.exam.backend.entity.IndividualStudentSlotData;
import com.exam.backend.pojo.IndividualStudentDto;

import java.util.List;

public interface IndividualStudentService {

    void saveStudent(IndividualStudentDto studentDto);

    IndividualStudent getIndividualStudentDetail(String rollNumber);

    void updateIndividualStudentData(IndividualStudent individualStudent);

    List<IndividualStudentSlotData> getSlotsDataForIndvStudents(String rollNumber, String mode);

    void updateExamSlotAndDemoSlotDateTimeForIndvStudent(String rollNumber, String examTheme, String examSlotDateTime, String demoSlotDateTime);

}
