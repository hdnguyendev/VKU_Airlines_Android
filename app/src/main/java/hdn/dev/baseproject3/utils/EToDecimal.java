package hdn.dev.baseproject3.utils;

import java.text.DecimalFormat;

public class EToDecimal {
    public static String convert(String input) {
        double num = Double.parseDouble(input);
        int exponent = (int) Math.floor(Math.log10(num));
        double result = num * Math.pow(10, -exponent);
        DecimalFormat df = new DecimalFormat("#.########");
        String formattedResult = df.format(result);
        return formattedResult;
    }
}
