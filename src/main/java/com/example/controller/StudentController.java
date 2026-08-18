package com.example.controller;

import com.example.dto.CreatedStudentRequestDto;
import com.example.dto.CreateStudentResponseDto;
import com.example.dto.UpdateStudentRequestDto;
import com.example.dto.UpdateStudentResponseDto;
import com.example.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {


    private StudentService studentService;

    StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    //create

    @PostMapping

    public ResponseEntity<CreateStudentResponseDto> createStudent(@Valid @RequestBody CreatedStudentRequestDto createdStudentRequestDto){
        CreateStudentResponseDto createStudent = studentService.createStudent(createdStudentRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createStudent);
    }

    //read
    @GetMapping("/get/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable Long id){

        CreateStudentResponseDto studentDet = studentService.getStudent(id);

        return ResponseEntity.ok(studentDet);
    }

    // Optional ?studentClass=10A narrows the roster to one class — used by the
    // attendance page to load "just this class" instead of every student.
    // Omit the param (or leave it blank) to get everyone, same as before.
    @GetMapping

    public ResponseEntity<List<CreateStudentResponseDto>> getStudent(
            @RequestParam(required = false) String studentClass){

        List<CreateStudentResponseDto> studentList = studentService.getAllStudent(studentClass);

        return ResponseEntity.ok(studentList);
    }

    // Every distinct class currently in use, e.g. ["9A", "9B", "10A"].
    // Powers the class dropdown on the attendance page.
    @GetMapping("/classes")

    public ResponseEntity<List<String>> getAllClasses(){

        List<String> classes = studentService.getAllClasses();

        return ResponseEntity.ok(classes);
    }
    //update

    @PutMapping("/{id}")

    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@PathVariable Long id , @RequestBody UpdateStudentRequestDto studentReq){

        UpdateStudentResponseDto studentUpdated = studentService.updateStudent(id , studentReq);

        return ResponseEntity.ok(studentUpdated);
    }

    //delete

    @PatchMapping("/soft-delete")

    public ResponseEntity<String> softDelete(@RequestParam Long id){
        studentService.softDeleteId(id);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Record Deleted");

    }

    @DeleteMapping("/delete/{id}")

    public ResponseEntity<String> deleteStudent(@PathVariable Long id){

        studentService.deleteStudent(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Record Deleted");
    }

    @DeleteMapping

    public ResponseEntity<String> deleteStudent(){

        Boolean isDeleted = studentService.deleteAllStudent();

        if(isDeleted){
            return ResponseEntity.ok("Record Deleted");
        }

        return ResponseEntity.notFound().build();
    }

}
