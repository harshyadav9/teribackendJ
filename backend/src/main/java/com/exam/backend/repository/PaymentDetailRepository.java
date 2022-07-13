package com.exam.backend.repository;

import com.exam.backend.entity.IndividualStudentPaymentData;
import com.exam.backend.entity.PaymentDetail;
import com.exam.backend.entity.SchoolSlotData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailRepository extends CrudRepository<PaymentDetail, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM vwIndividualStudentFeeDetail where RollNo = :rollNumber")
    IndividualStudentPaymentData getData(String rollNumber);

}
