package com.example.front;

import java.util.Calendar;


/**
 * The type Date.
 *
 * @author Anshul Prasad , Alexander Reyes
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    /**
     * The constant QUADRENNIAL.
     */
    public static final int QUADRENNIAL = 4;
    /**
     * The constant CENTENNIAL.
     */
    public static final int CENTENNIAL = 100;
    /**
     * The constant QUATERCENTENNIAL.
     */
    public static final int QUATERCENTENNIAL = 400;


    /**
     * Instantiates a new Date.
     * If the date format is incorrect, All fields are -1
     *
     * @param date String of date, everything set to 0 if invalid
     */
    public Date(String date) {
        String[] a = date.split("/");

        try {

            this.year = Integer.parseInt(a[2]);
            this.month = Integer.parseInt(a[0]);
            this.day = Integer.parseInt(a[1]);

        } catch (NumberFormatException e) {

            this.year = -1;
            this.month = -1;
            this.day = -1;
        } catch (ArrayIndexOutOfBoundsException e) {

            this.year = -1;
            this.month = -1;
            this.day = -1;
        }


    }

    /**
     * Instantiates a new Date with current date.
     */
// Auto sets date top current date, default
    public Date() {
        Calendar cal = Calendar.getInstance();

        this.month = cal.get(Calendar.MONTH) + 1;
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.year = cal.get(Calendar.YEAR);


    }


    /**
     * IChecks if year is leap year
     *
     * @return the boolean
     */
    public boolean isLeapYear() {
        if (this.year % QUADRENNIAL == 0 || this.year % QUATERCENTENNIAL == 0) {
            return this.year % CENTENNIAL != 0;

        }
        return false;

    }

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
// checks if Date is valid
    public boolean isValid() {
        if (this.year <= 0 || this.month <= 0 || this.day <= 0 ||
                this.month > 12 || this.day > 31) {
            return false;
        }
        if (this.month == 2) {
            if (this.day > 29) {
                return false;
            }
            if (this.day == 29) {
                return this.isLeapYear();

            }
            return true;
        }
        if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
            return this.day <= 30;
        }
        return true;
    }

    /**
     * To days int.
     *
     * @return the int
     */
// converts the date to days
    public int toDays() {
        int year = this.year;
        int month = this.month;
        if (this.month <= 2) {
            year = year - 1;
            month = month + 12;
        }
        return ((1461 * year) / 4) + ((153 * month) / 5) + this.day;

    }

    /**
     * Converts Date to string format
     *
     * @return Date to string format
     */
    @Override
    public String toString() {
        String dateStr = this.month + "/" + this.day + "/" + this.year;
        return dateStr;

    }

    /**
     * Implements comapreTo function with Date
     *
     * @param date
     * @return Days difference, if any date invalid, returns Integer.MAX_VALUE
     */

    public int compareTo(Date date) {

        if (!this.isValid() || !date.isValid()) {
            return Integer.MAX_VALUE;
        }
        return this.toDays() - date.toDays();
    }
}