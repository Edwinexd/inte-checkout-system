package com.agie;
import java.util.Calendar;


// A Personnummer is a 10 digit number that is used to identify a person in Sweden.
// The first 6 digits are the date of birth in the format YYMMDD.
// The last 4 digits are a serial number that is used to differentiate people born on the same day.
public class Customer { // TODO: We can't check if the date is in the future if we only allow two didgits for the year.
    private final int pnr;
    private final String pnrString;
    private final String pnrYear;
    private final String pnrMonth;
    private final String pnrDay;

    public Customer(int personnummer){
        pnrString = Integer.toString(personnummer);
        pnrYear = pnrString.substring(0, 2);
        pnrMonth = pnrString.substring(2, 4);
        pnrDay = pnrString.substring(4, 6);


        if(pnrString.length() != 10){
            throw new IllegalArgumentException("Personnummer has to be 10 in length");
        }

        isDateInFuture(); // We can't check this if pnr is 10 characters long.
        isDayInMonth();

        this.pnr = personnummer;
    }

    private void isDateInFuture(){
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(pnrYear);
        int month = Integer.parseInt(pnrMonth);
        int day = Integer.parseInt(pnrDay);

        if(year > calendar.get(Calendar.YEAR)){
            throw new IllegalArgumentException("Personnummer can't be in the future");
        }
        if (year == calendar.get(Calendar.YEAR)){
            if(month > calendar.get(Calendar.MONTH)){
                throw new IllegalArgumentException("Personnummer can't be in the future");
            }
            if (month == calendar.get(Calendar.MONTH)){
                if(day > calendar.get(Calendar.DAY_OF_MONTH)){
                    throw new IllegalArgumentException("Personnummer can't be in the future");
                }
            }
        }
    }

    private void isDayInMonth() {

        int daysInMonth;
        if(pnrMonth.equals("02")){
            daysInMonth = 28;
        } else if (pnrMonth.equals("04") || pnrMonth.equals("06") || pnrMonth.equals("09") || pnrMonth.equals("11")){
            daysInMonth = 30;
        } else {
            daysInMonth = 31;
        }
        if(Integer.parseInt(pnrDay) > daysInMonth){ // Maybe gives error if date is 02/04/07 etc...
            throw new IllegalArgumentException("Personnummer can't have a day higher than the amount of days in the month");
        }
    }

    public int getPnr(){
        return this.pnr;
    }
}
