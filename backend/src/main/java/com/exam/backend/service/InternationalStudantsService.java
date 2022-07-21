package com.exam.backend.service;

import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.pojo.InternationalStudantsDto;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface InternationalStudantsService {

    String saveStudentsData(List<InternationalStudantsDto> data) throws SQLIntegrityConstraintViolationException;

    List<SchoolSlotData> getSlotsData (String schoolId, String mode);

    int updateExamSlotAndDemoSlotDateTime(String schoolId, String examTheme, String examSlotDateTime, String demoSlotDateTime);

    String generateAndUpdateRollNumberForSchoolStudent(String schoolId);

}
