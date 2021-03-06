package com.exam.backend.service;

import com.exam.backend.entity.StudentClass;
import com.exam.backend.repository.ClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClassServiceImpl implements ClassService{

    Logger log = LoggerFactory.getLogger(ClassServiceImpl.class);

    private final ClassRepository classRepository;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public StudentClass getClassLevel(String className) {
        log.info("Inside getClassLevel() {}", className);
        Optional<StudentClass> studentClass = classRepository.findById(className);
        if (studentClass.isPresent()){
            log.info("studentClass in getClassLevel() {}", studentClass);
            return studentClass.get();

        }
        else {
            return null;
        }

    }
}
