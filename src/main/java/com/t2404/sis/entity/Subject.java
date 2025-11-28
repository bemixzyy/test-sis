package com.t2404.sis.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "subject_t")
public class Subject {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @Column(nullable = false, length = 20)
    private String subjectCode;

    @Column(nullable = false, length = 100)
    private String subjectName;

    @Column(nullable = false)
    private Integer credit;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<StudentScore> scores = new ArrayList<>();

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public Integer getCredit() { return credit; }
    public void setCredit(Integer credit) { this.credit = credit; }

    public List<StudentScore> getScores() { return scores; }
    public void setScores(List<StudentScore> scores) { this.scores = scores; }
}
