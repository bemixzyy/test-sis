package com.t2404.sis.controller;

import com.t2404.sis.entity.*;
import com.t2404.sis.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final StudentScoreService scoreService;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        if (!model.containsAttribute("studentScore")) {
            model.addAttribute("studentScore", new StudentScore());
        }
        return "scores/create";
    }

    @PostMapping("/create")
    public String createSubmit(
            @RequestParam Long studentId,
            @RequestParam Long subjectId,
            @RequestParam BigDecimal score1,   // ĐÃ SỬA: nhận thẳng BigDecimal
            @RequestParam BigDecimal score2,   // ĐÃ SỬA
            RedirectAttributes ra) {

        Student student = studentService.findById(studentId);
        Subject subject = subjectService.findById(subjectId);

        if (student == null || subject == null) {
            ra.addFlashAttribute("errorMessage", "Sinh viên hoặc môn học không tồn tại!");
            return "redirect:/scores/create";
        }

        // Validate điểm 0.0 → 10.0
        if (score1 == null || score2 == null
                || score1.compareTo(BigDecimal.ZERO) < 0 || score1.compareTo(BigDecimal.TEN) > 0
                || score2.compareTo(BigDecimal.ZERO) < 0 || score2.compareTo(BigDecimal.TEN) > 0) {
            ra.addFlashAttribute("errorMessage", "Điểm phải từ 0.0 đến 10.0!");
            return "redirect:/scores/create";
        }

        // Kiểm tra trùng
        if (scoreService.findByStudentIdAndSubjectId(studentId, subjectId) != null) {
            ra.addFlashAttribute("errorMessage",
                    "Điểm môn " + subject.getSubjectName() + " của " + student.getFullName() + " đã tồn tại!");
            return "redirect:/scores/create";
        }

        // Lưu điểm
        StudentScore score = new StudentScore();
        score.setStudent(student);
        score.setSubject(subject);
        score.setScore1(score1.setScale(2, RoundingMode.HALF_UP));
        score.setScore2(score2.setScale(2, RoundingMode.HALF_UP));

        scoreService.save(score);

        // Thông báo đẹp
        ra.addFlashAttribute("successMessage",
                student.getFullName() + " – " + subject.getSubjectName() +
                        " → " + score.getAverageScore() + " (" + score.getGradeLetter() + ")");

        return "redirect:/students/list";
    }
}