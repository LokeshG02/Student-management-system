package com.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public class BulkAttendanceRequestDto {

    // Optional — if not sent, the service defaults it to today.
    private LocalDate attendanceDate;

    @NotEmpty(message = "records can not be empty")
    @Valid
    private List<BulkAttendanceItemDto> records;

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public List<BulkAttendanceItemDto> getRecords() {
        return records;
    }

    public void setRecords(List<BulkAttendanceItemDto> records) {
        this.records = records;
    }
}
