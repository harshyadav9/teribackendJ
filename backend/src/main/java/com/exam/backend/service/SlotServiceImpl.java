package com.exam.backend.service;

import com.exam.backend.entity.*;
import com.exam.backend.pojo.IndividualStudentSlotDataDto;
import com.exam.backend.pojo.SchoolSlotDataIncoming;
import com.exam.backend.pojo.SchoolSlotUpdateStatus;
import com.exam.backend.repository.IndividualStudentRepository;
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
    private final AllotedSlotServiceImpl allotedSlotService;
    private final IndividualStudentServiceImpl individualStudentService;

    @Autowired
    public SlotServiceImpl(SlotRepository slotRepository, InternationalStudantsServiceImpl internationalStudantsService, AllotedSlotServiceImpl allotedSlotService, IndividualStudentServiceImpl individualStudentService) {
        this.slotRepository = slotRepository;
        this.internationalStudantsService = internationalStudantsService;
        this.allotedSlotService = allotedSlotService;
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

        List<Slot> slots = new ArrayList<>();
        List<AllotedSlot> allotedSlots = new ArrayList<>();

        for (SchoolSlotDataIncoming schoolSlotData : data){
            Slot slot = slotRepository.findBySlotId(schoolSlotData.getSlotId());
            if (slot.getSeatAvailable() > schoolSlotData.getStudentCount()){
                slot.setSeatAvailable(slot.getSeatAvailable() - schoolSlotData.getStudentCount());
                slots.add(slot);

                AllotedSlot allotedSlot = new AllotedSlot();
                AllotedSlotId id = new AllotedSlotId();
                id.setSlotId(slot.getSlotId());
                id.setAllotedSchoolId(schoolSlotData.getSchoolId());
                allotedSlot.setId(id);
                allotedSlots.add(allotedSlot);
                log.info("alloted slots Inside updateSlotData(){}", allotedSlots);
            }
            else {
                mp.put("Error", "Slot selected for examTheme " + slot.getExamTheme() + " is not having required available seats.");
                schoolSlotUpdateStatus.setStatus(mp);
                schoolSlotUpdateStatus.setErrored(true);
                log.info("Error slots mismatch with avaialable seats");
            }
        }

        if (!schoolSlotUpdateStatus.isErrored()){
            log.info("schoolSlotUpdateStatus is not errored");
            slotRepository.saveAll(slots);
            log.info("slots() save in slot table {}", slots);
            for (SchoolSlotDataIncoming schoolSlotData: data){

                internationalStudantsService.updateExamSlotAndDemoSlotDateTime(schoolSlotData.getSchoolId(), schoolSlotData.getExamTheme(),
                        String.valueOf(schoolSlotData.getDateOfExam()), schoolSlotData.getSlotDatetime() );
                log.info("internationalStudantsService table is updated with slot timing for school and examtheme {} {}",
                        schoolSlotData.getSchoolId(), schoolSlotData.getExamTheme());
            }
            allotedSlotService.saveAll(allotedSlots);
            log.info("allotedSlot table is updated successfully", allotedSlots);

        }
        log.info("schoolSlotUpdateStatus {}", schoolSlotUpdateStatus);
        mp.put("Success", "Slot is booked successfully.");
        schoolSlotUpdateStatus.setStatus(mp);
        return schoolSlotUpdateStatus;
    }

    @Override
    public SchoolSlotUpdateStatus updateSlotDataForIndvStudents(List<IndividualStudentSlotDataDto> data) {
        log.info("Inside updateSlotDataForIndvStudents(){}", data);
        SchoolSlotUpdateStatus schoolSlotUpdateStatus = new SchoolSlotUpdateStatus();
        Map<String,String> mp = new HashMap<>();

        List<Slot> slots = new ArrayList<>();
        List<AllotedSlot> allotedSlots = new ArrayList<>();

        for (IndividualStudentSlotDataDto individualStudentSlotDataDto : data){
            Slot slot = slotRepository.findBySlotId(individualStudentSlotDataDto.getSlotID());
            if (slot.getSeatAvailable() > 0){
                slot.setSeatAvailable(slot.getSeatAvailable() - 1);
                slots.add(slot);

                AllotedSlot allotedSlot = new AllotedSlot();
                AllotedSlotId id = new AllotedSlotId();
                id.setSlotId(slot.getSlotId());
                id.setAllotedSchoolId(individualStudentSlotDataDto.getRollNo());
                allotedSlot.setId(id);
                allotedSlots.add(allotedSlot);
                log.info("alloted slots Inside updateSlotDataForIndvStudents(){}", allotedSlots);
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
            allotedSlotService.saveAll(allotedSlots);
            log.info("allotedSlot table is updated successfully", allotedSlots);

        }
        log.info("schoolSlotUpdateStatus {}", schoolSlotUpdateStatus);
        return schoolSlotUpdateStatus;
    }

   /* @Override
    public List<IndividualStudentSlotData> getSlotsDataForIndvStudents(String rollNumber, String mode) {
        return individualStudentRepository.getSlotDataForIndvStudents(rollNumber, mode);
    }*/

}
