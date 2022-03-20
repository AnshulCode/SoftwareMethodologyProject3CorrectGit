package com.example.front;


import java.text.DecimalFormat;

/**
 * The type Savings.
 *
 * @author Anshul Prasad, Alexander Reyes
 */
public class Savings extends Account {
    private final String TYPE = "Savings";
    /**
     * The Fee.
     */

    private final double savingsFee = 6;
    protected double fee = savingsFee;
    /**
     * The Rate.
     */
    protected double rate;


    /**
     * The Is loyal.
     */
    protected boolean isLoyal;

    private final double normalRate = (.003 / 12);
    private final double loyalRate = (.0045 / 12);

    /**
     * Instantiates a new Savings.
     *
     * @param holder  the holder
     * @param balance the balance
     * @param loyalty the loyalty
     */
    public Savings(Profile holder, double balance, int loyalty) {
        super.holder = holder;
        super.balance = balance;
        if (loyalty == 1) {
            this.isLoyal = true;
        }
        if (this.isLoyal) {
            this.rate = loyalRate;
        } else {
            this.rate = normalRate;
        }


    }

    /**
     * Base constructor, dont use this
     */
    public Savings() {

    }

    /**
     * Gets fee, inherited from Account,implemented here
     *
     * @return fee
     */

    @Override
    public double fee() {
        if (super.balance >= 300) {
            this.fee = 0;
            return 0;
        }
        this.fee = savingsFee;
        return savingsFee;
    }


    /**
     * Gets Type of Account, inherited from account, implemented here
     *
     * @return
     */
    @Override
    public String getType() {
        return this.TYPE;
    }


    /**
     * Gives the interest preview for printFeeAndInterest method in AccountDatabase
     *
     * @return
     */
    @Override
    public String interestPreview() {
        DecimalFormat decimalFormat = new DecimalFormat("##,###,###,###.##");

        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        String acct = this.toString();
        return acct + "::fee $" + decimalFormat.format(this.fee()) + "::monthly interest $" +
                decimalFormat.format(this.monthlyInterest());
    }


    /**
     * Updates balance with interest
     */

    @Override
    public void setMonthlyInterest() {
        if (super.isClosed()) {
            return;
        }
        super.balance += this.monthlyInterest();
        super.balance -= this.fee();
        super.balance = super.rounder(super.balance);
    }

    /**
     * Gives the format for toString method
     *
     * @return String for toString
     */
    @Override
    public String printFormat() {
        DecimalFormat deciFormat = new DecimalFormat("###,###,###.##");

        deciFormat.setMaximumFractionDigits(2);
        deciFormat.setMinimumFractionDigits(2);

        String rateRounded = deciFormat.format(super.rounder(super.balance));
        if (!this.isLoyal) {
            if (super.closed) {
                return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded + "::CLOSED";
            }
            return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded;
        }
        if (super.closed) {
            return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded + "::CLOSED";
        }
        return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded + "::Loyal";
    }

    /**
     * gets monthly interest, Inherited from Account
     *
     * @return
     */
    @Override
    public double monthlyInterest() {

        double monthlyInterest = this.balance * this.rate;
        return super.rounder(monthlyInterest);
    }
}
