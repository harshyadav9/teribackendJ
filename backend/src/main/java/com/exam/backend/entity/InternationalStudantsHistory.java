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
@Table(name = "InternationalStudantsHistory")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class InternationalStudantsHistory implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "SchoolID")
    private String schoolID;

    @Column(name = "Password")
    private String password;

    @Column(name = "Name")
    private String name;

    @Column(name = "DOB")
    private String dob;

    @Column(name = "Section")
    private String section;

    @Column(name = "ExamTheme")
    private String examTheme;

    @Column(name = "DemoExam")
    private String demoExam;

    @Column(name = "Class")
    private String className;

    @Column(name = "StudentSeqNo")
    private String studentSeqNo;

}