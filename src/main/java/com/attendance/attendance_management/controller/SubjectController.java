package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.entity.Subject;
import com.attendance.attendance_management.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // CREATE - Add a new subject
    @PostMapping
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectService.addSubject(subject);
    }

    // READ - Get all subjects
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    // READ - Get subject by ID
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(
            @PathVariable Long id) {

        return subjectService.getSubjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE - Update subject
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(
            @PathVariable Long id,
            @RequestBody Subject subject) {

        try {
            return ResponseEntity.ok(
                    subjectService.updateSubject(id, subject)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Delete subject
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(
            @PathVariable Long id) {

        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}