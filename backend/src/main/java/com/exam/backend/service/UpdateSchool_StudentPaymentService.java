package com.exam.backend.service;

import com.exam.backend.entity.PaymentDetail;
import com.exam.backend.pojo.PaymentDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateSchool_StudentPaymentService {

    Logger log = LoggerFactory.getLogger(UpdateSchool_StudentPaymentService.class);

    private final PaymentDetailServiceImpl paymentDetailService;
    private final InternationalStudantsServiceImpl internationalStudantsService;

    @Autowired
    public UpdateSchool_StudentPaymentService(PaymentDetailServiceImpl paymentDetailService, InternationalStudantsServiceImpl internationalStudantsService) {
        this.paymentDetailService = paymentDetailService;
        this.internationalStudantsService = internationalStudantsService;
    }

    public String updatePaymentData(PaymentDetailDto paymentDetailDto){

        //update in paymentDetails and InternationalStudants tabels
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

//        if (paymentDetailDto.getPaymentReceivedStatus() != null && paymentDetailDto.getPaymentReceivedStatus().equalsIgnoreCase("Success")){
//            internationalStudantsService.updatePaymentFlagForSchoolsPaid(paymentDetailDto.getSchoolcode_Rollno());
//
//        }

        return "Payment Details Updated Successfully.";
    }

    public void generateRollNumber(){

    }


}
