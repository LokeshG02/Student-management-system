package com.example.repository;

import com.example.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // "StudentId" here walks the relationship: student -> id.
    // This is what lets us check/find a record for one student on one date
    // without writing any JPQL by hand.
    Optional<Attendance> findByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findByStudentIdOrderByAttendanceDateDesc(Long studentId);

    long countByStudentId(Long studentId);

    long countByStudentIdAndPresentTrue(Long studentId);
}
