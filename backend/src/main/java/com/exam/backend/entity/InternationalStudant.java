package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "InternationalStudants")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class InternationalStudant implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private Integer studentId;

    @Column(name = "DOB")
    private String dob;

    @Column(name = "Name")
    private String name;

    @Column(name = "SchoolID")
    private String schoolId;

    @Column(name = "Class")
    private String className;

    @Column(name = "Section")
    private String section;

    @Column(name = "ExamTheme")
    private String examTheme;

    @Column(name = "DemoExam")
    private String demoExam;

    @Column(name = "ExamLevel")
    private String examLevel;

    @Column(name = "Password")
    private String password;

    @Column(name = "paymentStatus")
    private boolean paymentStatus;

    @Column(name = "DemoSlotDateTime")
    private String demoSlotDatetime;

    @Column(name = "ExamSlotDateTime")
    private String examSlotDatetime;

    @Column(name = "Rollno")
    private String rollNo;

    @Column(name = "updateTimestamp")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimeStamp;

    @Column(name = "Createdby")
    private String createdby;

    @Column(name = "Modby")
    private String modby;

}