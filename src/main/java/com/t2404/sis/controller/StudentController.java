package com.t2404.sis.controller;

import com.t2404.sis.entity.Student;
import com.t2404.sis.service.StudentService;
import com.t2404.sis.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final SubjectService subjectService; // (edit, delete...)

    // Khi truy cập /students → chuyển thẳng về danh sách
    @GetMapping
    public String root() {
        return "redirect:/students/list";
    }

    // Trang chính: Danh sách sinh viên + điểm + Grade
    @GetMapping("/list")
    public String list(Model model) {
        List<Student> students = studentService.findAllWithScores();
        model.addAttribute("students", students);
        return "students/list"; // → templates/students/list.html
    }

    // Hiển thị form thêm sinh viên mới
    @GetMapping("/create")
    public String createForm(Model model) {
        // Nếu chưa có student → tạo mới để form bind
        if (!model.containsAttribute("student")) {
            model.addAttribute("student", new Student());
        }
        return "students/create"; // → templates/students/create.html
    }

    // Xử lý submit form thêm sinh viên
    @PostMapping("/create")
    public String createSubmit(
            @ModelAttribute Student student,
            RedirectAttributes redirectAttributes) {

        studentService.save(student);

        redirectAttributes.addFlashAttribute("successMessage",
                "Thêm sinh viên \"" + student.getFullName() + "\" thành công!");

        return "redirect:/students/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        studentService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Xóa sinh viên thành công!");
        return "redirect:/students/list";
    }
}