package com.exam.backend.service;

import com.exam.backend.entity.PaymentDetail;
import com.exam.backend.pojo.PaymentDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UpdateSchool_StudentPaymentService {

    Logger log = LoggerFactory.getLogger(UpdateSchool_StudentPaymentService.class);

    private final PaymentDetailServiceImpl paymentDetailService;
    private final InternationalStudantsServiceImpl internationalStudantsService;
    private final IndividualStudentServiceImpl individualStudentService;

    @Autowired
    public UpdateSchool_StudentPaymentService(PaymentDetailServiceImpl paymentDetailService, InternationalStudantsServiceImpl internationalStudantsService, IndividualStudentServiceImpl individualStudentService) {
        this.paymentDetailService = paymentDetailService;
        this.internationalStudantsService = internationalStudantsService;
        this.individualStudentService = individualStudentService;
    }

    public String insertPaymentData(PaymentDetailDto paymentDetailDto){
        log.info("Inside updatePaymentData() {}", paymentDetailDto.getOrderId());
        PaymentDetail paymentDetail = new PaymentDetail();

        paymentDetail.setOrderId(paymentDetailDto.getOrderId());
        paymentDetail.setPaymentId(paymentDetailDto.getPaymentId());
        paymentDetail.setAmount(paymentDetailDto.getAmount());
        paymentDetail.setCreatedBy(paymentDetailDto.getCreatedBy());
        paymentDetail.setModifyBy(paymentDetailDto.getModifyBy());
        paymentDetail.setSchoolcode_Rollno(paymentDetailDto.getSchoolcode_Rollno());
        paymentDetail.setSubscriberType(paymentDetailDto.getSubscriberType());
        paymentDetail.setPaymentReceivedStatus(paymentDetailDto.getPaymentReceivedStatus());
        paymentDetail.setMode(paymentDetailDto.getMode());

        paymentDetailService.savePaymentDetail(paymentDetail);
        log.info("Saved payment details successfully in updatePaymentData() {}", paymentDetailDto.getOrderId());
        return "Payment Details Updated Successfully.";
    }

    public String insertPaymentDataForOffline(List<PaymentDetailDto> paymentDetailDtoListOffline){
        log.info("Inside insertPaymentDataForOffline() {}", paymentDetailDtoListOffline);

        List<PaymentDetail> paymentDetailList = new ArrayList<>();

        for (PaymentDetailDto paymentDetailDto : paymentDetailDtoListOffline){
            PaymentDetail paymentDetail = new PaymentDetail();

            paymentDetail.setOrderId(paymentDetailDto.getOrderId());
            paymentDetail.setPaymentId(paymentDetailDto.getPaymentId());
            paymentDetail.setAmount(paymentDetailDto.getAmount());
            paymentDetail.setCreatedBy(paymentDetailDto.getCreatedBy());
            paymentDetail.setModifyBy("Admin");
            paymentDetail.setSchoolcode_Rollno(paymentDetailDto.getSchoolcode_Rollno());
            paymentDetail.setSubscriberType(paymentDetailDto.getSubscriberType());
            paymentDetail.setPaymentReceivedStatus(paymentDetailDto.getPaymentReceivedStatus());
            paymentDetail.setMode(paymentDetailDto.getMode());
            paymentDetailList.add(paymentDetail);

            int count = internationalStudantsService.updatePaymentFlagForSchool(paymentDetailDto.getSchoolcode_Rollno(), paymentDetailDto.getOrderId());
            log.info("Rows updated in internationalStudants in insertPaymentDataForOffline() :" +count);
        }

        paymentDetailService.savePaymentDetailsForOffline(paymentDetailList);
        log.info("Saved payment details successfully in insertPaymentDataForOffline() {}", paymentDetailList);
        return "Payment Details Updated Successfully.";
    }


    public String updatePaymentData(List<PaymentDetailDto> paymentDetailDtoList){
        log.info("Inside updatePaymentData() {}", paymentDetailDtoList);

        for (PaymentDetailDto paymentDetailDto : paymentDetailDtoList){
            PaymentDetail paymentDetail = paymentDetailService.getPaymentDetailDataForOrderId(paymentDetailDto.getOrderId());
            if (paymentDetail.getSubscriberType().equalsIgnoreCase("SCHOOL")){

                int count = internationalStudantsService.updatePaymentFlagForSchool(paymentDetail.getSchoolcode_Rollno(), paymentDetailDto.getOrderId());
                log.info("Rows updated in internationalStudants :" +count);
                paymentDetailService.updatePaymentDetail(paymentDetail.getOrderId(), paymentDetailDto.getPaymentId());
            }else {

                individualStudentService.updatePaymentFlagForStudent(paymentDetail.getSchoolcode_Rollno());
                paymentDetailService.updatePaymentDetail(paymentDetail.getOrderId(), paymentDetailDto.getPaymentId());
            }
        }

        log.info("Exiting updatePaymentData().");
        return "Payment Details Updated Successfully.";
    }

}
