package hdn.dev.baseproject3.utils;

import java.text.Normalizer;

public class FormatName {
    public static String convertNameToEnglish(String name) {
        // Replace non-ASCII characters with their ASCII equivalents
        String asciiName = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        // Replace spaces with empty strings
        String convertedName = asciiName.replaceAll("\\s+", "");
        convertedName = convertedName.replaceAll("Đ", "D");
        // Convert to title case
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(convertedName.charAt(0)));
        for (int i = 1; i < convertedName.length(); i++) {
            char c = convertedName.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(' ');
            }
            sb.append(c);
        }
        String titleCaseName = sb.toString();

        return titleCaseName;
    }
}
