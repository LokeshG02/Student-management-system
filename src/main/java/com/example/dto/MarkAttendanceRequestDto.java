package com.example.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MarkAttendanceRequestDto {

    @NotNull(message = "studentId is required")
    private Long studentId;

    // Optional — if not sent, the service defaults it to today.
    private LocalDate attendanceDate;

    private boolean present;

    private String remark;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
