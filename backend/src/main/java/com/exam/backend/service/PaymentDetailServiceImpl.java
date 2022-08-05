package com.exam.backend.service;

import com.exam.backend.entity.IndividualStudentPaymentData;
import com.exam.backend.entity.PaymentDetail;
import com.exam.backend.pojo.PaymentDetailDto;
import com.exam.backend.repository.PaymentDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PaymentDetailServiceImpl implements  PaymentDetailService{

    Logger log = LoggerFactory.getLogger(PaymentDetailServiceImpl.class);

    private final PaymentDetailRepository paymentDetailRepository;

    @Autowired
    public PaymentDetailServiceImpl(PaymentDetailRepository paymentDetailRepository) {
        this.paymentDetailRepository = paymentDetailRepository;
    }

    @Override
    public void savePaymentDetail(PaymentDetail paymentDetail) {
        log.info("Inside savePaymentDetail() {}", paymentDetail.getOrderId());
        paymentDetailRepository.save(paymentDetail);
        log.info("Completed savePaymentDetail() {}", paymentDetail.getOrderId());

    }

    @Override
    public void savePaymentDetailsForOffline(List<PaymentDetail> paymentDetailsOffline) {
        paymentDetailRepository.saveAll(paymentDetailsOffline);
    }

    @Override
    public IndividualStudentPaymentData getPaymentDetailForIndiStudent(String rollNumber) {
       return paymentDetailRepository.getData(rollNumber);

    }
    @Override
    public PaymentDetail getPaymentDetailDataForOrderId(String orderId) {
        return paymentDetailRepository.getPaymentData(orderId);
    }

    @Override
    public int updatePaymentDetail(String orderId, String paymentId) {
        return paymentDetailRepository.updatePaymentDetail(orderId, paymentId);
    }
}
