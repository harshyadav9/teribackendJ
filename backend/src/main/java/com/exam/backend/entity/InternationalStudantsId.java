package com.exam.backend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@ToString
@Embeddable
@Getter
@Setter
public class InternationalStudantsId implements Serializable {

    public InternationalStudantsId() {
    }

    @Column(name = "DOB")
    private String dob;

    @Column(name = "Name")
    private String name;

    @Column(name = "SchoolID")
    private String schoolId;

}
