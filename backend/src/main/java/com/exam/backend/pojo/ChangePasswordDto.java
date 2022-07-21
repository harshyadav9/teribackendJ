package com.exam.backend.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangePasswordDto {

    private String schoolId;

    private String indvRollNumber;

    private String newPassword;

    private String isIndividualStudent;

    private boolean isIndividualStudent1;

}