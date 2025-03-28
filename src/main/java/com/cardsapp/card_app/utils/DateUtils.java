package com.cardsapp.card_app.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String currentDate() {
        LocalDateTime localDateTime = getLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(localDateTime);
    }

    public static String addMonths(String date, int months) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        LocalDateTime newDateTime = localDateTime.plusMonths(months);
        return dateTimeFormatter.format(newDateTime);
    }

    public static String minusMonths(String date, int months) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        LocalDateTime newDateTime = localDateTime.minusMonths(months);
        return dateTimeFormatter.format(newDateTime);
    }

    public static boolean isDateInFuture(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        LocalDateTime currentDateTime = getLocalDateTime();
        return localDateTime.isAfter(currentDateTime);
    }

    public static int daysLeft(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        LocalDateTime currentDateTime = getLocalDateTime();
        return localDateTime.getDayOfMonth() - currentDateTime.getDayOfMonth();
    }

    public static String addDays(String date, int days) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        LocalDateTime newDateTime = localDateTime.plusDays(days);
        return dateTimeFormatter.format(newDateTime);
    }

    public static String minusDays(String date, int days) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        LocalDateTime newDateTime = localDateTime.minusDays(days);
        return dateTimeFormatter.format(newDateTime);
    }
    /**
     * The function calculates the difference in days between two dates provided as
     * strings in the format
     * "yyyy-MM-dd HH:mm:ss".
     * 
     * @param date1 The `date1` parameter should be a `String` representing a date
     *              and time in the format
     *              "yyyy-MM-dd HH:mm:ss".
     * @param date2 Thank you for providing the code snippet. Could you please
     *              provide the value of `date2`
     *              so that I can assist you further with calculating the difference
     *              in days between two dates?
     * @return The method `differenceInDays` returns the difference in days between
     *         two dates provided as
     *         input strings in the format "yyyy-MM-dd HH:mm:ss".
     */
    public static int differenceInDays(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
        return (int) java.time.temporal.ChronoUnit.DAYS.between(dateTime1, dateTime2);
    }

    /**
     * The function calculates the difference in hours between two given dates in
     * the format "yyyy-MM-dd
     * HH:mm:ss".
     * 
     * @param date1 date1: "2022-10-15 08:30:00"
     * @param date2 date2 is a String representing a date and time in the format
     *              "yyyy-MM-dd HH:mm:ss".
     * @return The method `differenceInHours` returns the difference in hours
     *         between two dates provided as
     *         input strings in the format "yyyy-MM-dd HH:mm:ss".
     */
    public static int differenceInHours(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
        return (int) java.time.temporal.ChronoUnit.HOURS.between(dateTime1, dateTime2);
    }

  /**
   * The function calculates the difference in minutes between two given dates in the format
   * "yyyy-MM-dd HH:mm:ss".
   * 
   * @param date1 date1 is a String representing the first date and time in the format "yyyy-MM-dd
   * HH:mm:ss".
   * @param date2 Thank you for providing the code snippet. Could you please provide the value for the
   * `date2` parameter so that I can assist you further with calculating the difference in minutes
   * between the two dates?
   * @return The method `differenceInMinutes` returns the difference in minutes between two dates
   * provided as input strings in the format "yyyy-MM-dd HH:mm:ss".
   */
    public static int differenceInMinutes(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
        return (int) java.time.temporal.ChronoUnit.MINUTES.between(dateTime1, dateTime2);
    }

    public static String startMonthDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        LocalDateTime startOfMonth = dateTime.withDayOfMonth(1);
        return formatter.format(startOfMonth);
    }

    public static String endMonthDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        LocalDateTime endOfMonth = dateTime.withDayOfMonth(1).plusMonths(1).minusDays(1);
        return formatter.format(endOfMonth);
    }

    public static String dateTodayPlusMinutes(int minutes) {
        LocalDateTime currentDateTime = getLocalDateTime();
        LocalDateTime futureDateTime = currentDateTime.plusMinutes(minutes);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(futureDateTime);
    }

    public static boolean nowAfterDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        LocalDateTime currentDateTime = getLocalDateTime();
        return currentDateTime.isAfter(dateTime);
    }

    public static String yearToday() {
        LocalDateTime currentDateTime = getLocalDateTime();
        return String.valueOf(currentDateTime.getYear());
    }

    public static String monthToday() {
        LocalDateTime currentDateTime = getLocalDateTime();
        return String.valueOf(currentDateTime.getMonthValue());
    }

    public static String dayToday() {
        LocalDateTime currentDateTime = getLocalDateTime();
        return String.valueOf(currentDateTime.getDayOfMonth());
    }

    public static int monthsDifference(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
        return (int) java.time.temporal.ChronoUnit.MONTHS.between(dateTime1, dateTime2);
    }

    public static String dateToday(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(dateTime);
    }
    
    private static final ZoneId NAIROBI_ZONE = ZoneId.of("Africa/Nairobi");

    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now(NAIROBI_ZONE);
    }

}
