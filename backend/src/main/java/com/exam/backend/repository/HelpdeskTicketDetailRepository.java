package com.exam.backend.repository;

import com.exam.backend.entity.HelpdeskTicketDetail;
import com.exam.backend.entity.IndividualStudentSlotData;
import com.exam.backend.entity.TicketDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpdeskTicketDetailRepository extends CrudRepository<HelpdeskTicketDetail, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetHelpdeskTicketDetail where schoolID_RollNo = :school_roll_id")
    List<TicketDetail> getTicketData(String school_roll_id);

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetHelpdeskTicketDetail")
    List<TicketDetail> getTicketDataForAdmin();

}
