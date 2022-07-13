package com.exam.backend.controller;

import com.exam.backend.entity.IndividualStudent;
import com.exam.backend.entity.IndividualStudentPaymentData;
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

    @PostMapping(value = "/generateRollNumber")
    public ResponseEntity<String> generateRollNumber(@RequestBody SchoolDto rollNumberDto) {
        log.info("inside generateRollNumber() {}", rollNumberDto);
        internationalStudantsService.generateAndUpdateRollNumberForSchoolStudent(rollNumberDto.getSchoolId());
        log.info("Exiting generateRollNumber() {}", rollNumberDto);
        return ResponseEntity.status(HttpStatus.OK).body("Roll Numbers are updated successfully.");
    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody SchoolDto rollNumberDto) {
        log.info("inside changePassword() {}", rollNumberDto);
        String resp = schoolService.updatePassword(rollNumberDto);
        log.info("Exiting changePassword() {}", rollNumberDto);
        if (resp.equals("Invalid schoolId.Password Update failed.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Password is Updated Successfully.");
        }

    }

    @PostMapping(value = "/registerStudent")
    public ResponseEntity<String> registerStudent(@RequestBody IndividualStudentDto individualStudentDto) {
        log.info("inside registerStudent() {}", individualStudentDto);
        individualStudentService.saveStudent(individualStudentDto);
        log.info("Exiting registerStudent() {}", individualStudentDto);

        return ResponseEntity.status(HttpStatus.OK).body("Student is Registered Successfully.");

    }

    @GetMapping(value = "/viewIndividualStudentDetails")
    public ResponseEntity<IndividualStudent> viewIndividualStudentDetails(@RequestParam String rollNumber) {
        log.info("inside viewIndividualStudentDetails() {}", rollNumber);
        IndividualStudent student = individualStudentService.getIndividualStudentDetail(rollNumber);

        log.info("Exiting viewIndividualStudentDetails() {}", student);
        if (student != null) {
            return ResponseEntity.status(HttpStatus.OK).body(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new IndividualStudent());
        }

    }

    @PostMapping(value = "/updateIndividualStudentDetails")
    public ResponseEntity<String> updateIndividualStudentDetails(@RequestBody IndividualStudent individualStudent) {
        log.info("inside updateIndividualStudentDetails() {}", individualStudent);
        individualStudentService.updateIndividualStudentData(individualStudent);

        log.info("Exiting updateIndividualStudentDetails()");
        return ResponseEntity.status(HttpStatus.OK).body("Individual Student Data updated successfully");
    }

    @GetMapping(value = "/getPaymentDetailsForIndividualStudent")
    public ResponseEntity<IndividualStudentPaymentData> getPaymentDetailsForIndividualStudent(@RequestParam String rollNumber) {
        log.info("inside getPaymentDetailsForIndividualStudent() {}", rollNumber);
        IndividualStudentPaymentData individualStudentPaymentData = paymentDetailService.getPaymentDetailForIndiStudent(rollNumber);

        log.info("Exiting getPaymentDetailsForIndividualStudent()");
        return ResponseEntity.status(HttpStatus.OK).body(individualStudentPaymentData);
    }
}