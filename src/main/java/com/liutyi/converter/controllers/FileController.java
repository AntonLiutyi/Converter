package com.liutyi.converter.controllers;

import com.liutyi.converter.models.Student;
import com.liutyi.converter.repos.StudentRepository;
import com.liutyi.converter.utils.MyFilesUtil;
import com.liutyi.converter.utils.MyStringUtil;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MyFilesUtil myFilesUtil;

    @Autowired
    private MyStringUtil myStringUtil;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        List<Map<String, String>> fileInfo = myStringUtil.prepareDataToSave(myFilesUtil.getDataFromFile(file));
        for (Map<String, String> map : fileInfo) {
            Student student = new Student(map);
            studentRepository.save(student);
        }
        return "redirect:/info";
    }

    @GetMapping("/download")
    public String downloadCsvFile(HttpServletResponse response, Model model) throws IOException {
        File outputFile = myFilesUtil.getCsvFile(studentRepository.findAll());
        InputStream is = new FileInputStream(outputFile);
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + outputFile.getName() + "\"");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
        is.close();
        return "redirect:/info";
    }

}
