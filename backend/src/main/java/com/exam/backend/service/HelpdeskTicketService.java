package com.exam.backend.service;

import com.exam.backend.entity.TicketDetail;
import com.exam.backend.pojo.HelpdeskTicketDto;

import java.util.List;

public interface HelpdeskTicketService {

    Integer createHelpdeskTicket(HelpdeskTicketDto helpdeskTicketDto);
    String updateHelpdeskTicket(HelpdeskTicketDto helpdeskTicketDto);
    List<TicketDetail> getHelpdeskTicketDetails(String school_roll_id);
    List<TicketDetail> getHelpdeskTicketDetailsForAdmin();

}
