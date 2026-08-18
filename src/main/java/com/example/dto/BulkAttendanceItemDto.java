package com.example.dto;

import jakarta.validation.constraints.NotNull;

public class BulkAttendanceItemDto {

    @NotNull(message = "studentId is required")
    private Long studentId;

    private boolean present;

    private String remark;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
