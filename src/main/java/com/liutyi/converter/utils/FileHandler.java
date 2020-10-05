package com.liutyi.converter.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileHandler {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public List<Map<String, String>> processFile() throws IOException, InvalidFormatException {
        File currentFile = new File(this.file.getOriginalFilename());
        Workbook workbook = new XSSFWorkbook(currentFile);
        Sheet sheet = workbook.getSheetAt(0);

        List<Map<String, String>> data = new ArrayList<>();
        Row firstRow = sheet.getRow(0);
        sheet.removeRow(firstRow);
        int i = 0;

        for (Row row : sheet) {
            data.add(new HashMap<>());
            int j = 0;
            for (Cell cell : row) {
                String currentColumn = Generator.generateColumnName(
                        firstRow.getCell(j).getRichStringCellValue().getString());
                if (cell.getCellTypeEnum() == CellType.STRING) {
                    data.get(i).put(currentColumn, cell.getRichStringCellValue().getString());
                } else {
                    data.get(i).put(currentColumn, "null");
                }
                j++;
            }
            i++;
        }

        return data;
    }

}
