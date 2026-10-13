package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.entity.Attendance;
import com.attendance.attendance_management.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // Mark attendance
    @PostMapping
    public Attendance markAttendance(@RequestBody Attendance attendance) {
        return attendanceService.markAttendance(attendance);
    }

    // Get all attendance records
    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // Get attendance by ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(
            @PathVariable Long id) {

        return attendanceService.getAttendanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get attendance percentage
    @GetMapping("/percentage/{studentId}/{subjectId}")
    public Map<String, Object> getAttendancePercentage(
            @PathVariable Long studentId,
            @PathVariable Long subjectId) {

        double percentage =
                attendanceService.getAttendancePercentage(
                        studentId, subjectId);

        return Map.of(
                "studentId", studentId,
                "subjectId", subjectId,
                "attendancePercentage", percentage
        );
    }

    // Get detailed attendance report
    @GetMapping("/report/{studentId}/{subjectId}")
    public Map<String, Object> getAttendanceReport(
            @PathVariable Long studentId,
            @PathVariable Long subjectId) {

        return attendanceService.getAttendanceReport(
                studentId, subjectId);
    }

    // Update attendance
    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(
            @PathVariable Long id,
            @RequestBody Attendance attendance) {

        try {
            return ResponseEntity.ok(
                    attendanceService.updateAttendance(
                            id, attendance)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete attendance
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long id) {

        try {
            attendanceService.deleteAttendance(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}