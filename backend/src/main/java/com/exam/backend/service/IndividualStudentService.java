package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudentSlotData;
import com.exam.backend.pojo.IndividualStudentDto;

import java.util.List;

public interface IndividualStudentService {

    String saveStudent(IndividualStudentDto studentDto);

    IndividualStudentDto getIndividualStudentDetail(String rollNumber);

    int updateIndividualStudentData(IndividualStudentDto individualStudentDto);

    List<IndividualStudentSlotData> getSlotsDataForIndvStudents(String rollNumber, String mode);

    void updateExamSlotAndDemoSlotDateTimeForIndvStudent(String rollNumber, String examTheme, String examSlotDateTime, String demoSlotDateTime);

    int updatePaymentFlagForStudent(String rollNo);

}
