package com.t2404.sis.repository;


import com.t2404.sis.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Override
    @EntityGraph(attributePaths = {"scores", "scores.subject"})
    List<Student> findAll();
}
