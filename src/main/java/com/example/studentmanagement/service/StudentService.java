package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Student email already exists"
            );
        }

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Student not found with ID: " + id
            ));
    }

    public Student updateStudent(Long id, Student details) {
        Student student = getStudentById(id);

        if (details.getEmail() != null
                && !details.getEmail().equalsIgnoreCase(student.getEmail())
                && studentRepository.existsByEmail(details.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Student email already exists"
            );
        }

        student.setName(details.getName());
        student.setEmail(details.getEmail());
        student.setBranch(details.getBranch());
        student.setYear(details.getYear());

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.delete(getStudentById(id));
    }
}