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
            if(db.publicFind(add)!= null){
                if(db.publicFind(add).getType().equals(add.getType())){
                    if(db.open(add)){
                        return "Account reopened";
                    }
                }
            }
            if(!db.open(add)){
                return add.getHolder().toString() + " " + "same " +
                        "account(type) is in the database.";
            }else{
                return "Account Opened.";
            }



    }

    public String close(String fname,String lname, String dob, String acctType){
        Account close = setAccount(acctType,fname,lname,dob,0);
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
    public  Account setAccount(String acctType,String fname, String lname,String dob,double amount){
        if(acctType.equals("Checking")){
            return new Checking(new Profile(fname,lname,dob),amount);
        }else if(acctType.equals("Money Market")){
            return new MoneyMarket(new Profile(fname,lname,dob),amount);
        }else if(acctType.equals("Savings")){
            return new Savings(new Profile(fname,lname,dob),amount,1);
        }else if(acctType.equals("College Checking")){
            return new CollegeChecking(new Profile(fname,lname,dob),amount,1);
        }
        return null;
    }
    public String deposit(String acctType,String fname,String lname,String dob, double amount){
        Account deposit = setAccount(acctType,fname,lname,dob,amount);
        if(amount<=0){
            return "Deposit - cannot be 0 or negative.";
        }

        if (db.publicFind(deposit) == null) {
            return (deposit.getHolder().toString() + " " +
                    deposit.getType() + " is not in the database.");
        }else if (db.publicFind(deposit).isClosed()) {
            return ("Account closed.");
        } else if (db.publicFind(deposit) != null) {
            if(!db.publicFind(deposit).getType().equals(deposit.getType())){
                return (deposit.getHolder().toString() + " " +
                        deposit.getType() + " is not in the database.");
            }
        }
        db.deposit(deposit);
        return "Deposit - balance updated.";
    }
    public String withdraw(String acctType,String fname,String lname,String dob, double amount){
        Account withdraw = setAccount(acctType,fname,lname,dob,amount);
        if(amount<=0){
            return "Withdraw - cannot be 0 or negative.";
        }
        if(!db.withdraw(withdraw)){
            if(!db.publicFind(withdraw).isSufficentFunds(amount)){
                return "Withdraw - insufficient fund.";
            }else if (db.publicFind(withdraw).isClosed()) {
                return ("Account is closed.");
            }else{
                return (withdraw.getHolder().toString() + " " +
                        withdraw.getType() + " is not in the database.");
            }
        }
        return "Withdraw - balance updated.";
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
