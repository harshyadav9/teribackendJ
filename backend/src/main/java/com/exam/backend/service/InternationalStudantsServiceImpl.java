package com.exam.backend.service;

import com.exam.backend.entity.InternationalStudants;
import com.exam.backend.entity.InternationalStudantsId;
import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.entity.StudentClass;
import com.exam.backend.pojo.InternationalStudantsDto;
import com.exam.backend.repository.InternationalStudantsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

            InternationalStudants internationalStudant = new InternationalStudants();
            internationalStudant.setClassName(dto.getClassName());
            internationalStudant.setDemoExam(dto.getDemoExam());
            internationalStudant.setExamTheme(dto.getExamTheme());
            InternationalStudantsId id = new InternationalStudantsId();

            id.setDob(dto.getDob());
            id.setName(dto.getName());
            id.setSchoolId(dto.getSchoolId());
            internationalStudant.setId(id);
            internationalStudant.setExamLevel(studentClass.getLevel());
            internationalStudant.setSection(dto.getSection());
            internationalStudant.setPassword(dto.getDob());
            internationalStudant.setPaymentStatus(false);
            log.info("internationalStudants data() {}", internationalStudant);
            internationalStudantsRepository.save(internationalStudant);
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
    public void updateExamSlotAndDemoSlotDateTime(String schoolId, String examTheme, String examSlotDateTime, String demoSlotDateTime) {
        log.info("Inside updateExamSlotAndDemoSlotDateTime() {} {} {} {}", schoolId, examTheme, examSlotDateTime, demoSlotDateTime);
        if (!examTheme.equalsIgnoreCase("MOCK")){
            List<InternationalStudants> li = internationalStudantsRepository.findByIdSchoolIdAndExamTheme(schoolId, examTheme);
            for (InternationalStudants school : li) {
                if (school.getExamSlotDatetime() == null){
                    school.setExamSlotDatetime(examSlotDateTime + "-" + demoSlotDateTime);
                }

            }
            log.info("li inside updateExamSlotAndDemoSlotDateTime() {}", li);
            internationalStudantsRepository.saveAll(li);
            log.info("completed internationalStudantsRepository.saveAll(li) {}", li);
        }
        if (examTheme.equalsIgnoreCase("MOCK")){
            List<InternationalStudants> liMock = internationalStudantsRepository.findByIdSchoolIdAndDemoExam(schoolId, "YES");
            for (InternationalStudants school : liMock) {

                if (school.getDemoSlotDatetime() == null && school.getDemoExam().equalsIgnoreCase("YES")){
                    school.setDemoSlotDatetime(examSlotDateTime + "-" + demoSlotDateTime);
                }
            }
            internationalStudantsRepository.saveAll(liMock);
            log.info("completed internationalStudantsRepository.saveAll(liMock) {}", liMock);
        }
    }
}