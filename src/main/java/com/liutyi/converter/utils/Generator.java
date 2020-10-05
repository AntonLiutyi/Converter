package com.liutyi.converter.utils;

import com.ibm.icu.text.Transliterator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Generator {
    public static String generatePassword(int length) {
        List<String> charCategories = Arrays.asList(
                "abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789");

        if (length <= 0) return "";

        String password = "";
        Random random = new Random(System.nanoTime());

        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password += (charCategory.charAt(position));
        }

        return password;
    }

    public static String generateEmail(String name) {
        String email = name.toLowerCase();
        email = email.replace(' ', '.');
        email += "@student.sumdu.edu.ua";
        return email;
    }

    public static String makeTransliteration(String name) {
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        String result = toLatinTrans.transliterate(name);
        return result;
    }

    public static String generateColumnName(String name) {
        String result = name.toLowerCase();
        result = result.replace(' ', '_');
        return result;
    }
}
