package com.liutyi.converter.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FilesUtil {
    private static final String path = "";

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
        File newFile = new File(multipartFile.getOriginalFilename());
        newFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return newFile;
    }

    public List<Map<String, String>> getDataFromFile(MultipartFile multipartFile) throws IOException {
        FileInputStream currentFile = new FileInputStream(convertMultipartFileToFile(multipartFile));
        Workbook workbook = new XSSFWorkbook(currentFile);
        Sheet sheet = workbook.getSheetAt(0);

        List<Map<String, String>> data = new ArrayList<>();
        Row firstRow = sheet.getRow(0);
        int i = 0;

        for (Row row : sheet) {
            data.add(new HashMap<>());
            for (int j = 0; j < firstRow.getPhysicalNumberOfCells(); j++) {
                String currentColumn = Generator.generateColumnName(
                        firstRow.getCell(j).getRichStringCellValue().getString());
                Cell cell = row.getCell(j);
                if (cell != null) {
                    if (cell.getCellTypeEnum() == CellType.STRING) {
                        data.get(i).put(currentColumn, cell.getRichStringCellValue().getString());
                    } else {
                        data.get(i).put(currentColumn, "err");
                    }
                } else data.get(i).put(currentColumn, " ");
            }
            i++;
        }

        return data;
    }

    public List<Map<String, String>> processData(List<Map<String, String>> data) {
        return null;
    }
}
