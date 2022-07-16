package com.exam.backend.service;

import com.exam.backend.entity.*;
import com.exam.backend.pojo.InternationalStudantsDto;
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

    @Autowired
    public InternationalStudantsServiceImpl(ClassServiceImpl classService, InternationalStudantsRepository internationalStudantsRepository) {
        this.classService = classService;
        this.internationalStudantsRepository = internationalStudantsRepository;
    }

    @Override
    public void saveStudentsData(List<InternationalStudantsDto> data) {

        log.info("Inside saveStudentsData() {}", data);

        for (InternationalStudantsDto dto : data) {
            log.info("dto{}", dto);
            StudentClass studentClass = classService.getClassLevel(dto.getClassName());
            log.info("studentClass data() {} {}", studentClass, data);

            InternationalStudant internationalStudant = new InternationalStudant();
            internationalStudant.setClassName(dto.getClassName());
            internationalStudant.setDemoExam(dto.getDemoExam() != null && !dto.getDemoExam().isEmpty() && dto.getDemoExam().equalsIgnoreCase("YES") ? "YES" : "NO");
            internationalStudant.setExamTheme(dto.getExamTheme());
            InternationalStudantsId id = new InternationalStudantsId();

            id.setDob(dto.getDob());
            id.setName(dto.getName());
            id.setSchoolId(dto.getSchoolId());
            internationalStudant.setId(id);
            if (studentClass != null){
                internationalStudant.setExamLevel(studentClass.getLevel());
            }
            internationalStudant.setSection(dto.getSection());

            InternationalStudantsId internationalStudantsId = new InternationalStudantsId();
            internationalStudantsId.setDob(dto.getDob());
            internationalStudantsId.setName(dto.getName());
            internationalStudantsId.setSchoolId(dto.getSchoolId());

            Optional<InternationalStudant> internationalStudant1 = internationalStudantsRepository.findById(internationalStudantsId);
            if (internationalStudant1.isPresent()) {
                if (internationalStudant1.get().getPaymentStatus() || internationalStudant1.get().getExamSlotDatetime() != null ||
                        internationalStudant1.get().getDemoSlotDatetime() != null) {
                    internationalStudant.setExamTheme(internationalStudant1.get().getExamTheme());
                    internationalStudant.setPaymentStatus(internationalStudant1.get().getPaymentStatus());
                    internationalStudant.setDemoExam(internationalStudant1.get().getDemoExam());
                    internationalStudant.setExamSlotDatetime(internationalStudant1.get().getExamSlotDatetime() != null ? internationalStudant1.get().getExamSlotDatetime() : null);
                    internationalStudant.setDemoSlotDatetime(internationalStudant1.get().getDemoSlotDatetime() != null ? internationalStudant1.get().getDemoSlotDatetime() : null);
                } else {
                    internationalStudant.setPaymentStatus(false);
                }
            } else {
                internationalStudant.setPaymentStatus(false);
            }
            internationalStudant.setPassword(dto.getDob());

            UUID uuid = UUID.randomUUID();
            internationalStudant.setStudentId(String.valueOf(uuid));
            internationalStudant.setModby(dto.getSchoolId());
            internationalStudant.setCreatedby(dto.getSchoolId());
            internationalStudantsRepository.save(internationalStudant);
            log.info("internationalStudants data() is saved {}", internationalStudant);
        }
        log.info("Completed saveStudentsData() {}", data);
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

            List<InternationalStudant> li = internationalStudantsRepository.findByIdSchoolIdAndExamTheme(schoolId, examTheme);
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
            List<InternationalStudant> liMock = internationalStudantsRepository.findByIdSchoolIdAndDemoExam(schoolId, "YES");
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
        List<InternationalStudant> studentsToBeUpdatedForASchool = internationalStudantsRepository.findAllByIdSchoolIdAndPaymentStatusAndRollNoNull(schoolId, true);

        if (studentsToBeUpdatedForASchool.size() == 0) {
            log.info("Nothing to update for schoolId {}", schoolId);
            return "Nothing to update";

        }

        long checkIfSchoolHasRollNumber = internationalStudantsRepository.countByIdSchoolIdAndPaymentStatusAndRollNoNotNull(schoolId, true);
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