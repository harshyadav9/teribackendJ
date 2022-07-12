package com.exam.backend.service;

import com.exam.backend.entity.PaymentDetail;
import com.exam.backend.repository.PaymentDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        paymentDetailRepository.save(paymentDetail);

    }
}
