package com.t2404.sis.service;

import com.t2404.sis.entity.Subject;
import com.t2404.sis.repository.SubjectRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository; // ĐÚNG TÊN + ĐÚNG KIỂU

    // ĐÚNG: Trả về List<Subject>, không phải Student!!!
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findById(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    // BẮT BUỘC có @Transactional để count() và save() hoạt động đúng
    @PostConstruct
    @Transactional
    public void initDefaults() {
        if (subjectRepository.count() == 0) {
            subjectRepository.saveAll(List.of(
                    createSubject("JAVA", "Java Programming", 4),
                    createSubject("PHP", "PHP Programming", 3),
                    createSubject("WDA", "Web Development and Applications", 3)
            ));
        }
    }

    // Helper để code sạch đẹp
    private Subject createSubject(String code, String name, int credit) {
        Subject s = new Subject();
        s.setSubjectCode(code);
        s.setSubjectName(name);
        s.setCredit(credit);
        return s;
    }
}