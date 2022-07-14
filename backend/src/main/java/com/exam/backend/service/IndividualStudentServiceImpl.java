package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudent;
import com.exam.backend.entity.IndividualStudentSlotData;
import com.exam.backend.pojo.IndividualStudentDto;
import com.exam.backend.repository.IndividualStudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

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
    public String saveStudent(IndividualStudentDto studentDto) {
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
            individualStudent.setRollNo(rollNum);
        } else {
            Integer last4digits1 = 0000;
            String rollNum = generateIndividualStudentRollNumber(studentDto.getRollNoPrefix(), decimalFormat.format(++last4digits1));
            individualStudent.setRollNo(rollNum);
        }
        IndividualStudent individualStudent2 = individualStudentRepository.save(individualStudent);
        log.info("Completed saveStudent() {}", individualStudent2);
        return individualStudent2.getRollNo();
    }

    @Override
    public IndividualStudentDto getIndividualStudentDetail(String rollNumber) {
        log.info("getIndividualStudentDetail() {}", rollNumber);
        IndividualStudent individualStudent = individualStudentRepository.findByRollNo(rollNumber);
        IndividualStudentDto individualStudentDto = new IndividualStudentDto();
        individualStudentDto.setAdd1(individualStudent.getAdd1());
        individualStudentDto.setCity(individualStudent.getCity());
        individualStudentDto.setCountry(individualStudent.getCountry());
        individualStudentDto.setAdd2(individualStudent.getAdd2());
        individualStudentDto.setDob(individualStudent.getDob());
        individualStudentDto.setEmail(individualStudent.getEmail());
        individualStudentDto.setDemoExam(individualStudent.getDemoExam());
        individualStudentDto.setExamLevel(individualStudent.getExamLevel());
        individualStudentDto.setName(individualStudent.getName());
        individualStudentDto.setCountryCode(individualStudent.getCountry());
        individualStudentDto.setSection(individualStudent.getSection());
        individualStudentDto.setSchool(individualStudent.getSchool());
        individualStudentDto.setStandard(individualStudent.getStandard());
        individualStudentDto.setPin(individualStudent.getPin());
        individualStudentDto.setPgMobile(individualStudent.getPgMobile());
        individualStudentDto.setPgEmail(individualStudent.getPgEmail());
        individualStudentDto.setModifiedby(individualStudent.getModifiedby());
        individualStudentDto.setMobile(individualStudent.getMobile());
        individualStudentDto.setGender(individualStudent.getGender());
        individualStudentDto.setExamTheme(individualStudent.getExamTheme());
        individualStudentDto.setCreatedBy(individualStudent.getCreatedby());


        log.info("Completed getIndividualStudentDetail() {}", individualStudentDto);
        return individualStudentDto;
    }

    @Override
    public int updateIndividualStudentData(IndividualStudentDto individualStudentDto) {
        log.info("updateIndividualStudentData() {}", individualStudentDto);
        int count = individualStudentRepository.updateIndividualStudentData(individualStudentDto.getRollNo(), individualStudentDto.getAdd1(), individualStudentDto.getCity(),
                individualStudentDto.getPin(), individualStudentDto.getSchool(), individualStudentDto.getStandard(), individualStudentDto.getSection(),
                individualStudentDto.getPgEmail(), individualStudentDto.getPgMobile(), individualStudentDto.getExamTheme(),
                individualStudentDto.getDemoExam(), individualStudentDto.getExamLevel());
        log.info("Completed updateIndividualStudentData() {}", individualStudentDto);
        return count;

    }

    @Override
    public List<IndividualStudentSlotData> getSlotsDataForIndvStudents(String rollNumber, String mode) {
        return individualStudentRepository.getSlotDataForIndvStudents(rollNumber, mode);
    }

    public String generateIndividualStudentRollNumber(String rollNumberPrefix, String runningNumber) {
        log.info("generateIndividualStudentRollNumber() {} {} {}", rollNumberPrefix, runningNumber);
        return rollNumberPrefix + runningNumber;

    }

    @Override
    public void updateExamSlotAndDemoSlotDateTimeForIndvStudent(String rollNumber, String examTheme, String examSlotDateTime, String demoSlotDateTime) {
        log.info("Inside updateExamSlotAndDemoSlotDateTimeForIndvStudent() {} {} {} {}", rollNumber, examTheme, examSlotDateTime, demoSlotDateTime);
        if (!examTheme.equalsIgnoreCase("MOCK")){
            IndividualStudent individualStudent = individualStudentRepository.findByRollNo(rollNumber);
            individualStudent.setExamSlotDateTime(examSlotDateTime + "-" + demoSlotDateTime);

            individualStudentRepository.save(individualStudent);
            log.info("completed updateExamSlotAndDemoSlotDateTimeForIndvStudent.save(individualStudent) {}", individualStudent);
        }
        if (examTheme.equalsIgnoreCase("MOCK")){
            IndividualStudent individualStudent = individualStudentRepository.findByRollNoAndDemoExam(rollNumber, "YES");
            individualStudent.setDemoSlotDateTime(examSlotDateTime + "-" + demoSlotDateTime);
            individualStudentRepository.save(individualStudent);
            log.info("completed updateExamSlotAndDemoSlotDateTimeForIndvStudent.save(individualStudent) {}", individualStudent);
        }
    }

}
