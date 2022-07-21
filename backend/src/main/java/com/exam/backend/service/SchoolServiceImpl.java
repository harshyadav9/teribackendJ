package com.exam.backend.service;

import com.exam.backend.entity.School;
import com.exam.backend.pojo.ChangePasswordDto;
import com.exam.backend.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SchoolServiceImpl implements SchoolService{

    Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public String updatePassword(ChangePasswordDto rollNumberDto) {
        log.info("Inside updatePassword() {}", rollNumberDto);

        School school = new School();
        school.setSchoolsCode(rollNumberDto.getSchoolId());
        school.setPassword(rollNumberDto.getNewPassword());
        int i = schoolRepository.updatePassword(rollNumberDto.getNewPassword(), rollNumberDto.getSchoolId());
       if (i != 0){
           log.info("Completed updatePassword() {}", rollNumberDto);
           return "Password is updated successfully.";

       }else{
           log.info("Invalid schoolId");
           return "Invalid schoolId.Password Update failed.";
       }

    }

}
