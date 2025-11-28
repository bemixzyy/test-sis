package com.t2404.sis.service;

import com.t2404.sis.entity.Student;
import com.t2404.sis.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // Lấy tất cả sinh viên (không kèm scores)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    // Lấy tất cả sinh viên + scores + subject (dùng để hiển thị danh sách)
    @Transactional(readOnly = true)
    public List<Student> findAllWithScores() {
        List<Student> students = studentRepository.findAll();
        students.forEach(s -> Hibernate.initialize(s.getScores()));
        return students;
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}