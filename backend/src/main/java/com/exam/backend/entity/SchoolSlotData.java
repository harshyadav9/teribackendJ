package com.exam.backend.entity;

import java.sql.Date;

public interface SchoolSlotData {

    Integer getSlotId();

    String getMode();

    String getExamTheme();

    Date getDateOfExam();

    String getSlotDatetime();

    Integer getSeatAvailable();

    Integer getTotalSeat();

    Integer getStudentCount();

    String getSchoolId();


}