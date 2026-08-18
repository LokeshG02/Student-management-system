package com.example.repository;

import com.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public interface StudentRepository extends JpaRepository<Student , Long> {

    Optional<Student>  findByIdAndSoftDeleteIsFalse(Long id);

    List<Student> findBySoftDeleteIsFalse();

    // Used when taking attendance for one class only — filters the roster
    // down to students in that class before the teacher marks anyone.
    List<Student> findBySoftDeleteIsFalseAndStudentClassIgnoreCase(String studentClass);

    Boolean existsByEmail(String emailId);

    // Powers the class dropdown on the attendance page — every distinct
    // class currently in use, so a teacher can pick one instead of typing it.
    @Query("SELECT DISTINCT s.studentClass FROM Student s WHERE s.softDelete = false ORDER BY s.studentClass")
    List<String> findDistinctStudentClasses();

}
