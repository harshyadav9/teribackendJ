package com.exam.backend.controller;

import com.exam.backend.entity.IndividualStudentPaymentData;
import com.exam.backend.entity.IndividualStudentSlotData;
import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.entity.TicketDetail;
import com.exam.backend.pojo.*;
import com.exam.backend.service.*;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
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
    private final DownloadExcelTemplateHelper downloadExcelTemplateHelper;
    private final HelpdeskTicketServiceImpl helpdeskTicketService;
    private final ChangePasswordService changePasswordService;
    private final ConfigServiceImpl configService;

    @Autowired
    private Environment env;

    @Autowired
    public TerryController(SaveDataToDb saveDataToDb, SlotServiceImpl slotService, UpdateSchool_StudentPaymentService updateSchool_studentPaymentService,
                           InternationalStudantsServiceImpl internationalStudantsService, SchoolServiceImpl schoolService, IndividualStudentServiceImpl individualStudentService, PaymentDetailServiceImpl paymentDetailService,
                           DownloadExcelTemplateHelper downloadExcelTemplateHelper, HelpdeskTicketServiceImpl helpdeskTicketService, ChangePasswordService changePasswordService, ConfigServiceImpl configService) {

        this.saveDataToDb = saveDataToDb;
        this.slotService = slotService;
        this.updateSchool_studentPaymentService = updateSchool_studentPaymentService;
        this.internationalStudantsService = internationalStudantsService;
        this.schoolService = schoolService;
        this.individualStudentService = individualStudentService;
        this.paymentDetailService = paymentDetailService;
        this.downloadExcelTemplateHelper = downloadExcelTemplateHelper;
        this.helpdeskTicketService = helpdeskTicketService;
        this.changePasswordService = changePasswordService;
        this.configService = configService;
    }

    @GetMapping(value = "/testLoadedEnv")
    public ResponseEntity<String> testLoadedEnv() {
        return ResponseEntity.status(HttpStatus.OK).body(env.getProperty("db.env"));
    }

    @PostMapping(value = "/uploadSchoolData")
    public ResponseEntity<String> uploadSchoolData(@RequestBody List<InternationalStudantsDto> data) throws SQLIntegrityConstraintViolationException {
        log.info("inside uploadSchoolData() {}", data);
        String continueFLag = configService.getConfigDataForConfigName("uploadSchoolData");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            try{
                String msg = saveDataToDb.saveData(data);
                log.info(msg);
                return ResponseEntity.status(HttpStatus.OK).body(msg);
            } catch(Exception e){
                log.error(e.getMessage());
                if (e.getMessage().contains("ConstraintViolationException")){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }

        } else {
            log.info("Enable flag is false. No data returned in uploadSchoolData()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }
    }

    @GetMapping(value = "/downloadExcelTemplate")
    public void downloadExcelTemplate(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=StudentUploadTemplate.xlsx");
        ByteArrayInputStream stream = downloadExcelTemplateHelper.downloadTemplate();
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping(value = "/getSlotsData")
    public ResponseEntity<List<SchoolSlotData>> getSlotsData(@RequestParam String schoolId, @RequestParam String mode) {
        log.info("Inside getSlotsData() {} {}", schoolId, mode);
        List<SchoolSlotData> li = null;
        String continueFLag = configService.getConfigDataForConfigName("getSlotsData");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            li = slotService.getSlotsData(schoolId, mode);
            log.info("Exiting getSlotsData() {}", li);
            return ResponseEntity.status(HttpStatus.OK).body(li);
        } else {
            log.info("Enable flag is false. No data returned in getSlotsData()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(li);
        }

    }

    @PostMapping(value = "/updateSchoolSlotDetail")
    public ResponseEntity<SchoolSlotUpdateStatus> updateSchoolSlotDetail(@RequestBody List<SchoolSlotDataIncoming> data) {
        log.info("Inside updateSchoolSlotDetail() {}", data);
        SchoolSlotUpdateStatus schoolSlotUpdateStatus = null;
        String continueFLag = configService.getConfigDataForConfigName("updateSchoolSlotDetail");

        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            schoolSlotUpdateStatus = slotService.updateSlotData(data);
            log.info("Exiting updateSchoolSlotDetail() {}", schoolSlotUpdateStatus);
            return ResponseEntity.status(HttpStatus.OK).body(schoolSlotUpdateStatus);
        } else {
            log.info("Enable flag is false. No data returned in updateSchoolSlotDetail()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(schoolSlotUpdateStatus);
        }

    }

    @PostMapping(value = "/insertPaymentDetails")
    public ResponseEntity<String> insertPaymentDetails(@RequestBody PaymentDetailDto paymentDetailDto) {
        log.info("Inside insertPaymentDetails() {}", paymentDetailDto);

        String continueFLag = configService.getConfigDataForConfigName("insertPaymentDetails");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            String string = updateSchool_studentPaymentService.insertPaymentData(paymentDetailDto);
            log.info("Exiting insertPaymentDetails() {}", string);
            return ResponseEntity.status(HttpStatus.OK).body(string);
        } else {
            log.info("Enable flag is false. No data returned in insertPaymentDetails()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }
    }

    @PostMapping(value = "/updatePaymentDetails")
    public ResponseEntity<String> updatePaymentDetails(@RequestBody List<PaymentDetailDto> paymentDetailDtoList) {
        log.info("Inside updatePaymentDetails() {}", paymentDetailDtoList);
        String continueFLag = configService.getConfigDataForConfigName("updatePaymentDetails");

        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            String string = updateSchool_studentPaymentService.updatePaymentData(paymentDetailDtoList);
            log.info("Exiting updatePaymentDetails() {}", string);
            return ResponseEntity.status(HttpStatus.OK).body(string);
        } else {
            log.info("Enable flag is false. No data returned in updatePaymentDetails()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }

    }

    @PostMapping(value = "/insertPaymentDetailsForOffline")
    public ResponseEntity<String> insertPaymentDetailsForOffline(@RequestBody List<PaymentDetailDto> paymentDetailDtoListOffline) {
        log.info("Inside insertPaymentDetailsForOffline() {}", paymentDetailDtoListOffline);

        String continueFLag = configService.getConfigDataForConfigName("insertPaymentDetailsForOffline");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            String string = updateSchool_studentPaymentService.insertPaymentDataForOffline(paymentDetailDtoListOffline);
            log.info("Exiting insertPaymentDetailsForOffline() {}", string);
            return ResponseEntity.status(HttpStatus.OK).body(string);
        } else {
            log.info("Enable flag is false. No data returned in insertPaymentDetailsForOffline()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }

    }

    @PostMapping(value = "/generateSchoolRollNumber")
    public ResponseEntity<String> generateSchoolRollNumber(@RequestBody ChangePasswordDto rollNumberDto) {
        log.info("Inside generateSchoolRollNumber() {}", rollNumberDto);

        String continueFLag = configService.getConfigDataForConfigName("generateSchoolRollNumber");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            String message = internationalStudantsService.generateAndUpdateRollNumberForSchoolStudent(rollNumberDto.getSchoolId());
            log.info("Exiting generateSchoolRollNumber() {}", rollNumberDto);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            log.info("Enable flag is false. No data returned in generateSchoolRollNumber()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }

    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        log.info("Inside changePassword() {}", changePasswordDto);
        String continueFLag = configService.getConfigDataForConfigName("changePassword");

        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            String msg = changePasswordService.changePassword(changePasswordDto);
            log.info("Exiting changePassword() {}", changePasswordDto);
            if (msg.equals("No data found for given input.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(msg);
            }
        } else {
            log.info("Enable flag is false. No data returned in changePassword()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }

    }

    @PostMapping(value = "/registerStudent")
    public ResponseEntity<String> registerStudent(@RequestBody IndividualStudentDto individualStudentDto) {
        log.info("Inside registerStudent() {}", individualStudentDto);
        String continueFLag = configService.getConfigDataForConfigName("registerStudent");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            String rollNumber = individualStudentService.saveStudent(individualStudentDto);
            log.info("Completed registerStudent() {}", rollNumber);
            if (rollNumber != null) {
                return ResponseEntity.status(HttpStatus.OK).body(rollNumber);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to generate");
            }
        } else {
            log.info("Enable FLag is false. Not returning anything in registerStudent()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }
    }

    @GetMapping(value = "/viewIndividualStudentDetails")
    public ResponseEntity<IndividualStudentDto> viewIndividualStudentDetails(@RequestParam String rollNumber) {
        log.info("Inside viewIndividualStudentDetails() {}", rollNumber);
        IndividualStudentDto individualStudentDto = null;
        String continueFLag = configService.getConfigDataForConfigName("viewIndividualStudentDetails");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            individualStudentDto = individualStudentService.getIndividualStudentDetail(rollNumber);

            log.info("Exiting viewIndividualStudentDetails() {}", individualStudentDto);
            if (individualStudentDto != null) {
                return ResponseEntity.status(HttpStatus.OK).body(individualStudentDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new IndividualStudentDto());
            }
        } else {
            log.info("Enable FLag is false. Not returning anything in viewIndividualStudentDetails()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(individualStudentDto);
        }
    }

    @PostMapping(value = "/updateIndividualStudentDetails")
    public ResponseEntity<String> updateIndividualStudentDetails(@RequestBody IndividualStudentDto individualStudentDto) {
        log.info("Inside updateIndividualStudentDetails() {}", individualStudentDto);

        String continueFLag = configService.getConfigDataForConfigName("updateIndividualStudentDetails");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            int count = individualStudentService.updateIndividualStudentData(individualStudentDto);
            if (count == 1) {
                log.info("Student record is updated successfully for rollNumber {}", individualStudentDto.getRollNo());
                return ResponseEntity.status(HttpStatus.OK).body("Individual Student Data updated successfully.");
            } else {
                log.info("Student record is not updated successfully for rollNumber {}", individualStudentDto.getRollNo());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Roll Number.");
            }
        } else {
            log.info("Enable FLag is false. Not returning anything in updateIndividualStudentDetails()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }

    }

    @GetMapping(value = "/getPaymentDetailsForIndividualStudent")
    public ResponseEntity<IndividualStudentPaymentData> getPaymentDetailsForIndividualStudent(@RequestParam String rollNumber) {
        log.info("Inside getPaymentDetailsForIndividualStudent() {}", rollNumber);
        IndividualStudentPaymentData individualStudentPaymentData = null;
        String continueFLag = configService.getConfigDataForConfigName("getPaymentDetailsForIndividualStudent");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            individualStudentPaymentData = paymentDetailService.getPaymentDetailForIndiStudent(rollNumber);
            log.info("Exiting getPaymentDetailsForIndividualStudent()");
            return ResponseEntity.status(HttpStatus.OK).body(individualStudentPaymentData);

        } else {
            log.info("Enable FLag is false. Not returning anything in getPaymentDetailsForIndividualStudent()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(individualStudentPaymentData);
        }

    }

    @GetMapping(value = "/getSlotsDataForIndividualStudent")
    public ResponseEntity<List<IndividualStudentSlotData>> getSlotsDataForIndividualStudent(@RequestParam String rollNumber, @RequestParam String mode) {
        log.info("Inside getSlotsDataForIndividualStudent() {} {}", rollNumber, mode);
        List<IndividualStudentSlotData> li = null;
        String continueFLag = configService.getConfigDataForConfigName("getSlotsDataForIndividualStudent");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            li = individualStudentService.getSlotsDataForIndvStudents(rollNumber, mode);
            log.info("Exiting getSlotsDataForIndividualStudent() {}", li);
            return ResponseEntity.status(HttpStatus.OK).body(li);
        } else {
            log.info("Enable FLag is false. Not returning anything in getSlotsDataForIndividualStudent()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(li);
        }
    }

    @PostMapping(value = "/updateSlotsDataForIndividualStudent")
    public ResponseEntity<SchoolSlotUpdateStatus> updateSlotsDataForIndividualStudent(@RequestBody List<IndividualStudentSlotDataDto> incomingData) {
        log.info("Inside updateSlotsDataForIndividualStudent() {}", incomingData);
        SchoolSlotUpdateStatus schoolSlotUpdateStatus = null;
        String continueFLag = configService.getConfigDataForConfigName("updateSlotsDataForIndividualStudent");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            schoolSlotUpdateStatus = slotService.updateSlotDataForIndvStudents(incomingData);
            log.info("Exiting updateSlotsDataForIndividualStudent() {}", schoolSlotUpdateStatus);
            return ResponseEntity.status(HttpStatus.OK).body(schoolSlotUpdateStatus);
        } else {
            log.info("Enable FLag is false. Not returning anything in updateSlotsDataForIndividualStudent()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(schoolSlotUpdateStatus);
        }

    }

    @PostMapping(value = "/createHelpdeskTicket")
    public ResponseEntity<Integer> createHelpdeskTicket(@RequestBody HelpdeskTicketDto helpdeskTicketDto) {
        log.info("Inside createHelpdeskTicket() {}", helpdeskTicketDto);

        String continueFLag = configService.getConfigDataForConfigName("createHelpdeskTicket");

        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            Integer ticketId = helpdeskTicketService.createHelpdeskTicket(helpdeskTicketDto);
            log.info("Exiting createHelpdeskTicket() {}", ticketId);
            return ResponseEntity.status(HttpStatus.OK).body(ticketId);
        } else {
            log.info("Enable FLag is false. Not returning anything in createHelpdeskTicket()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(0);
        }

    }

    @GetMapping(value = "/getHelpdeskTicketDetails")
    public ResponseEntity<List<TicketDetail>> getHelpdeskTicketDetails(@RequestParam String school_roll_id) {
        log.info("Inside getHelpdeskTicketDetails() {}", school_roll_id);
        List<TicketDetail> ticketDetails = null;

        String continueFLag = configService.getConfigDataForConfigName("getHelpdeskTicketDetails");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            ticketDetails = helpdeskTicketService.getHelpdeskTicketDetails(school_roll_id);
            log.info("Exiting getHelpdeskTicketDetails() {}", school_roll_id);
            if (ticketDetails != null && ticketDetails.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(ticketDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            }
        } else {
            log.info("Enable FLag is false. Not returning anything in getHelpdeskTicketDetails()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ticketDetails);
        }

    }

    @GetMapping(value = "/getHelpdeskTicketDetailsForAdmin")
    public ResponseEntity<List<TicketDetail>> getHelpdeskTicketDetailsForAdmin() {
        log.info("Inside getHelpdeskTicketDetailsForAdmin()");
        List<TicketDetail> ticketDetails = null;
        String continueFLag = configService.getConfigDataForConfigName("getHelpdeskTicketDetailsForAdmin");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            ticketDetails = helpdeskTicketService.getHelpdeskTicketDetailsForAdmin();
            log.info("Exiting getHelpdeskTicketDetails()");
            if (ticketDetails != null && ticketDetails.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(ticketDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            }
        } else {
            log.info("Enable FLag is false. Not returning anything in getHelpdeskTicketDetailsForAdmin()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ticketDetails);
        }

    }

    @PostMapping(value = "/updateHelpdeskTicketDetails")
    public ResponseEntity<String> updateHelpdeskTicketDetails(@RequestBody HelpdeskTicketDto helpdeskTicketDto) {
        log.info("Inside updateHelpdeskTicketDetails() {}", helpdeskTicketDto);

        String continueFLag = configService.getConfigDataForConfigName("updateHelpdeskTicketDetails");
        if (continueFLag != null && continueFLag.equalsIgnoreCase("TRUE")) {
            String msg = helpdeskTicketService.updateHelpdeskTicket(helpdeskTicketDto);
            log.info("Exiting updateHelpdeskTicketDetails() {}", helpdeskTicketDto);
            if (msg != null) {
                return ResponseEntity.status(HttpStatus.OK).body(msg);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }
        } else {
            log.info("Enable FLag is false. Not returning anything in updateHelpdeskTicketDetails()");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Requested Action is disabled. Please check with Admin.");
        }
    }
}