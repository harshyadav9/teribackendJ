package com.exam.backend.service;

import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.entity.Slot;
import com.exam.backend.pojo.IndividualStudentSlotDataDto;
import com.exam.backend.pojo.SchoolSlotDataIncoming;
import com.exam.backend.pojo.SchoolSlotUpdateStatus;
import com.exam.backend.repository.SlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation= Propagation.REQUIRED)
public class SlotServiceImpl implements SlotService {

    Logger log = LoggerFactory.getLogger(SlotServiceImpl.class);
    private final SlotRepository slotRepository;
    private final InternationalStudantsServiceImpl internationalStudantsService;
    private final IndividualStudentServiceImpl individualStudentService;

    @Autowired
    public SlotServiceImpl(SlotRepository slotRepository, InternationalStudantsServiceImpl internationalStudantsService, IndividualStudentServiceImpl individualStudentService) {
        this.slotRepository = slotRepository;
        this.internationalStudantsService = internationalStudantsService;
        this.individualStudentService = individualStudentService;
    }

    @Override
    public List<SchoolSlotData> getSlotsData(String schoolId, String mode) {
        return internationalStudantsService.getSlotsData(schoolId, mode);

    }

    @Override
    public SchoolSlotUpdateStatus updateSlotData(List<SchoolSlotDataIncoming> data) {
        log.info("Inside updateSlotData(){}", data);

        SchoolSlotUpdateStatus schoolSlotUpdateStatus = new SchoolSlotUpdateStatus();
        Map<String,String> mp = new HashMap<>();

        for (SchoolSlotDataIncoming schoolSlotData : data){
            Slot slot = slotRepository.findBySlotId(schoolSlotData.getSlotId());
            if (slot.getSeatAvailable() > schoolSlotData.getStudentCount()){
                slot.setSeatAvailable(slot.getSeatAvailable() - schoolSlotData.getStudentCount());

                int counter = internationalStudantsService.updateExamSlotAndDemoSlotDateTime(schoolSlotData.getSchoolId(), schoolSlotData.getExamTheme(),
                        String.valueOf(schoolSlotData.getDateOfExam()), schoolSlotData.getSlotDatetime() );
                log.info("internationalStudantsService table is updated with slot timing for school and examtheme {} {}",
                        schoolSlotData.getSchoolId(), schoolSlotData.getExamTheme());
                if (counter > 0){
                    slotRepository.save(slot);
                    log.info("slot() saved in slot table {}", slot);
                }
            }
            else {
                mp.put("Error", "Slot selected for examTheme " + slot.getExamTheme() + " is not having required available seats.");
                schoolSlotUpdateStatus.setStatus(mp);
                schoolSlotUpdateStatus.setErrored(true);
                log.info("Error slots mismatch with avaialable seats");
            }
        }
        if (schoolSlotUpdateStatus.isErrored()){
            return schoolSlotUpdateStatus;
        } else {
            log.info("schoolSlotUpdateStatus is not errored");
            mp.put("Success", "Slot is booked successfully.");
            schoolSlotUpdateStatus.setStatus(mp);
            return schoolSlotUpdateStatus;
        }
    }

    @Override
    public SchoolSlotUpdateStatus updateSlotDataForIndvStudents(List<IndividualStudentSlotDataDto> data) {
        log.info("Inside updateSlotDataForIndvStudents(){}", data);
        SchoolSlotUpdateStatus schoolSlotUpdateStatus = new SchoolSlotUpdateStatus();
        Map<String,String> mp = new HashMap<>();

        List<Slot> slots = new ArrayList<>();

        for (IndividualStudentSlotDataDto individualStudentSlotDataDto : data){
            Slot slot = slotRepository.findBySlotId(individualStudentSlotDataDto.getSlotID());
            if (slot.getSeatAvailable() > 0){
                slot.setSeatAvailable(slot.getSeatAvailable() - 1);
                slots.add(slot);
            }
            else {
                mp.put("Error", "Slot selected for examTheme " + slot.getExamTheme() + " is not having required available seats.");
                schoolSlotUpdateStatus.setStatus(mp);
                schoolSlotUpdateStatus.setErrored(true);
                log.info("Error slots mismatch with available seats");
            }
        }

        if (!schoolSlotUpdateStatus.isErrored()){
            log.info("schoolSlotUpdateStatus is not errored");
            slotRepository.saveAll(slots);
            log.info("slots() save in slot table {}", slots);
            for (IndividualStudentSlotDataDto individualStudentSlotDataDto : data){

                individualStudentService.updateExamSlotAndDemoSlotDateTimeForIndvStudent(individualStudentSlotDataDto.getRollNo(), individualStudentSlotDataDto.getExamTheme(),
                        individualStudentSlotDataDto.getDateofExam(), individualStudentSlotDataDto.getSlotdatetime());
                log.info("individualStudentstable is updated with slot timing for rollnumber and examtheme {} {}",
                        individualStudentSlotDataDto.getRollNo(), individualStudentSlotDataDto.getExamTheme());
            }
        }
        log.info("schoolSlotUpdateStatus {}", schoolSlotUpdateStatus);
        return schoolSlotUpdateStatus;
    }
}
