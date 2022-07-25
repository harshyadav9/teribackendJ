package com.exam.backend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class InternationalStudantsHistoryDto {

    private String schoolID;

    private String password;

    private String name;

    private String dob;

    private String section;

    private String examTheme;

    private String demoExam;

}