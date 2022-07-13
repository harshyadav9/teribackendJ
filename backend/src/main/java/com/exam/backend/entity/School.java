package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Schools")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class School implements Serializable {

    @Id
    @Column(name = "schoolsCode")
    private String schoolsCode;

    @Column(name = "password")
    private String password;

}