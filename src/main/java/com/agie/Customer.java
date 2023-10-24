package com.agie;
import java.util.Calendar;

/* A personNumber is a 10-digit number that is used to identify a person in Sweden.
 In this implementation we've decided to use 12 digits instead of 10.
 This makes the format as following:
 YYYYMMDDXXXX
 The first 8 digits are the date of birth.
 The last 4 digits are a serial number that is used to differentiate people born on the same day.
 */

public class Customer {
    private final long pnr;
    private final int age;

    public Customer(long personNumber){
        if(personNumber < 0){
            throw new IllegalArgumentException("personNumber can't be negative");
        }
        if(!isLengthOfPnrValid(personNumber)){
            throw new IllegalArgumentException("Personnummer has to be 12 in length");
        }

        final String pnrString = ""+personNumber;

        final int pnrYear = Integer.parseInt(pnrString.substring(0,4));
        final String pnrMonth = pnrString.substring(4, 6);
        final String pnrDay = pnrString.substring(6, 8);
        // We can't validate the last 4 digits.

        if(pnrYear < 1900){
            throw new IllegalArgumentException("A person can't be born before the year 1900 and be a customer");
        }

        if(isDateInFuture(pnrYear, pnrMonth, pnrDay)){
            throw new IllegalArgumentException("Person-number can't be in the future");
        }

        if(!isDayInMonthValid(pnrYear,pnrMonth, pnrDay)){
            throw new IllegalArgumentException("Day is not valid for the month");
        }
        this.pnr = personNumber;
        this.age = getAgeByPersonNumber(pnrYear, pnrMonth, pnrDay);
    }

    private int getAgeByPersonNumber(int Year, String Month, String Day) {
        Calendar calendar = Calendar.getInstance();
        int age = calendar.get(Calendar.YEAR) - Year;
        if (Integer.parseInt(Month) > calendar.get(Calendar.MONTH)) {
            age--;
        } else if (Integer.parseInt(Month) == calendar.get(Calendar.MONTH)) {
            if (Integer.parseInt(Day) > calendar.get(Calendar.DAY_OF_MONTH)) {
                age--;
            }
        }
        return age;
    }

    private boolean isLengthOfPnrValid(Long personNumber){
        String pnrString = ""+personNumber;
        return pnrString.length() == 12;
    }

    private boolean isDateInFuture(int pnrYear, String pnrMonth, String pnrDay){
        Calendar calendar = Calendar.getInstance();
        final int month = Integer.parseInt(pnrMonth);
        final int day = Integer.parseInt(pnrDay);

        if(pnrYear > calendar.get(Calendar.YEAR)){
            return true;
        }
        if (pnrYear == calendar.get(Calendar.YEAR)){
            if(month > calendar.get(Calendar.MONTH)){
                return true;
            }
            if (month == calendar.get(Calendar.MONTH)){
                return day > calendar.get(Calendar.DAY_OF_MONTH);
            }
        }
        return false;
    }

    // Checks if the day is valid for the month.
    // EXAMPLE: 31st of February is not a valid date.
    // EXAMPLE: 29th of February is only valid if the year is a leap year.
    private boolean isDayInMonthValid(int pnrYear, String pnrMonth, String pnrDay){
        int daysInMonth;
        if(pnrMonth.equals("02")){
            if (isYearALeapYear(pnrYear)){
                daysInMonth = 29;
            }
            else {
                daysInMonth = 28;
            }
        } else if (pnrMonth.equals("04") || pnrMonth.equals("06") || pnrMonth.equals("09") || pnrMonth.equals("11")){
            daysInMonth = 30;
        } else {
            daysInMonth = 31;
        }

        if(Integer.parseInt(pnrDay) < 1){
            throw new IllegalArgumentException("Day can't be less than 1");
        }

        return Integer.parseInt(pnrDay) <= daysInMonth;
    }

    private boolean isYearALeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public long getPnr(){
        return this.pnr;
    }

    public int getAge(){
        return this.age;
    }
}
