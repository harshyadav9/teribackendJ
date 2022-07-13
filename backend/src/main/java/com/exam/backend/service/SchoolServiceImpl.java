package com.exam.backend.service;

import com.exam.backend.pojo.InternationalStudantsDto;
import com.exam.backend.pojo.SchoolDto;
import com.exam.backend.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void updatePassword(SchoolDto rollNumberDto) {
        log.info("Inside updatePassword() {}", rollNumberDto);
        schoolRepository.updatePassword(rollNumberDto.getNewPassword(), rollNumberDto.getSchoolId());
        log.info("Completed updatePassword() {}", rollNumberDto);

    }
}
