package com.agie;
import java.util.Calendar;


// A Personnummer is a 10-digit number that is used to identify a person in Sweden.
// In this implementation we've decided to use 12 digits instead of 10.
// This makes the format as following
// YYYYMMDDXXXX
// The first 8 digits are the date of birth.
// The last 4 digits are a serial number that is used to differentiate people born on the same day.

public class Customer {
    private final long pnr;
    private final String pnrString;
    private final String pnrYear;
    private final String pnrMonth;
    private final String pnrDay;
    private final boolean isLeapYear;

    public Customer(long personnummer){

        try{
            pnrString = Integer.toString(Math.toIntExact(personnummer)); // Unure about math.toint
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Personnummer can only contain numbers");
        }

        pnrYear = pnrString.substring(0, 4);
        pnrMonth = pnrString.substring(4, 6);
        pnrDay = pnrString.substring(6, 8);


        isLeapYear = isYearALeapYear();

        if(!isLengthOfPnrValid()){
            throw new IllegalArgumentException("Personnummer has to be 12 in length");
        }

        if(isDateInFuture()){
            throw new IllegalArgumentException("Personnummer can't be in the future");
        }

        isDayInMonthValid();

        this.pnr = personnummer;
    }

    private boolean isLengthOfPnrValid() {
        return pnrString.length() == 12;
    }

    private boolean isDateInFuture(){
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(pnrYear);
        int month = Integer.parseInt(pnrMonth);
        int day = Integer.parseInt(pnrDay);

        if(year > calendar.get(Calendar.YEAR)){
            return true;
        }
        if (year == calendar.get(Calendar.YEAR)){
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
    private boolean isDayInMonthValid() {
        int daysInMonth;
        if(pnrMonth.equals("02")){
            if (isLeapYear){
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

        return Integer.parseInt(pnrDay) <= daysInMonth;
    }

    private boolean isYearALeapYear() {
        int year = Integer.parseInt(pnrYear);
        if(year % 4 == 0){
            if(year % 100 == 0){
                // TODO: Dead code, guaranteed to be true
                return year % 400 == 0; // no idea if this last part is needed
            }
            return false;
        }
        return false;
    }

    public long getPnr(){
        return this.pnr;
    }
}
