package com.exam.backend.service;

import com.exam.backend.pojo.InternationalStudantsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SaveDataToDb {

    Logger log = LoggerFactory.getLogger(SaveDataToDb.class);

    private final InternationalStudantsService internationalStudantsService;

    @Autowired
    public SaveDataToDb(InternationalStudantsService internationalStudantsService) {
        this.internationalStudantsService = internationalStudantsService;
    }

    public String saveData(List<InternationalStudantsDto> data) throws SQLIntegrityConstraintViolationException {
        log.info("Inside saveData() {}", data);
        return internationalStudantsService.saveStudentsData(data);

    }
}
