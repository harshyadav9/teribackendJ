package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudent;
import com.exam.backend.entity.School;
import com.exam.backend.pojo.ChangePasswordDto;
import com.exam.backend.repository.IndividualStudentRepository;
import com.exam.backend.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ChangePasswordService {

    Logger log = LoggerFactory.getLogger(ChangePasswordService.class);

    private final IndividualStudentRepository individualStudentRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public ChangePasswordService(IndividualStudentRepository individualStudentRepository, SchoolRepository schoolRepository) {
        this.individualStudentRepository = individualStudentRepository;
        this.schoolRepository = schoolRepository;

    }

    public String changePassword(ChangePasswordDto changePasswordDto) {
        log.info("Inside changePassword() {}", changePasswordDto);

        if (changePasswordDto.getIsIndividualStudent() != null && changePasswordDto.getIsIndividualStudent().equalsIgnoreCase("true")){
            Optional<IndividualStudent> individualStudent = individualStudentRepository.findById(changePasswordDto.getIndvRollNumber());
            if (individualStudent.isPresent()){
                individualStudent.get().setPassword(changePasswordDto.getNewPassword());
                individualStudentRepository.save(individualStudent.get());
                return "New Password is saved successfully.";
            }else {
                return "No data found for given input.";
            }
        } else {
            Optional<School> school = schoolRepository.findById(changePasswordDto.getSchoolId());
            if (school.isPresent()) {
                school.get().setPassword(changePasswordDto.getNewPassword());
                schoolRepository.save(school.get());
                return "New Password is saved successfully.";

            } else {
                return "No data found for given input.";
            }
        }
    }
}
