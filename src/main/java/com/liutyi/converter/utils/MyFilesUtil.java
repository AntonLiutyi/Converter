package com.liutyi.converter.utils;

import com.liutyi.converter.models.Student;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Component
public class MyFilesUtil {
    private static final String SPR = File.separator;
    private static final String LOCAL_FILE_PATH = System.getProperty("user.dir")+ SPR +"src"+ SPR +"main"+ SPR +"resources"+ SPR +"storage"+ SPR;
    private static final String FIRST_ROW = "First Name [Required],Last Name [Required],Email Address [Required],Password [Required],Password Hash Function [UPLOAD ONLY],Org Unit Path [Required],New Primary Email [UPLOAD ONLY],Recovery Email,Home Secondary Email,Work Secondary Email,Recovery Phone [MUST BE IN THE E.164 FORMAT],Work Phone,Home Phone,Mobile Phone,Work Address,Home Address,Employee ID,Employee Type,Employee Title,Manager Email,Department,Cost Center,Building ID,Floor Name,Floor Section,Change Password at Next Sign-In,New Status [UPLOAD ONLY]\n";

    private static final String ORG_UNIT_PATH = "/";
    private static final String RECOVERY_EMAIL = "kick.maks545@icloud.com";
    private static final String HOME_PHONE = "+38095111844";
    private static final String CHANGE_PASSWORD = "0";

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
        File newFile = new File(System.getProperty("java.io.tmpdir")+ SPR +multipartFile.getOriginalFilename());
        newFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return newFile;
    }

    public List<Map<String, String>> getDataFromFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultipartFileToFile(multipartFile);
        BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

        List<Map<String, String>> data = new ArrayList<>();
        String row;
        int i = 0;
        while ((row = reader.readLine()) != null) {
            List<String> cells = new LinkedList<>(Arrays.asList(row.split(",")));
            cells.removeAll(Arrays.asList("", " ", ","));
            if (!cells.isEmpty()) {
                int j = 0;
                data.add(new HashMap<>());
                for (String cell : cells) {
                    data.get(i).put(String.valueOf(j), cell);
                    j++;
                }
            }
            i++;
        }

        reader.close();

        return data;
    }

    public File getCsvFile(Iterable<Student> data) throws IOException {
        File myFile = new File(LOCAL_FILE_PATH + "OutputInfo.csv");
        if(!myFile.createNewFile()) {
            PrintWriter writer = new PrintWriter(myFile);
            writer.print("");
            writer.close();
        }

        List<List<String>> listOfStudents = new ArrayList<>();
        int i = 0;
        for (Student student : data) {
            listOfStudents.add(new ArrayList<>());
            listOfStudents.get(i).add(student.getFirst_name());
            listOfStudents.get(i).add(student.getLast_name());
            listOfStudents.get(i).add(student.getEmail_address());
            listOfStudents.get(i).add(student.getPassword());
            i++;
        }

        try (PrintWriter myWriter = new PrintWriter(myFile)) {
            StringBuilder fileBody = new StringBuilder(FIRST_ROW);
            for (List<String> student : listOfStudents) {
                for (String str : student) {
                    fileBody.append(str).append(",");
                }
                fileBody.append(","+ORG_UNIT_PATH+","+RECOVERY_EMAIL+",,,,"+HOME_PHONE+",,,,,,,,,,,,,,,"+CHANGE_PASSWORD+",\n");
            }
            myWriter.write(fileBody.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myFile;
    }

}
