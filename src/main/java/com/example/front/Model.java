package com.example.front;

public class Model {
    private AccountDatabase db;


    public Model(){
        this.db = new AccountDatabase();
    }

    public String open(String fname, String lname, double amount, int loyal,String date,
                      int code, String acctType){
            Account add = null;
            if(acctType.equals("Money Market")){
                add = new MoneyMarket(new Profile(fname,lname,date),amount);
                if (amount < 2500) {
                    return "Minimum of $2500 to open a MoneyMarket account.";
                }
            }else if(acctType.equals("Checking")){
                add = new Checking(new Profile(fname,lname,date),amount);
            }else if(acctType.equals("Savings")){
                add = new Savings(new Profile(fname,lname,date),amount,loyal);
            } else if (acctType.equals("College Checking")) {
                add = new CollegeChecking(new Profile(fname,lname,date),amount,code);
            }
            if(amount <= 0){
                return "Initial deposit cannot be 0 or negative";
            }

            if(!db.open(add)){
                return add.getHolder().toString() + " " + "same " +
                        "account(type) is in the database.";
            }

            return "Account Opened.";

    }

    public String close(String fname,String lname, String dob, String acctType){
        Account close = null;
        if(acctType.equals("Checking")){
            close = new Checking(new Profile(fname,lname,dob),0);
        }else if(acctType.equals("Money Market")){
            close = new MoneyMarket(new Profile(fname,lname,dob),0);
        }else if(acctType.equals("Money Market")){
            close = new Savings(new Profile(fname,lname,dob),0,1);
        }else if(acctType.equals("College Checking")){
            close = new CollegeChecking(new Profile(fname,lname,dob),0,1);
        }
        if(!db.close(close)){
            if (db.publicFind(close) != null) {
                Account found = db.publicFind(close);
                if (!db.publicFind(close).getType().equals(close.getType())) {
                    return close.getHolder().toString() + " " +
                            close.getType() + " is not in the database.";
                }
                if (found.isClosed()) {
                    return ("Account is closed already.");

                }
                if (db.close(close)) {
                    return ("Account is closed.");
                }
            } else {
                return "Account does not exist.";
            }
        }

        return "Account Closed";
    }
    public String printCmd(String cmd){
        if(db.isEmpty()){
            return "Account Database is empty";
        }
        if(cmd.equals("P")){
            return db.print();
        }
        if(cmd.equals("PI")){
            return db.printFeeAndInterest();
        }
        if(cmd.equals("PT")){
            return db.printByAccountType();
        }
        return db.updateAccounts();
    }

}
