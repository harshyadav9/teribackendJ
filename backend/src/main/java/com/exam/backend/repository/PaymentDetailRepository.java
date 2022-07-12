package com.exam.backend.repository;

import com.exam.backend.entity.PaymentDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepository extends CrudRepository<PaymentDetail, Integer> {

}
