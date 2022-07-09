package com.exam.backend.controller;

import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.pojo.SchoolSlotDataIncoming;
import com.exam.backend.pojo.InternationalStudantsDto;
import com.exam.backend.pojo.SchoolSlotUpdateStatus;
import com.exam.backend.service.SaveDataToDb;
import com.exam.backend.service.SlotServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terry/olympiad")
@CrossOrigin(origins = "*")
public class TerryController {

    Logger log = LoggerFactory.getLogger(TerryController.class);

    private final SaveDataToDb saveDataToDb;

    private final SlotServiceImpl slotService;

    @Autowired
    public TerryController(SaveDataToDb saveDataToDb, SlotServiceImpl slotService) {

        this.saveDataToDb = saveDataToDb;
        this.slotService = slotService;
    }

    @PostMapping(value = "/uploadData")
    public ResponseEntity<String> saveData(@RequestBody List<InternationalStudantsDto> data) {
        log.info("inside saveData() {}", data);
        saveDataToDb.saveData(data);
        log.info("Exiting saveData() successfully.");
        return ResponseEntity.status(HttpStatus.OK).body("Mauj Karo");
    }

    @GetMapping(value = "/getSlotsData")
    public ResponseEntity<List<SchoolSlotData>> getSlotsData(@RequestParam String schoolId, @RequestParam String mode) {
        log.info("inside getSlotsData() {} {}", schoolId, mode);
        List<SchoolSlotData> li = slotService.getSlotsData(schoolId, mode);
        log.info("Exiting getSlotsData() {}", li);
        return ResponseEntity.status(HttpStatus.OK).body(li);
    }

    @PostMapping(value = "/updateSchoolAndSlotDetail")
    public ResponseEntity<SchoolSlotUpdateStatus> updateSchoolAndSlotDetail(@RequestBody List<SchoolSlotDataIncoming> data) {
        log.info("inside updateSchoolAndSlotDetail() {}", data);
        SchoolSlotUpdateStatus schoolSlotUpdateStatus = slotService.updateSlotData(data);
        log.info("Exiting updateSchoolAndSlotDetail() {}", schoolSlotUpdateStatus);
        return ResponseEntity.status(HttpStatus.OK).body(schoolSlotUpdateStatus);
    }
}