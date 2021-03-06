package com.exam.backend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IndividualStudentDto {

    private String rollNo;

    private String password;

    private String name;

    private String dob;

    private String mobile;

    private String email;

    private String gender;

    private String country;

    private String add1;

    private String state;

    private String city;

    private String pin;

    private String school;

    private String standard;

    private String section;

    private String pgEmail;

    private String pgMobile;

    private String examTheme;

    private String demoExam;

    private String examLevel;

    private String createdBy;

    private String modifiedby;

    private String countryCode;

    private String state_city_cd;

    private String rollNoPrefix;

    private String pgName;

    private boolean paymentStatus;

    private String examSlotDateTime;

    private String demoSlotDateTime;

    private String indigo;

}