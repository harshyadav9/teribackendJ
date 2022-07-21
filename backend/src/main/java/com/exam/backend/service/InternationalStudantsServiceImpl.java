package com.exam.backend.service;

import com.exam.backend.entity.*;
import com.exam.backend.pojo.InternationalStudantsDto;
import com.exam.backend.repository.InternationalStudantsHistoryRepository;
import com.exam.backend.repository.InternationalStudantsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class InternationalStudantsServiceImpl implements InternationalStudantsService {

    Logger log = LoggerFactory.getLogger(InternationalStudantsServiceImpl.class);

    private final ClassServiceImpl classService;
    private final InternationalStudantsRepository internationalStudantsRepository;
    private final InternationalStudantsHistoryRepository internationalStudantsHistoryRepository;

    @Autowired
    public InternationalStudantsServiceImpl(ClassServiceImpl classService, InternationalStudantsRepository internationalStudantsRepository, InternationalStudantsHistoryRepository internationalStudantsHistoryRepository) {
        this.classService = classService;
        this.internationalStudantsRepository = internationalStudantsRepository;
        this.internationalStudantsHistoryRepository = internationalStudantsHistoryRepository;
    }

    @Override
    public void saveStudentsData(List<InternationalStudantsDto> data) {

        log.info("Inside saveStudentsData() {}", data);

        for (InternationalStudantsDto dto : data) {
            log.info("dto{}", dto);

            if (dto.getStudentId()!= null){

                //update the details for this studentid in table
                Optional<InternationalStudant> internationalStudant = internationalStudantsRepository.findById(dto.getStudentId());
                if (internationalStudant.isPresent()){
                    internationalStudant = Optional.ofNullable(setData(dto, internationalStudant.get()));
                    internationalStudantsRepository.save(internationalStudant.get());
                }

            } else {
                //create new record
                InternationalStudant internationalStudant = new InternationalStudant();
                internationalStudant = setData(dto, internationalStudant);
                internationalStudantsRepository.save(internationalStudant);
            }
            insertDataIntoHistoryTableForTracking(dto);
            log.info("Data is saved to Studants history table.");

        }
        log.info("Completed saveStudentsData() {}", data);
    }

    private InternationalStudant setData(InternationalStudantsDto internationalStudantDto, InternationalStudant internationalStudant){
        StudentClass studentClass = classService.getClassLevel(internationalStudantDto.getClassName());

        internationalStudant.setClassName(internationalStudantDto.getClassName());
        internationalStudant.setDemoExam(internationalStudantDto.getDemoExam() != null && !internationalStudantDto.getDemoExam().isEmpty() && internationalStudantDto.getDemoExam().equalsIgnoreCase("YES") ? "YES" : "NO");
        internationalStudant.setExamTheme(internationalStudant.isPaymentStatus() || internationalStudant.getExamSlotDatetime() != null || internationalStudant.getDemoSlotDatetime() != null ?
                internationalStudant.getExamTheme() : internationalStudantDto.getExamTheme());
        //internationalStudant.setPaymentStatus(internationalStudant.isPaymentStatus() ? internationalStudant.isPaymentStatus() : internationalStudantDto.isPaymentStatus());
        if (studentClass != null) {
            internationalStudant.setExamLevel(studentClass.getLevel());
        }
        internationalStudant.setSection(internationalStudantDto.getSection());
        internationalStudant.setPassword(internationalStudantDto.getDob());
        internationalStudant.setModby(internationalStudantDto.getSchoolId());
        internationalStudant.setCreatedby(internationalStudantDto.getSchoolId());
        internationalStudant.setDob(internationalStudantDto.getDob());
        internationalStudant.setName(internationalStudantDto.getName());
        internationalStudant.setSchoolId(internationalStudantDto.getSchoolId());

        return internationalStudant;

    }

    private void insertDataIntoHistoryTableForTracking(InternationalStudantsDto data) {
        log.info("Entered insertDataIntoHistoryTableForTracking() {}", data);
        InternationalStudantsHistory internationalStudantsHistory = new InternationalStudantsHistory();
        internationalStudantsHistory.setDob(data.getDob());
        internationalStudantsHistory.setDemoExam(data.getDemoExam());
        internationalStudantsHistory.setName(data.getName());
        internationalStudantsHistory.setPassword(data.getDob());
        internationalStudantsHistory.setSection(data.getSection());
        internationalStudantsHistory.setExamTheme(data.getExamTheme());
        internationalStudantsHistory.setSchoolID(data.getSchoolId());
        internationalStudantsHistory.setClassName(data.getClassName());

        internationalStudantsHistoryRepository.save(internationalStudantsHistory);
        log.info("Exiting insertDataIntoHistoryTableForTracking() {}", data);

    }

    @Override
    public List<SchoolSlotData> getSlotsData(String schoolId, String mode) {
        log.info("Inside getSlotsData() {} {}", schoolId, mode);
        List<SchoolSlotData> li = internationalStudantsRepository.getData(schoolId, mode);
        log.info("Completed getSlotsData() {}", li);
        return li;
    }

    @Override
    public int updateExamSlotAndDemoSlotDateTime(String schoolId, String examTheme, String examSlotDateTime, String demoSlotDateTime) {
        log.info("Inside updateExamSlotAndDemoSlotDateTime() {} {} {} {}", schoolId, examTheme, examSlotDateTime, demoSlotDateTime);
        int counter = 0;
        if (!examTheme.contains("MOCK")) {

            List<InternationalStudant> li = internationalStudantsRepository.findBySchoolIdAndExamTheme(schoolId, examTheme);
            for (InternationalStudant school : li) {
                if (school.getExamSlotDatetime() == null) {
                    school.setExamSlotDatetime(examSlotDateTime + "-" + demoSlotDateTime);
                    counter++;
                }

            }
            if (counter > 0) {
                internationalStudantsRepository.saveAll(li);
                log.info("completed internationalStudantsRepository.saveAll(li) {}", li);
            }
        }
        if (examTheme.contains("MOCK")) {
            List<InternationalStudant> liMock = internationalStudantsRepository.findBySchoolIdAndDemoExam(schoolId, "YES");
            for (InternationalStudant school : liMock) {

                if (school.getDemoSlotDatetime() == null && school.getDemoExam().equalsIgnoreCase("YES")) {
                    school.setDemoSlotDatetime(examSlotDateTime + "-" + demoSlotDateTime);
                    counter++;

                }
            }
            if (counter > 0) {
                internationalStudantsRepository.saveAll(liMock);
                log.info("completed internationalStudantsRepository.saveAll(liMock) {}", liMock);
            }

        }
        return counter;
    }

    @Override
    public String generateAndUpdateRollNumberForSchoolStudent(String schoolId) {
        log.info("Inside generateAndUpdateRollNumberForSchoolStudent() {}", schoolId);

        List<InternationalStudant> studentsToBeUpdatedForASchool = internationalStudantsRepository.findAllBySchoolIdAndPaymentStatusAndRollNoNull(schoolId, true);

        if (studentsToBeUpdatedForASchool.size() == 0) {
            log.info("Nothing to update for schoolId {}", schoolId);
            return "Nothing to update";

        }

        long checkIfSchoolHasRollNumber = internationalStudantsRepository.countBySchoolIdAndPaymentStatusAndRollNoNotNull(schoolId, true);
        log.info("checkIfSchoolHasRollNumber for schoolid {} {}", checkIfSchoolHasRollNumber, schoolId);
        RollNumberData rollNumberData = internationalStudantsRepository.getSchoolDataForGivenSchool(schoolId);

        if (checkIfSchoolHasRollNumber > 0) {
            String rollNum = internationalStudantsRepository.findRollNumberForAlreadyPaidSchool(schoolId, true);
            final DecimalFormat decimalFormat = new DecimalFormat("0000");
            Integer last4Digits = generateRollNumber(rollNum);
            for (InternationalStudant internationalStudant : studentsToBeUpdatedForASchool) {
                internationalStudant.setRollNo(createRollNumberPattern(decimalFormat.format(++last4Digits), rollNumberData));
            }

        } else {
            final DecimalFormat decimalFormat = new DecimalFormat("0000");
            Integer rollNumberVal = 0000;
            for (InternationalStudant internationalStudant : studentsToBeUpdatedForASchool) {
                internationalStudant.setRollNo(createRollNumberPattern(decimalFormat.format(++rollNumberVal), rollNumberData));
            }
        }
        log.info("schoolsToBeUpdated {}", studentsToBeUpdatedForASchool);
        internationalStudantsRepository.saveAll(studentsToBeUpdatedForASchool);
        return "Roll Numbers updated Successfully.";

    }

    private Integer generateRollNumber(String rollNum) {
        log.info("generateRollNumber() for rollNumber {}", rollNum);
        Integer last4digits = Integer.valueOf(rollNum.substring(rollNum.length() - 5));
        return last4digits;

    }

    private String createRollNumberPattern(String last4digits, RollNumberData rollNumberData) {

        String finalRollNumber = rollNumberData.getCountryCode() + rollNumberData.getYear() + rollNumberData.getStateCode() + rollNumberData.getSchoolNumber() + last4digits;
        log.info("createRollNumberPattern() finalRollNumber{}", finalRollNumber);
        return finalRollNumber;
    }
}