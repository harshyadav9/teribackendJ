package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudentPaymentData;
import com.exam.backend.entity.PaymentDetail;

import java.util.List;

public interface PaymentDetailService {

    void savePaymentDetail(PaymentDetail paymentDetail);

    void savePaymentDetailsForOffline(List<PaymentDetail> paymentDetailsOffline);

    IndividualStudentPaymentData getPaymentDetailForIndiStudent(String rollNumber);

    PaymentDetail getPaymentDetailDataForOrderId(String orderId);

    int updatePaymentDetail(String orderId, String paymentId);

}
