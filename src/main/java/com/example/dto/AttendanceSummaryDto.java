package com.example.dto;

public class AttendanceSummaryDto {

    private Long studentId;
    private String studentName;
    private long totalDaysMarked;
    private long presentDays;
    private long absentDays;
    private double attendancePercentage;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public long getTotalDaysMarked() {
        return totalDaysMarked;
    }

    public void setTotalDaysMarked(long totalDaysMarked) {
        this.totalDaysMarked = totalDaysMarked;
    }

    public long getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(long presentDays) {
        this.presentDays = presentDays;
    }

    public long getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(long absentDays) {
        this.absentDays = absentDays;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }
}
