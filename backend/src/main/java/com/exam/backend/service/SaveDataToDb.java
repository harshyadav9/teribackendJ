package com.exam.backend.service;

import com.exam.backend.pojo.InternationalStudantsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveDataToDb {

    Logger log = LoggerFactory.getLogger(SaveDataToDb.class);

    private final InternationalStudantsService internationalStudantsService;

    @Autowired
    public SaveDataToDb(InternationalStudantsService internationalStudantsService) {
        this.internationalStudantsService = internationalStudantsService;
    }

    public void saveData(List<InternationalStudantsDto> data){
        log.info("Inside saveData() {}", data);
        internationalStudantsService.saveStudentsData(data);
        log.info("Completed saveData() {}", data);
    }

}
