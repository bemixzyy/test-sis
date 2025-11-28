package com.t2404.sis.repository;


import com.t2404.sis.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findBySubjectCode(String code);
}
