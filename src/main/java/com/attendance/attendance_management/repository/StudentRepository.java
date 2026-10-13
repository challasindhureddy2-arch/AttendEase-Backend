package com.attendance.attendance_management.repository;

import com.attendance.attendance_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}