package com.tp.studentsapi.repository;

import com.tp.studentsapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}
