package com.example.service;

import com.example.dto.CreateStudentResponseDto;
import com.example.dto.CreatedStudentRequestDto;
import com.example.dto.UpdateStudentRequestDto;
import com.example.dto.UpdateStudentResponseDto;
import com.example.entity.Student;
import com.example.exception.DuplicateResourceException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreatedStudentRequestDto studentReq){

        Student createStudent = mapToEntity(studentReq);
        if(checkDublicateEmail(createStudent)){
            throw new DuplicateResourceException("Student with email "+ createStudent.getEmail() + " already exits.");
        }

        studentRepository.save(createStudent);

        CreateStudentResponseDto creatededStudent = mapToDto(createStudent);
        return creatededStudent;
    }

    public CreateStudentResponseDto getStudent(Long id){
        Student studentDet = studentRepository
                .findByIdAndSoftDeleteIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id "+ id + " not found"));

        return mapToDto(studentDet);
    }

    public List<CreateStudentResponseDto> getAllStudent(){
        List<Student> allStudent = studentRepository.findBySoftDeleteIsFalse();

        return allStudent.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Same as getAllStudent(), but narrowed to one class — this is what lets
    // attendance default to "just this class" instead of the whole school.
    // A blank/null studentClass falls back to every student, same as getAllStudent().
    public List<CreateStudentResponseDto> getAllStudent(String studentClass){
        if (studentClass == null || studentClass.isBlank()) {
            return getAllStudent();
        }

        List<Student> classStudents = studentRepository
                .findBySoftDeleteIsFalseAndStudentClassIgnoreCase(studentClass.trim());

        return classStudents.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Every distinct class currently in use — feeds the class picker on the
    // attendance page so a teacher can choose from real classes.
    public List<String> getAllClasses(){
        return studentRepository.findDistinctStudentClasses();
    }

    public UpdateStudentResponseDto updateStudent(Long id  , UpdateStudentRequestDto studentReq){
        Student existingStudent = studentRepository
                .findByIdAndSoftDeleteIsFalse(id)
                .orElseThrow(() ->new  ResourceNotFoundException("Student with id "+ id + " not found"));



        existingStudent.setName(studentReq.getName());
        existingStudent.setRollNo(studentReq.getRollNo());
        existingStudent.setSubject(studentReq.getSubject());
        existingStudent.setStudentClass(studentReq.getStudentClass());
        existingStudent.setEmail(studentReq.getEmail());
        existingStudent.setAge(studentReq.getAge());
        existingStudent.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(existingStudent);

        UpdateStudentResponseDto updateStudentResponseDto = updatedStudent(existingStudent);

        return updateStudentResponseDto;
    }

    public Boolean softDeleteId(Long id){
        Student studentCheck = studentRepository
                                .findByIdAndSoftDeleteIsFalse(id)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException("This id:" +id + " does not exists"));





        studentCheck.setSoftDelete(true);

        studentRepository.save(studentCheck);
        return true;


    }

    public void deleteStudent(Long id){
        Student studentToBeDeleted = studentRepository
                                .findById(id)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException("This id:" +id + " does not exists"));

        studentRepository.delete(studentToBeDeleted);

    }

    public Boolean deleteAllStudent(){
        long studentCheck = studentRepository.count();

        if(studentCheck == 0) {
            return false;
        }

        studentRepository.deleteAll();

        return true;
    }


    private Student mapToEntity(CreatedStudentRequestDto createdStudentRequestDto){

        Student student = new Student();

        student.setName(createdStudentRequestDto.getName());
        student.setAge(createdStudentRequestDto.getAge());
        student.setEmail(createdStudentRequestDto.getEmail());
        student.setRollNo(createdStudentRequestDto.getRollNo());
        student.setSubject(createdStudentRequestDto.getSubject());
        student.setStudentClass(createdStudentRequestDto.getStudentClass());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        student.setSoftDelete(false);

        return student;
    }

    private CreateStudentResponseDto mapToDto(Student student){

        CreateStudentResponseDto responseDto = new CreateStudentResponseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setStudentClass(student.getStudentClass());
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());
        responseDto.setMessage("Student record of "+ student.getName());

        return responseDto;
    }

    private UpdateStudentResponseDto updatedStudent(Student student){
        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setStudentClass(student.getStudentClass());
        responseDto.setUpdatedAt(student.getUpdatedAt());
        responseDto.setMessage(student.getName() +" record is updated");

        return responseDto;
    }

    private Boolean checkDublicateEmail(Student student){

        return studentRepository.existsByEmail(student.getEmail());
    }
}
