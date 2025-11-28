package com.t2404.sis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;   // ← THIẾU CÁI NÀY NÊN BÁO LỖI!

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_score_t")
public class StudentScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentScoreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(precision = 5, scale = 2)
    private BigDecimal score1;

    @Column(precision = 5, scale = 2)
    private BigDecimal score2;

    // ==================== ĐIỂM TRUNG BÌNH (làm tròn 2 chữ số) ====================
    @Transient
    public BigDecimal getAverageScore() {
        if (score1 == null || score2 == null) {
            return BigDecimal.ZERO;
        }
        return score1.multiply(BigDecimal.valueOf(0.3))
                .add(score2.multiply(BigDecimal.valueOf(0.7)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    // ==================== XẾP LOẠI CHỮ A/B/D/F
    @Transient
    public String getGradeLetter() {
        double avg = getAverageScore().doubleValue();
        if (avg >= 8.0) return "A";
        if (avg >= 6.0) return "B";
        if (avg >= 4.0) return "D";
        return "F";
    }

    // ==================== MÀU BADGE CHO BOOTSTRAP ====================
    @Transient
    public String getBadgeClass() {
        return switch (getGradeLetter()) {
            case "A" -> "bg-success";
            case "B" -> "bg-info text-dark";
            case "D" -> "bg-warning text-dark";
            default  -> "bg-danger";  // ← ĐÃ BỎ DẤU ; THỪA Ở ĐÂY
        };
    }

    // Các getter/setter Lombok đã tự sinh, nhưng nếu IDE kêu thì để lại cho chắc
    public Long getStudentScoreId() { return studentScoreId; }
    public void setStudentScoreId(Long studentScoreId) { this.studentScoreId = studentScoreId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public BigDecimal getScore1() { return score1; }
    public void setScore1(BigDecimal score1) { this.score1 = score1; }

    public BigDecimal getScore2() { return score2; }
    public void setScore2(BigDecimal score2) { this.score2 = score2; }
}