package com.exam.backend.service;

import com.exam.backend.entity.HelpdeskTicket;
import com.exam.backend.entity.HelpdeskTicketDetail;
import com.exam.backend.entity.TicketDetail;
import com.exam.backend.pojo.HelpdeskTicketDto;
import com.exam.backend.repository.HelpdeskTicketDetailRepository;
import com.exam.backend.repository.HelpdeskTicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class HelpdeskTicketServiceImpl implements HelpdeskTicketService {

    Logger log = LoggerFactory.getLogger(HelpdeskTicketServiceImpl.class);

    private final HelpdeskTicketDetailRepository helpdeskTicketDetailRepository;

    private final HelpdeskTicketRepository helpdeskTicketRepository;

    @Autowired
    public HelpdeskTicketServiceImpl(HelpdeskTicketRepository helpdeskTicketRepository, HelpdeskTicketDetailRepository helpdeskTicketDetailRepository) {
        this.helpdeskTicketRepository = helpdeskTicketRepository;
        this.helpdeskTicketDetailRepository = helpdeskTicketDetailRepository;
    }

    @Override
    public Integer createHelpdeskTicket(HelpdeskTicketDto helpdeskTicketDto) {
        HelpdeskTicket helpdeskTicket = new HelpdeskTicket();
        helpdeskTicket.setCreatedBy(helpdeskTicketDto.getCreatedBy());
        helpdeskTicket.setCategoryID(helpdeskTicketDto.getCategoryID());
        helpdeskTicket.setModifiedBy(helpdeskTicketDto.getModifiedBy());
        helpdeskTicket.setSubject(helpdeskTicketDto.getSubject());
        helpdeskTicket.setStatusID(helpdeskTicketDto.getStatusID());
        helpdeskTicket.setSubscriberType(helpdeskTicketDto.getSubscriberType());
        helpdeskTicket.setSchoolID_RollNo(helpdeskTicketDto.getSchoolID_RollNo());

        HelpdeskTicket helpdeskTicket1 = helpdeskTicketRepository.save(helpdeskTicket);

        if (helpdeskTicket1 != null) {
            createHelpdeskDetailData(helpdeskTicketDto, helpdeskTicket1.getTicketID());
            return helpdeskTicket1.getTicketID();
        } else {
            throw new RuntimeException("Issue in saving ticket data in helpdeskTicket table. ");
        }
    }

    @Override
    public String updateHelpdeskTicket(HelpdeskTicketDto helpdeskTicketDto) {

       Optional<HelpdeskTicket> helpdeskTicket = helpdeskTicketRepository.findById(helpdeskTicketDto.getTicketID());
       if (helpdeskTicket.isPresent()){
           helpdeskTicket.get().setStatusID(helpdeskTicketDto.getStatusID());
           helpdeskTicket.get().setModifiedBy(helpdeskTicketDto.getModifiedBy());
           helpdeskTicketRepository.save(helpdeskTicket.get());

           createHelpdeskDetailData(helpdeskTicketDto, helpdeskTicketDto.getTicketID());
           return "Ticket Details are successfully updated.";
       } else {
           return "Invalid Ticket Id.";
       }
    }

    @Override
    public List<TicketDetail> getHelpdeskTicketDetails(String school_roll_id) {
        List<TicketDetail> tickets = helpdeskTicketDetailRepository.getTicketData(school_roll_id);
        return tickets;
    }

    private void createHelpdeskDetailData(HelpdeskTicketDto helpdeskTicketDto, Integer ticketId){
        HelpdeskTicketDetail helpdeskTicketDetail = new HelpdeskTicketDetail();
        helpdeskTicketDetail.setTicketID(ticketId);
        helpdeskTicketDetail.setTicketStatusID(helpdeskTicketDto.getStatusID());
        helpdeskTicketDetail.setMessage(helpdeskTicketDto.getMessage());
        helpdeskTicketDetail.setCreatedBy(helpdeskTicketDto.getCreatedBy());

        helpdeskTicketDetailRepository.save(helpdeskTicketDetail);
    }
}
