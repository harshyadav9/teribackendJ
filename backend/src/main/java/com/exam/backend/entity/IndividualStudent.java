package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "IndividualStudent")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class IndividualStudent implements Serializable {

    @Id
    @Column(name = "RollNo")
    private String rollNo;

    @Column(name = "Password")
    private String password;

    @Column(name = "Name")
    private String name;

    @Column(name = "DOB")
    private String dob;

    @Column(name = "Mobile")
    private String mobile;

    @Column(name = "Email")
    private String email;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Country")
    private String country;

    @Column(name = "Add1")
    private String add1;

    @Column(name = "Add2")
    private String add2;

    @Column(name = "State")
    private String state;

    @Column(name = "City")
    private String city;

    @Column(name = "Pin")
    private String pin;

    @Column(name = "School")
    private String school;

    @Column(name = "Class")
    private String standard;

    @Column(name = "Section")
    private String section;

    @Column(name = "PGEmail")
    private String pgEmail;

    @Column(name = "pgMobile")
    private String pgMobile;

    @Column(name = "ExamTheme")
    private String examTheme;

    @Column(name = "DemoExam")
    private String demoExam;

    @Column(name = "ExamLevel")
    private String examLevel;

    @Column(name = "ExamSlotDateTime")
    private String examSlotDateTime;

    @Column(name = "DemoSlotDateTime")
    private String demoSlotDateTime;

    @Column(name = "Createdby")
    private String createdby;

    @Column(name = "Modifiedby")
    private String modifiedby;

    @Column(name = "PaymentStatus")
    private boolean paymentStatus;

    @Column(name = "PGName")
    private String pgName;
}