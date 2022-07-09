package com.exam.backend.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@Getter
@Setter
public class SchoolSlotDataIncoming {

    private Integer slotId;

    private String mode;

    private String examTheme;

    private Date dateOfExam;

    private String slotDatetime;

    private Integer seatAvailable;

    private Integer totalSeat;

    private Integer studentCount;

    private String schoolId;

}