package com.exam.backend.controller;

import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.pojo.PaymentDetailDto;
import com.exam.backend.pojo.SchoolSlotDataIncoming;
import com.exam.backend.pojo.InternationalStudantsDto;
import com.exam.backend.pojo.SchoolSlotUpdateStatus;
import com.exam.backend.service.SaveDataToDb;
import com.exam.backend.service.SlotServiceImpl;
import com.exam.backend.service.UpdateSchool_StudentPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terry")
@CrossOrigin(origins = "*")
public class TerryController {

    Logger log = LoggerFactory.getLogger(TerryController.class);

    private final SaveDataToDb saveDataToDb;
    private final SlotServiceImpl slotService;
    private final UpdateSchool_StudentPaymentService updateSchool_studentPaymentService;

    @Autowired
    public TerryController(SaveDataToDb saveDataToDb, SlotServiceImpl slotService, UpdateSchool_StudentPaymentService updateSchool_studentPaymentService) {

        this.saveDataToDb = saveDataToDb;
        this.slotService = slotService;
        this.updateSchool_studentPaymentService = updateSchool_studentPaymentService;
    }

    @PostMapping(value = "/uploadData")
    public ResponseEntity<String> saveData(@RequestBody List<InternationalStudantsDto> data) {
        log.info("inside saveData() {}", data);
        saveDataToDb.saveData(data);
        log.info("Exiting saveData() successfully.");
        return ResponseEntity.status(HttpStatus.OK).body("Data is saved successfully");
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

    @PostMapping(value = "/updatePaymentDetails")
    public ResponseEntity<String> updatePaymentDetails(@RequestBody PaymentDetailDto paymentDetailDto) {
        log.info("inside updatePaymentDetails() {}", paymentDetailDto);
        String string = updateSchool_studentPaymentService.updatePaymentData(paymentDetailDto);
        log.info("Exiting updatePaymentDetails() {}", string);
        return ResponseEntity.status(HttpStatus.OK).body(string);
    }

}