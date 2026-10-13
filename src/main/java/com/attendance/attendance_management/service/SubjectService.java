package com.attendance.attendance_management.service;

import com.attendance.attendance_management.entity.Subject;
import com.attendance.attendance_management.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // Create subject
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    // Read all subjects
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // Read subject by ID
    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    // Update subject
    public Subject updateSubject(Long id, Subject updatedSubject) {

        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        existingSubject.setSubjectCode(updatedSubject.getSubjectCode());
        existingSubject.setSubjectName(updatedSubject.getSubjectName());
        existingSubject.setDepartment(updatedSubject.getDepartment());
        existingSubject.setYear(updatedSubject.getYear());
        existingSubject.setSemester(updatedSubject.getSemester());

        return subjectRepository.save(existingSubject);
    }

    // Delete subject
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}