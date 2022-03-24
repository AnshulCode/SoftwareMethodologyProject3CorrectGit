package com.example.front;


/**
 * The type Account.
 *
 * @author Anshul Prasad, Alexander Reyes
 */
public abstract class Account {

    /**
     * The Holder.
     */
    protected Profile holder;
    /**
     * Is Closed?
     */
    protected boolean closed;
    /**
     * The Balance.
     */
    protected double balance;

    /**
     * Open.
     *
     * @param amount the amount
     */
    public void open(double amount) {
        if (closed) {
            this.balance = 0.0;
            this.balance = this.rounder(amount);
            closed = false;
        }
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return this.rounder(this.balance);
    }

    /**
     * Rounder double.
     *
     * @param number the number
     * @return the double
     */
    public double rounder(double number) {

        double monthlyInterest = number;

        double placeholder = monthlyInterest;

        double toHund = placeholder * 100.0;
        double toPointFive = toHund + 0.5;
        int reuslts = (int) toPointFive;

        double interest = reuslts / 100.0;
        return interest;
    }


    /**
     * Close.
     */
    public void close() {
        this.balance = 0.00;
        this.closed = true;
    }

    /**
     * Is closed boolean.
     *
     * @return the boolean
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Gets holder.
     *
     * @return the holder
     */
    public Profile getHolder() {
        return this.holder;
    }


    /**
     * checks if accounts are equal
     *
     * @param obj
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        Account compare = (Account) obj;

        if (!this.holder.equals(compare.getHolder())) {
            return false;
        }
        if (compare.getType().contains("Checking") && this.getType().contains("Checking")) {
            return true;
        }
        if (!compare.getType().equals(this.getType())) {
            return false;
        }
        return true;
    }

    /**
     * gets String format of Account
     *
     * @return
     */
    @Override
    public String toString() {
        return this.printFormat();
    }

    /**
     * Withdraw.
     *
     * @param amount the amount
     */
    public void withdraw(double amount) {
        if (!this.isSufficentFunds(amount)) {
            return;
        }
        this.balance -= amount;
        this.balance = this.rounder(this.balance);
    }

    /**
     * Deposit.
     *
     * @param amount the amount
     */
    public void deposit(double amount) {
        if (amount < 0) {
            return;
        }
        this.balance += this.rounder(amount);
        this.balance = this.rounder(this.balance);
    }

    /**
     * Interest preview string.
     *
     * @return the string
     */
    public abstract String interestPreview();

    /**
     * Is sufficent funds boolean.
     *
     * @param amount the amount
     * @return the boolean
     */
    public boolean isSufficentFunds(double amount) {
        if (this.balance - this.fee() - amount <= 0) {
            return false;
        }
        return true;
    }

    /**
     * Sets monthly interest in balance
     */
    public abstract void setMonthlyInterest();

    /**
     * format string for toString()
     *
     * @return the string
     */
    public abstract String printFormat();

    /**
     * Monthly interest
     *
     * @return monthlyInterest double
     */
    public abstract double monthlyInterest(); //return the monthly interest

    /**
     * Fee double.
     *
     * @return the double
     */
    public abstract double fee(); //return the monthly fee

    /**
     * Gets type.
     *
     * @return the type
     */
    public abstract String getType(); //return the account type (class name)

}