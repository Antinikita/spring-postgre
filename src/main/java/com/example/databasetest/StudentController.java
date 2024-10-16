package com.example.databasetest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        return ResponseEntity.ok().body(student).getBody();
    }
    @PostMapping("/SetStudents")
    public Student setStudent(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }
    @PutMapping("/UpdateStudents/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") long id, @Valid @RequestBody Student student) throws ResourceNotFoundException {
        Student student1= studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setEmailId(student.getEmailId());
        final Student updatedStudent = studentRepository.save(student1);
        return ResponseEntity.ok().body(updatedStudent);
    }
    @DeleteMapping("/deleteStudents/{id}")
    public Map<String,Boolean> deleteStudent(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        studentRepository.delete(student);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
