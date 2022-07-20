package com.exam.backend.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class InternationalStudantsHistoryDto {

    private String schoolID;

    private String password;

    private String name;

    private String dob;

    private String section;

    private String examTheme;

    private String demoExam;

}