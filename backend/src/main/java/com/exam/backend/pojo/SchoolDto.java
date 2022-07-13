package com.exam.backend.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchoolDto {

    private String schoolId;

    private String newPassword;

}