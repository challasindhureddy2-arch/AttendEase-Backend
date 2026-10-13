package com.attendance.attendance_management.repository;

import com.attendance.attendance_management.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}