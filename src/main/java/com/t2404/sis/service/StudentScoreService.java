package com.t2404.sis.service;

import com.t2404.sis.entity.StudentScore;
import com.t2404.sis.repository.StudentScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentScoreService {

    private final StudentScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    public List<StudentScore> findAll() {
        return scoreRepository.findAll();
    }

    public StudentScore findById(Long id) {
        return scoreRepository.findById(id).orElse(null);
    }

    public StudentScore save(StudentScore score) {
        return scoreRepository.save(score);
    }

    public void deleteById(Long id) {
        scoreRepository.deleteById(id);
    }

    // Tìm điểm theo sinh viên + môn (tránh trùng)
    public StudentScore findByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        return scoreRepository.findByStudentStudentIdAndSubjectSubjectId(studentId, subjectId);
    }
}