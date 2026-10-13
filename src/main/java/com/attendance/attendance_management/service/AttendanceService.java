package com.attendance.attendance_management.service;

import com.attendance.attendance_management.entity.Attendance;
import com.attendance.attendance_management.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {

        Attendance existingAttendance =
                attendanceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Attendance record not found"));

        existingAttendance.setStudentId(updatedAttendance.getStudentId());
        existingAttendance.setSubjectId(updatedAttendance.getSubjectId());
        existingAttendance.setAttendanceDate(updatedAttendance.getAttendanceDate());
        existingAttendance.setStatus(updatedAttendance.getStatus());

        return attendanceRepository.save(existingAttendance);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    // Calculate attendance percentage
    public double getAttendancePercentage(Long studentId, Long subjectId) {

        List<Attendance> records =
                attendanceRepository.findByStudentIdAndSubjectId(
                        studentId, subjectId);

        if (records.isEmpty()) {
            return 0.0;
        }

        long presentCount = records.stream()
                .filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus()))
                .count();

        return (presentCount * 100.0) / records.size();
    }

    // Generate attendance report
    public Map<String, Object> getAttendanceReport(
            Long studentId, Long subjectId) {

        List<Attendance> records =
                attendanceRepository.findByStudentIdAndSubjectId(
                        studentId, subjectId);

        long totalClasses = records.size();

        long presentClasses = records.stream()
                .filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus()))
                .count();

        long absentClasses = records.stream()
                .filter(a -> "ABSENT".equalsIgnoreCase(a.getStatus()))
                .count();

        double percentage = totalClasses == 0
                ? 0.0
                : (presentClasses * 100.0) / totalClasses;

        return Map.of(
                "studentId", studentId,
                "subjectId", subjectId,
                "totalClasses", totalClasses,
                "presentClasses", presentClasses,
                "absentClasses", absentClasses,
                "attendancePercentage", percentage
        );
    }
}