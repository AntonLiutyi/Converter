package com.liutyi.converter.controllers;

import com.liutyi.converter.models.Student;
import com.liutyi.converter.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private StudentRepository studentsRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/info")
    public String info(Model model) {
        Iterable<Student> students = studentsRepository.findAll();
        model.addAttribute("students", students);
        return "info";
    }
}
