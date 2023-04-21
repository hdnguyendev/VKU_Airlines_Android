package hdn.dev.baseproject3.utils;

public class CovertCityName {
    public static String convertCityName(String cityName) {
        if (cityName.toUpperCase().equals("HO CHI MINH CITY")) {
            return "HCM CITY";
        } else {
            // xử lý các trường hợp khác nếu cần
            return cityName;
        }
    }
}
