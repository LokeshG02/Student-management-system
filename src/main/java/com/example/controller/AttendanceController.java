package com.example.controller;

import com.example.dto.*;
import com.example.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private AttendanceService attendanceService;

    AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // Mark (or correct, if already marked) one student's attendance for a date.
    @PostMapping
    public ResponseEntity<AttendanceResponseDto> markAttendance(@Valid @RequestBody MarkAttendanceRequestDto req) {
        AttendanceResponseDto marked = attendanceService.markAttendance(req);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(marked);
    }

    // Mark a whole class's attendance for one date in a single request.
    @PostMapping("/bulk")
    public ResponseEntity<List<AttendanceResponseDto>> markBulkAttendance(@Valid @RequestBody BulkAttendanceRequestDto req) {
        List<AttendanceResponseDto> marked = attendanceService.markBulkAttendance(req);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(marked);
    }

    // Whole class's attendance for a given date, e.g. GET /api/attendance/date/2026-08-17
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<AttendanceResponseDto> list = attendanceService.getAttendanceByDate(date);

        return ResponseEntity.ok(list);
    }

    // Full attendance history for one student.
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByStudent(@PathVariable Long studentId) {
        List<AttendanceResponseDto> list = attendanceService.getAttendanceByStudent(studentId);

        return ResponseEntity.ok(list);
    }

    // Present/total percentage for one student.
    @GetMapping("/student/{studentId}/summary")
    public ResponseEntity<AttendanceSummaryDto> getSummary(@PathVariable Long studentId) {
        AttendanceSummaryDto summary = attendanceService.getSummary(studentId);

        return ResponseEntity.ok(summary);
    }

    // Correct a specific attendance record by its own id.
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(
            @PathVariable Long id,
            @RequestBody UpdateAttendanceRequestDto req) {

        AttendanceResponseDto updated = attendanceService.updateAttendance(id, req);

        return ResponseEntity.ok(updated);
    }

    // Remove a record entirely.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Attendance record deleted");
    }
}
