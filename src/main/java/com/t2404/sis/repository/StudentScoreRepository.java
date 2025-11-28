package com.t2404.sis.repository;

import com.t2404.sis.entity.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {
    StudentScore findByStudentStudentIdAndSubjectSubjectId(Long studentId, Long subjectId);
}
