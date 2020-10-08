package com.liutyi.converter.utils;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MyStringUtil {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String DOMAIN = "@student.sumdu.edu.ua";

    public String generatePassword(int length) {
        List<String> charCategories = Arrays.asList(LOWER, UPPER, DIGITS);

        if (length <= 0) return "";

        StringBuilder password = new StringBuilder();
        Random random = new Random(System.nanoTime());

        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }

        return password.toString();
    }

    public String generateEmail(String fullName) {
        List<String> names;
        try {
            names = new LinkedList<>(Arrays.asList(fullName.split(" ")));
            names.removeAll(Collections.singleton(""));
            return names.get(1).toLowerCase() + "." + names.get(0).toLowerCase() + DOMAIN;
        } catch (Exception e) {
            return "err";
        }
    }

    public String makeTransliterationCyrillicToLatin(String word) {
        if (word.isEmpty()) return "";
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (char ch : word.toCharArray()) {
            if (ch == '\uFEFF') continue;
            boolean isUpper = Character.isUpperCase(ch);
            ch = Character.toLowerCase(ch);
            String newSymbol = "";
            boolean b = isUpper || (i > 0 && (word.charAt(i - 1) == 'ь' || word.charAt(i - 1) == '’'));
            switch (ch) {
                case 'а': newSymbol = "a"; break;
                case 'б': newSymbol = "b"; break;
                case 'в': newSymbol = "v"; break;
                case 'г': newSymbol =  (i > 0 && word.charAt(i - 1) == 'з') ? "gh" : "h"; break;
                case 'ґ': newSymbol = "g"; break;
                case 'д': newSymbol = "d"; break;
                case 'е': newSymbol = "e"; break;
                case 'є': newSymbol = b ? "ye" : "ie"; break;
                case 'ж': newSymbol = "zh"; break;
                case 'з': newSymbol = "z"; break;
                case 'и': newSymbol = "y"; break;
                case 'і': newSymbol = "i"; break;
                case 'ї': newSymbol = b ? "yi" : "i"; break;
                case 'й': newSymbol = isUpper ? "y" : "i"; break;
                case 'к': newSymbol = "k"; break;
                case 'л': newSymbol = "l"; break;
                case 'м': newSymbol = "m"; break;
                case 'н': newSymbol = "n"; break;
                case 'о': newSymbol = "o"; break;
                case 'п': newSymbol = "p"; break;
                case 'р': newSymbol = "r"; break;
                case 'с': newSymbol = "s"; break;
                case 'т': newSymbol = "t"; break;
                case 'у': newSymbol = "u"; break;
                case 'ф': newSymbol = "f"; break;
                case 'х': newSymbol = "kh"; break;
                case 'ц': newSymbol = "ts"; break;
                case 'ч': newSymbol = "ch"; break;
                case 'ш': newSymbol = "sh"; break;
                case 'щ': newSymbol = "shch"; break;
                case 'ь':
                case '’':
                    break;
                case 'ю': newSymbol = b ? "yu" : "iu"; break;
                case 'я': newSymbol = b ? "ya" : "ia"; break;
                default: newSymbol = Character.toString(ch);
            }
            StringBuilder finalSymbol = new StringBuilder(newSymbol);
            if (isUpper) {
                finalSymbol.setCharAt(0, Character.toUpperCase(newSymbol.charAt(0)));
            }
            result.append(finalSymbol.toString());
            i++;
        }
        return result.toString();
    }

    public List<Map<String, String>> prepareDataToSave(List<Map<String, String>> data) {
        for (Map<String, String> map : data) {
            map.put("0", makeTransliterationCyrillicToLatin(map.get("0")));
            map.put("1", makeTransliterationCyrillicToLatin(map.get("1")));
            map.put("2", generateEmail(map.get("0") + " " + map.get("1")));
            map.put("3", generatePassword(8));
        }
        return data;
    }

}
