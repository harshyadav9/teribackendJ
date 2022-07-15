package com.exam.backend.controller;

import com.exam.backend.entity.IndividualStudentPaymentData;
import com.exam.backend.entity.IndividualStudentSlotData;
import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.pojo.*;
import com.exam.backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final InternationalStudantsServiceImpl internationalStudantsService;
    private final SchoolServiceImpl schoolService;
    private final IndividualStudentServiceImpl individualStudentService;
    private final PaymentDetailServiceImpl paymentDetailService;

    @Autowired
    public TerryController(SaveDataToDb saveDataToDb, SlotServiceImpl slotService, UpdateSchool_StudentPaymentService updateSchool_studentPaymentService,
                           InternationalStudantsServiceImpl internationalStudantsService, SchoolServiceImpl schoolService, IndividualStudentServiceImpl individualStudentService, PaymentDetailServiceImpl paymentDetailService) {

        this.saveDataToDb = saveDataToDb;
        this.slotService = slotService;
        this.updateSchool_studentPaymentService = updateSchool_studentPaymentService;
        this.internationalStudantsService = internationalStudantsService;
        this.schoolService = schoolService;
        this.individualStudentService = individualStudentService;
        this.paymentDetailService = paymentDetailService;
    }

    @PostMapping(value = "/uploadSchoolData")
    public ResponseEntity<String> uploadSchoolData(@RequestBody List<InternationalStudantsDto> data) {
        log.info("inside uploadSchoolData() {}", data);
        saveDataToDb.saveData(data);
        log.info("Exiting uploadSchoolData() successfully.");
        return ResponseEntity.status(HttpStatus.OK).body("Data is saved successfully");
    }

    @GetMapping(value = "/getSlotsData")
    public ResponseEntity<List<SchoolSlotData>> getSlotsData(@RequestParam String schoolId, @RequestParam String mode) {
        log.info("inside getSlotsData() {} {}", schoolId, mode);
        List<SchoolSlotData> li = slotService.getSlotsData(schoolId, mode);
        log.info("Exiting getSlotsData() {}", li);
        return ResponseEntity.status(HttpStatus.OK).body(li);
    }

    @PostMapping(value = "/updateSchoolSlotDetail")
    public ResponseEntity<SchoolSlotUpdateStatus> updateSchoolSlotDetail(@RequestBody List<SchoolSlotDataIncoming> data) {
        log.info("inside updateSchoolSlotDetail() {}", data);
        SchoolSlotUpdateStatus schoolSlotUpdateStatus = slotService.updateSlotData(data);
        log.info("Exiting updateSchoolSlotDetail() {}", schoolSlotUpdateStatus);
        return ResponseEntity.status(HttpStatus.OK).body(schoolSlotUpdateStatus);
    }

    @PostMapping(value = "/insertPaymentDetails")
    public ResponseEntity<String> insertPaymentDetails(@RequestBody PaymentDetailDto paymentDetailDto) {
        log.info("inside insertPaymentDetails() {}", paymentDetailDto);
        String string = updateSchool_studentPaymentService.updatePaymentData(paymentDetailDto);
        log.info("Exiting insertPaymentDetails() {}", string);
        return ResponseEntity.status(HttpStatus.OK).body(string);
    }

    @PostMapping(value = "/generateSchoolRollNumber")
    public ResponseEntity<String> generateSchoolRollNumber(@RequestBody SchoolDto rollNumberDto) {
        log.info("inside generateSchoolRollNumber() {}", rollNumberDto);
        String message = internationalStudantsService.generateAndUpdateRollNumberForSchoolStudent(rollNumberDto.getSchoolId());
        log.info("Exiting generateSchoolRollNumber() {}", rollNumberDto);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping(value = "/changeSchoolPassword")
    public ResponseEntity<String> changeSchoolPassword(@RequestBody SchoolDto rollNumberDto) {
        log.info("inside changeSchoolPassword() {}", rollNumberDto);
        String resp = schoolService.updatePassword(rollNumberDto);
        log.info("Exiting changeSchoolPassword() {}", rollNumberDto);
        if (resp.equals("Invalid schoolId.Password Update failed.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Password is Updated Successfully.");
        }
    }

    @PostMapping(value = "/registerStudent")
    public ResponseEntity<String> registerStudent(@RequestBody IndividualStudentDto individualStudentDto) {
        log.info("inside registerStudent() {}", individualStudentDto);
        String rollNumber = individualStudentService.saveStudent(individualStudentDto);
        log.info("Completed registerStudent() {}", rollNumber);
        if (rollNumber != null){
            return ResponseEntity.status(HttpStatus.OK).body(rollNumber);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to generate");
        }
    }

    @GetMapping(value = "/viewIndividualStudentDetails")
    public ResponseEntity<IndividualStudentDto> viewIndividualStudentDetails(@RequestParam String rollNumber) {
        log.info("inside viewIndividualStudentDetails() {}", rollNumber);
        IndividualStudentDto individualStudentDto = individualStudentService.getIndividualStudentDetail(rollNumber);

        log.info("Exiting viewIndividualStudentDetails() {}", individualStudentDto);
        if (individualStudentDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(individualStudentDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new IndividualStudentDto());
        }
    }

    @PostMapping(value = "/updateIndividualStudentDetails")
    public ResponseEntity<String> updateIndividualStudentDetails(@RequestBody IndividualStudentDto individualStudentDto) {
        log.info("inside updateIndividualStudentDetails() {}", individualStudentDto);
        int count = individualStudentService.updateIndividualStudentData(individualStudentDto);
        if (count == 1){
            log.info("Student record is updated successfully for rollNumber {}", individualStudentDto.getRollNo());
            return ResponseEntity.status(HttpStatus.OK).body("Individual Student Data updated successfully.");

        }else {
            log.info("Student record is updated successfully for rollNumber {}", individualStudentDto.getRollNo());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Roll Number.");
        }

    }

    @GetMapping(value = "/getPaymentDetailsForIndividualStudent")
    public ResponseEntity<IndividualStudentPaymentData> getPaymentDetailsForIndividualStudent(@RequestParam String rollNumber) {
        log.info("inside getPaymentDetailsForIndividualStudent() {}", rollNumber);
        IndividualStudentPaymentData individualStudentPaymentData = paymentDetailService.getPaymentDetailForIndiStudent(rollNumber);
        log.info("Exiting getPaymentDetailsForIndividualStudent()");
        return ResponseEntity.status(HttpStatus.OK).body(individualStudentPaymentData);
    }

    @GetMapping(value = "/getSlotsDataForIndividualStudent")
    public ResponseEntity<List<IndividualStudentSlotData>> getSlotsDataForIndividualStudent(@RequestParam String rollNumber, @RequestParam String mode) {
        log.info("inside getSlotsDataForIndividualStudent() {} {}", rollNumber, mode);
        List<IndividualStudentSlotData> li = individualStudentService.getSlotsDataForIndvStudents(rollNumber, mode);
        log.info("Exiting getSlotsDataForIndividualStudent() {}", li);
        return ResponseEntity.status(HttpStatus.OK).body(li);
    }

    @PostMapping(value = "/updateSlotsDataForIndividualStudent")
    public ResponseEntity<SchoolSlotUpdateStatus> updateSlotsDataForIndividualStudent(@RequestBody List<IndividualStudentSlotDataDto> incomingData) {
        log.info("inside updateSlotsDataForIndividualStudent() {}", incomingData);
        SchoolSlotUpdateStatus schoolSlotUpdateStatus = slotService.updateSlotDataForIndvStudents(incomingData);
        log.info("Exiting updateSlotsDataForIndividualStudent() {}", schoolSlotUpdateStatus);
        return ResponseEntity.status(HttpStatus.OK).body(schoolSlotUpdateStatus);
    }
}