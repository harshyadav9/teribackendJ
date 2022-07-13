package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudent;
import com.exam.backend.pojo.IndividualStudentDto;

public interface IndividualStudentService {

    void saveStudent(IndividualStudentDto studentDto);

    IndividualStudent getIndividualStudentDetail(String rollNumber);

    void updateIndividualStudentData(IndividualStudent individualStudent);

}
