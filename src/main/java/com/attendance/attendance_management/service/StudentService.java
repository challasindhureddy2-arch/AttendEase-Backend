package com.attendance.attendance_management.service;

import com.attendance.attendance_management.entity.Student;
import com.attendance.attendance_management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Create student
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    // Read all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Read student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Update student
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existingStudent.setRollNumber(updatedStudent.getRollNumber());
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setDepartment(updatedStudent.getDepartment());
        existingStudent.setYear(updatedStudent.getYear());

        return studentRepository.save(existingStudent);
    }

    // Delete student
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}