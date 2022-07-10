package com.exam.backend.service;

import com.exam.backend.entity.AllotedSlot;
import com.exam.backend.repository.AllotedSlotRespository;
import com.exam.backend.repository.ClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AllotedSlotServiceImpl implements AllotedSlotService{

    Logger log = LoggerFactory.getLogger(AllotedSlotServiceImpl.class);

    private final AllotedSlotRespository allotedSlotRespository;

    @Autowired
    public AllotedSlotServiceImpl(AllotedSlotRespository allotedSlotRespository) {
        this.allotedSlotRespository = allotedSlotRespository;
    }

    @Override
    public void saveAll(List<AllotedSlot> slots) {
        log.info("Inside saveAll() of AllotedSlotServiceImpl {}", slots);
        allotedSlotRespository.saveAll(slots);
        log.info("Finished saveAll() of AllotedSlotServiceImpl {}", slots);

    }
}
