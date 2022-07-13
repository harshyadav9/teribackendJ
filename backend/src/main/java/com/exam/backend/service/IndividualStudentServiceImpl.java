package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudent;
import com.exam.backend.pojo.IndividualStudentDto;
import com.exam.backend.repository.IndividualStudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;

@Service
@Transactional
public class IndividualStudentServiceImpl implements IndividualStudentService {

    Logger log = LoggerFactory.getLogger(ClassServiceImpl.class);

    private final IndividualStudentRepository individualStudentRepository;

    @Autowired
    public IndividualStudentServiceImpl(IndividualStudentRepository individualStudentRepository) {
        this.individualStudentRepository = individualStudentRepository;
    }

    @Override
    public void saveStudent(IndividualStudentDto studentDto) {
        log.info("Inside saveStudent() {}", studentDto);
        IndividualStudent individualStudent = new IndividualStudent();
        individualStudent.setDob(studentDto.getDob());
        individualStudent.setDemoExam(studentDto.getDemoExam());
        individualStudent.setExamLevel(studentDto.getExamLevel());
        individualStudent.setExamTheme(studentDto.getExamTheme());
        individualStudent.setName(studentDto.getName());
        individualStudent.setPassword(studentDto.getPassword());
        individualStudent.setSection(studentDto.getSection());
        individualStudent.setAdd1(studentDto.getAdd1());
        individualStudent.setAdd2(studentDto.getAdd2());
        individualStudent.setCity(studentDto.getCity());
        individualStudent.setCountry(studentDto.getCountry());
        individualStudent.setCreatedby(studentDto.getCreatedBy());
        individualStudent.setEmail(studentDto.getEmail());
        individualStudent.setGender(studentDto.getGender());
        individualStudent.setState(studentDto.getState());
        individualStudent.setStandard(studentDto.getStandard());
        individualStudent.setMobile(studentDto.getMobile());
        individualStudent.setModifiedby(studentDto.getModifiedby());
        individualStudent.setSchool(studentDto.getSchool());
        individualStudent.setPin(studentDto.getPin());
        individualStudent.setPgEmail(studentDto.getPgEmail());
        individualStudent.setPgMobile(studentDto.getPgMobile());

        //generate RollNumber
        Integer last4digits = individualStudentRepository.findLastRunningRollNumber();
        final DecimalFormat decimalFormat = new DecimalFormat("0000");
        if (last4digits != null) {
            String rollNum = generateIndividualStudentRollNumber(studentDto.getRollNoPrefix(), decimalFormat.format(++last4digits));
            individualStudent.setRollNumber(rollNum);
        } else {
            Integer last4digits1 = 0000;
            String rollNum = generateIndividualStudentRollNumber(studentDto.getRollNoPrefix(), decimalFormat.format(++last4digits1));
            individualStudent.setRollNumber(rollNum);
        }
        individualStudentRepository.save(individualStudent);
        log.info("Completed saveStudent() {}", studentDto);

    }

    @Override
    public IndividualStudent getIndividualStudentDetail(String rollNumber) {
        log.info("getIndividualStudentDetail() {}", rollNumber);
        IndividualStudent individualStudent = individualStudentRepository.findByRollNumber(rollNumber);
        log.info("Completed getIndividualStudentDetail() {}", individualStudent);
        return individualStudent;
    }

    @Override
    public void updateIndividualStudentData(IndividualStudent individualStudent) {
        log.info("updateIndividualStudentData() {}", individualStudent);
        individualStudentRepository.save(individualStudent);
        log.info("Completed updateIndividualStudentData() {}", individualStudent);
    }

    public String generateIndividualStudentRollNumber(String rollNumberPrefix, String runningNumber) {
        log.info("generateIndividualStudentRollNumber() {} {} {}", rollNumberPrefix, runningNumber);
        return rollNumberPrefix+runningNumber;

    }

}
