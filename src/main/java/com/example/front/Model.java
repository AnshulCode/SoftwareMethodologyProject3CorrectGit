package com.example.front;

public class Model {
    private AccountDatabase db;

    public Model(){
        this.db = new AccountDatabase();
    }
    private Account createAccount( ){
        return null;
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
