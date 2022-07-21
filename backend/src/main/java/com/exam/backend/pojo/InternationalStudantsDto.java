package com.exam.backend.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InternationalStudantsDto {

    private String dob;

    private String name;

    private String className;

    private String section;

    private String examTheme;

    private String demoExam;

    private String schoolId;

    private Integer studentId;

    private boolean paymentStatus;
}