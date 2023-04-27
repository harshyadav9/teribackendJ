package com.exam.backend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InternationalStudantsDto {

    private String language;

    private String dob;

    private String studentSeqNo;

    private String name;

    private String className;

    private String section;

    private String examTheme;

    private String demoExam;

    private String schoolId;

    private Integer studentId;

    private String DemoSlotDateTime;

    private String ExamSlotDateTime;
}