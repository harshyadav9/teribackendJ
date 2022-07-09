/*
package com.exam.backend.pojo;

import lombok.ToString;

import java.sql.Date;

@ToString
public class SlotDto {

    private final int slotId;

    private final String mode;

    private final String examTheme;

    private final Date dateOfExam;

    private final String slotDateTime;

    private final int seatAvailable;

    private final int totalSeat;

    public SlotDto(Builder builder) {
        this.slotId = builder.slotId;
        this.slotDateTime = builder.slotDateTime;
        this.dateOfExam = builder.dateOfExam;
        this.mode = builder.mode;
        this.examTheme = builder.examTheme;
        this.totalSeat = builder.totalSeat;
        this.seatAvailable = builder.seatAvailable;

    }

    public int getSlotId() {
        return slotId;
    }

    public String getMode() {
        return mode;
    }

    public String getExamTheme() {
        return examTheme;
    }

    public Date getDateOfExam() {
        return dateOfExam;
    }

    public String getSlotDateTime() {
        return slotDateTime;
    }

    public int getSeatAvailable() {
        return seatAvailable;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public static class Builder {

        private int slotId;

        private String mode;

        private String examTheme;

        private Date dateOfExam;

        private String slotDateTime;

        private int seatAvailable;

        private int totalSeat;

        public Builder slotId(int slotId) {
            this.slotId = slotId;
            return this;
        }

        public Builder mode(String mode) {
            this.mode = mode;
            return this;
        }

        public Builder examTheme(String examTheme) {
            this.examTheme = examTheme;
            return this;
        }

        public Builder dateOfExam(Date dateOfExam) {
            this.dateOfExam = dateOfExam;
            return this;
        }

        public Builder slotDateTime(String slotDateTime) {
            this.slotDateTime = slotDateTime;
            return this;
        }

        public Builder seatAvailable(int seatAvailable) {
            this.seatAvailable = seatAvailable;
            return this;
        }

        public Builder totalSeat(int totalSeat) {
            this.totalSeat = totalSeat;
            return this;
        }

        public SlotDto build() {
            SlotDto slotDto =  new SlotDto(this);
            return slotDto;
        }

    }
}
*/
