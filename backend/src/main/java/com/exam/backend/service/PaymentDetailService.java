package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudentPaymentData;
import com.exam.backend.entity.PaymentDetail;

public interface PaymentDetailService {

    void savePaymentDetail(PaymentDetail paymentDetail);

    IndividualStudentPaymentData getPaymentDetailForIndiStudent(String rollNumber);

}
