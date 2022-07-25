package com.exam.backend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class IndividualStudentSlotDataDto {

    private int slotID;

    private String mode;

    private String examTheme;

    private String dateofExam;

    private String slotdatetime;

    private String seatAvailable;

    private String totalSeat;

    private String rollNo;

}