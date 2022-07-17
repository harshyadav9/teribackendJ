package com.exam.backend.repository;

import com.exam.backend.entity.HelpdeskTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpdeskTicketRepository extends CrudRepository<HelpdeskTicket, Integer> {

}
