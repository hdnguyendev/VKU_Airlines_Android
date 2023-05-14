package hdn.dev.baseproject3.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class FormatDay {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatDateSearchVip(String inputDate) {
        DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(inputDate, oldFormat);
        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = date.format(newFormat);
        return formattedDate;
    } public static String formatDateSearch(String inputDate) {
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        DateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date date = inputDateFormat.parse(inputDate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String formatDate(String inputDate) {
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        DateFormat outputDateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy hh:mm a", Locale.getDefault());

        try {
            Date date = inputDateFormat.parse(inputDate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatDateWithoutTime(String inputDate) {
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        DateFormat outputDateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy", Locale.getDefault());

        try {
            Date date = inputDateFormat.parse(inputDate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatMMMMdd(String input) {
        LocalDateTime dateTime = LocalDateTime.parse(input);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd");
        String formattedDate = dateTime.format(formatter);
        return formattedDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatTimeBooking(String input) {
        LocalDateTime dateTime = LocalDateTime.parse(input);
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthValue();
        int year = dateTime.getYear();
        String formattedDateTime = String.format("%d:%02d %02d/%02d/%d", hour, minute, day, month, year);

        return formattedDateTime;

    }

    public static String formatTime(String inputTime) {
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        DateFormat outputDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        try {
            Date date = inputDateFormat.parse(inputTime);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String calculateTimeDifference(String startTime, String endTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endTime, formatter);

        Duration duration = Duration.between(start, end);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        if (hours <= 1) {
            return String.format("%d Hour %d minutes", hours, minutes);
        }
        return String.format("%d Hours %d minutes", hours, minutes);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatDateTicketDetail(String date) {
//        String dateString = "2023-05-02T11:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        return (formattedDate); // Output: May 02, 2023
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatTimeTicketDetail(String date_start, String date_end) {
//        String departureTime = "2023-05-02T11:00:00";
//        String arrivalTime = "2023-05-02T13:00:00";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime departure = LocalTime.parse(date_start, inputFormatter);
        LocalTime arrival = LocalTime.parse(date_end, inputFormatter);
        String formattedTime = departure.format(outputFormatter) + " - " + arrival.format(outputFormatter);
        return (formattedTime); // Output: 11:00 - 13:00
    }


}
