package com.liutyi.converter.controllers;

import com.liutyi.converter.models.Student;
import com.liutyi.converter.repos.StudentRepository;
import com.liutyi.converter.utils.FileHandler;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FileHandler fileHandler;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException, InvalidFormatException {
        fileHandler.setFile(file);
        List<Map<String, String>> fileInfo = fileHandler.processFile();
        for (Map<String, String> map : fileInfo) {
            Student student = new Student(map);
            studentRepository.save(student);
        }
        return "redirect:/info";
    }

}
