package com.exam.backend.service;

import com.exam.backend.entity.InternationalStudant;
import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.pojo.InternationalStudantsDto;

import java.util.List;

public interface InternationalStudantsService {

    void saveStudentsData(List<InternationalStudantsDto> data);

    List<SchoolSlotData> getSlotsData (String schoolId, String mode);

    void updateExamSlotAndDemoSlotDateTime(String schoolId, String examTheme, String examSlotDateTime, String demoSlotDateTime);

    String generateAndUpdateRollNumberForSchoolStudent(String schoolId);

}
