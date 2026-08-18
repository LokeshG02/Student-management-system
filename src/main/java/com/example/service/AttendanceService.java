package com.example.service;

import com.example.dto.*;
import com.example.entity.Attendance;
import com.example.entity.Student;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AttendanceRepository;
import com.example.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class AttendanceService {

    private AttendanceRepository attendanceRepository;
    private StudentRepository studentRepository;

    AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    // Mark (or correct) one student's attendance for a date.
    // If a record already exists for that student+date it gets updated instead
    // of throwing a duplicate error — a teacher re-tapping "present" for the
    // same student/day should just fix the existing row, not fail.
    public AttendanceResponseDto markAttendance(MarkAttendanceRequestDto req) {

        Student student = studentRepository
                .findByIdAndSoftDeleteIsFalse(req.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + req.getStudentId() + " not found"));

        LocalDate date = req.getAttendanceDate() != null ? req.getAttendanceDate() : LocalDate.now();

        Attendance saved = upsertAttendance(student, date, req.isPresent(), req.getRemark());

        return mapToDto(saved);
    }

    // Mark a whole class's attendance for one date in a single call.
    public List<AttendanceResponseDto> markBulkAttendance(BulkAttendanceRequestDto req) {

        LocalDate date = req.getAttendanceDate() != null ? req.getAttendanceDate() : LocalDate.now();

        return req.getRecords().stream()
                .map(item -> {
                    Student student = studentRepository
                            .findByIdAndSoftDeleteIsFalse(item.getStudentId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Student with id " + item.getStudentId() + " not found"));

                    Attendance saved = upsertAttendance(student, date, item.isPresent(), item.getRemark());
                    return mapToDto(saved);
                })
                .toList();
    }

    // Whole class's attendance for a given date, ordered by roll number.
    public List<AttendanceResponseDto> getAttendanceByDate(LocalDate date) {
        List<Attendance> records = attendanceRepository.findByAttendanceDate(date);

        return records.stream()
                .sorted(Comparator.comparingInt(a -> a.getStudent().getRollNo()))
                .map(this::mapToDto)
                .toList();
    }

    // Full attendance history for one student, most recent first.
    public List<AttendanceResponseDto> getAttendanceByStudent(Long studentId) {
        studentRepository
                .findByIdAndSoftDeleteIsFalse(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + studentId + " not found"));

        List<Attendance> records = attendanceRepository.findByStudentIdOrderByAttendanceDateDesc(studentId);

        return records.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Correct a specific attendance record by its own id (e.g. fix a mistake
    // noticed on a later date, not just same-day).
    public AttendanceResponseDto updateAttendance(Long id, UpdateAttendanceRequestDto req) {
        Attendance existing = attendanceRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance record with id " + id + " not found"));

        existing.setPresent(req.isPresent());
        existing.setRemark(req.getRemark());
        existing.setUpdatedAt(LocalDateTime.now());

        attendanceRepository.save(existing);

        return mapToDto(existing);
    }

    public void deleteAttendance(Long id) {
        Attendance existing = attendanceRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance record with id " + id + " not found"));

        attendanceRepository.delete(existing);
    }

    // Present-days / total-days percentage for one student.
    public AttendanceSummaryDto getSummary(Long studentId) {
        Student student = studentRepository
                .findByIdAndSoftDeleteIsFalse(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + studentId + " not found"));

        long total = attendanceRepository.countByStudentId(studentId);
        long presentDays = attendanceRepository.countByStudentIdAndPresentTrue(studentId);
        long absentDays = total - presentDays;
        double percentage = total == 0 ? 0.0 : (presentDays * 100.0) / total;

        AttendanceSummaryDto summary = new AttendanceSummaryDto();
        summary.setStudentId(student.getId());
        summary.setStudentName(student.getName());
        summary.setTotalDaysMarked(total);
        summary.setPresentDays(presentDays);
        summary.setAbsentDays(absentDays);
        summary.setAttendancePercentage(Math.round(percentage * 100.0) / 100.0);

        return summary;
    }

    // Shared create-or-update logic used by both the single and bulk mark endpoints.
    private Attendance upsertAttendance(Student student, LocalDate date, boolean present, String remark) {
        Attendance attendance = attendanceRepository
                .findByStudentIdAndAttendanceDate(student.getId(), date)
                .orElseGet(Attendance::new);

        boolean isNew = attendance.getId() == null;

        attendance.setStudent(student);
        attendance.setAttendanceDate(date);
        attendance.setPresent(present);
        attendance.setRemark(remark);
        attendance.setUpdatedAt(LocalDateTime.now());
        if (isNew) {
            attendance.setCreatedAt(LocalDateTime.now());
        }

        return attendanceRepository.save(attendance);
    }

    private AttendanceResponseDto mapToDto(Attendance attendance) {
        AttendanceResponseDto dto = new AttendanceResponseDto();

        dto.setId(attendance.getId());
        dto.setStudentId(attendance.getStudent().getId());
        dto.setStudentName(attendance.getStudent().getName());
        dto.setRollNo(attendance.getStudent().getRollNo());
        dto.setAttendanceDate(attendance.getAttendanceDate());
        dto.setPresent(attendance.isPresent());
        dto.setRemark(attendance.getRemark());
        dto.setCreatedAt(attendance.getCreatedAt());
        dto.setUpdatedAt(attendance.getUpdatedAt());
        dto.setMessage("Attendance for " + attendance.getStudent().getName()
                + " on " + attendance.getAttendanceDate()
                + " marked " + (attendance.isPresent() ? "present" : "absent"));

        return dto;
    }
}
