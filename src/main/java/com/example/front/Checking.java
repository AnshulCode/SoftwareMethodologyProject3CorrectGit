package com.example.front;


import java.text.DecimalFormat;

/**
 * The type Checking.
 *
 * @author Anshul Prasad, Alexander Reyes
 */
public class Checking extends Account {
    /**
     * The Fee.
     */
    protected double fee;
    /**
     * The Rate.
     */
    protected double rate = .001 / 12;

    private final String TYPE = "Checking";

    /**
     * Instantiates a new Checking.
     */
    public Checking() {

    }

    /**
     * Instantiates a new Checking.
     *
     * @param holder  the holder
     * @param balance the balance
     */
    public Checking(Profile holder, double balance) {
        super.holder = holder;
        super.balance = balance;


    }


    /**
     * gets fee for checking account
     *
     * @return fee
     */
    @Override
    public double fee() {
        if (super.balance >= 1000) {
            this.fee = 0;
            return this.fee;
        }
        this.fee = 25;
        return this.fee;
    }

    /**
     * gets Class Type
     *
     * @return Class Type
     */
    @Override
    public String getType() {
        return TYPE;
    }


    /**
     * Gets print format for toString() in Account
     *
     * @return
     */
    @Override
    public String printFormat() {
        DecimalFormat deciFormat = new DecimalFormat("###,###,###.##");

        deciFormat.setMaximumFractionDigits(2);
        deciFormat.setMinimumFractionDigits(2);

        String rateRounded = deciFormat.format(super.rounder(super.balance));
        if (super.closed) {
            return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded + "::CLOSED";
        }
        return this.TYPE + "::" + super.holder.toString() + "::Balance $" + rateRounded;
    }


    /**
     * Gets preview for Account Database method printFeeAndInterest
     *
     * @return
     */
    @Override
    public String interestPreview() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###.##");

        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        String acct = this.toString();
        return acct + "::fee $" + decimalFormat.format(this.fee()) + "::monthly interest $"
                + decimalFormat.format(this.monthlyInterest());
    }

    /**
     *
     */
    @Override
    public void setMonthlyInterest() {
        if (super.isClosed()) {
            return;
        }
        super.balance += this.monthlyInterest();
        super.balance -= super.rounder(this.fee());
        super.balance = super.rounder(super.balance);
    }

    /**
     * @return
     */
    @Override
    public double monthlyInterest() {
        if (super.isClosed()) {
            return 0.00;
        }
        double monthlyInterest = this.balance * this.rate;
        return super.rounder(monthlyInterest);
    }
}
