package com.example.front;


import java.text.DecimalFormat;


/**
 * The type College checking account.
 *
 * @author Anshul Prasad, Alexander Reyes
 */
public class CollegeChecking extends Checking {


    private static final String NEW_BRUNSWICK = "NEW_BRUNSWICK";
    private static final String CAMDEN = "CAMDEN";
    private static final String NEWARK = "NEWARK";
    private static final String TYPE = "College Checking";


    private int location;


    /**
     * Instantiates a new College checking account.
     *
     * @param holder   the holder
     * @param balance  the balance
     * @param location the location
     */
    public CollegeChecking(Profile holder, double balance, int location) {

        super.holder = holder;
        super.balance = balance;
        this.location = location;
        double rateVar = 0.0025 / 12;
        super.rate = rateVar;

    }

    /**
     * Gets fee for account
     *
     * @return the fee for a College checking Account
     */

    @Override
    public double fee() {
        return 0;
    }

    /**
     * returns the class type for a College checking Account
     *
     * @return
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Gives the format for toString to print
     * Implemented from Accounts class
     * This is needed to get the right input for the toString method
     *
     * @return the CollageCheckingAccount in string format
     */
    @Override
    public String printFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        String rateRounded = decimalFormat.format(super.rounder(super.balance));

        if (!super.closed) {
            if (this.location == 0) {
                return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded + "::" + this.NEW_BRUNSWICK;
            } else if (this.location == 1) {
                return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded + "::" + this.NEWARK;
            } else {
                return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded + "::" + this.CAMDEN;
            }
        }
        if (this.location == 0) {
            return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded + "::CLOSED::" +
                    this.NEW_BRUNSWICK;
        } else if (this.location == 1) {
            return this.TYPE + "::" + super.holder.toString() + "::Balance $" +
                    rateRounded + "::CLOSED::" + this.NEWARK;
        } else {
            return this.TYPE + "::" + super.holder.toString() + "::Balance $" +
                    rateRounded + "::CLOSED::" + this.CAMDEN;
        }


    }


}
