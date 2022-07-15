package com.exam.backend.service;

import com.exam.backend.entity.StudentClass;

import java.util.Optional;

public interface ClassService {

    StudentClass getClassLevel(String className);
}
