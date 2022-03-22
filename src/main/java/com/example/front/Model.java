package com.example.front;

public class Model {
    private AccountDatabase db;
    private boolean isNotOpen;

    public Model(){
        this.db = new AccountDatabase();
    }

    public String open(String fname, String lname, double amount, int loyal,String date,
                      int code, String acctType){
            Account add = null;
            if(acctType.equals("Money Market")){
                add = new MoneyMarket(new Profile(fname,lname,date),amount);
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
                return "Account of type "+db.publicFind(add).getType()+"exists";
            }

            return "Account Opened.";

    }

    public String close(String fname, String dob, String acctType){
        Account close;
        if(acctType.equals("Money Market")){
            //close = new MoneyMarket(name,0);
        }
        return "none";
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
