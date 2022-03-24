package com.example.front;

/**
 * This class only helps manage db and give string responses, does not interact with view
 * @author Anshul Prasad, Alexander Reyes
 */

public class ManagerForAccountDB {
    private AccountDatabase db;





    /**
     * Constructor for model, creates db
     */
    public ManagerForAccountDB(){
        this.db = new AccountDatabase();
    }

    /**
     * Opens account on DB, returns response
     * @param fname
     * @param lname
     * @param amount
     * @param loyal , in case of Savings
     * @param date
     * @param code , in case of College Checking
     * @param acctType
     * @return Message
     */
    public String open(String fname, String lname, double amount, int loyal,String date,
                      int code, String acctType){
            Account add = null;
            if(acctType.equals("Money Market")){
                add = new MoneyMarket(new Profile(fname,lname,date.trim()),amount);
                if (amount < 2500) {
                    return "Minimum of $2500 to open a MoneyMarket account.";
                }
            }else if(acctType.equals("Checking")){
                add = new Checking(new Profile(fname,lname,date.trim()),amount);
            }else if(acctType.equals("Savings")){
                add = new Savings(new Profile(fname,lname,date.trim()),amount,loyal);
            } else if (acctType.equals("College Checking")) {
                add = new CollegeChecking(new Profile(fname,lname,date.trim()),amount,code);
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

    /**
     * Close account, returns Message
     * @param fname
     * @param lname
     * @param dob
     * @param acctType
     * @return Message
     */
    public String close(String fname,String lname, String dob, String acctType){
        Account close = setAccount(acctType,fname,lname,dob.trim(),0);
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

    /**
     * sets account type for close, withdraw and deposit
     * @param acctType
     * @param fname
     * @param lname
     * @param dob
     * @param amount
     * @return Account needed
     */
    public  Account setAccount(String acctType,String fname, String lname,String dob,double amount){
        if(acctType.equals("Checking")){
            return new Checking(new Profile(fname,lname,dob.trim()),amount);
        }else if(acctType.equals("Money Market")){
            return new MoneyMarket(new Profile(fname,lname,dob.trim()),amount);
        }else if(acctType.equals("Savings")){
            return new Savings(new Profile(fname,lname,dob.trim()),amount,1);
        }else if(acctType.equals("College Checking")){
            return new CollegeChecking(new Profile(fname,lname,dob.trim()),amount,1);
        }
        return null;
    }

    /**
     * handles deposit
     * @param acctType
     * @param fname
     * @param lname
     * @param dob
     * @param amount
     * @return Message
     */
    public String deposit(String acctType,String fname,String lname,String dob, double amount){
        Account deposit = setAccount(acctType,fname,lname,dob.trim(),amount);
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

    /**
     * withdraws from db
     * @param acctType
     * @param fname
     * @param lname
     * @param dob
     * @param amount
     * @return Message
     */
    public String withdraw(String acctType,String fname,String lname,String dob, double amount){
        Account withdraw = setAccount(acctType,fname,lname,dob.trim(),amount);
        if(amount<=0){
            return "Withdraw - cannot be 0 or negative.";
        }
        if(!db.withdraw(withdraw)){
            if(db.publicFind(withdraw) != null ){
                if(!db.publicFind(withdraw).isSufficentFunds(amount))
                    return "Withdraw - insufficient fund.";
            }else if (db.publicFind(withdraw) != null ) {
                if(db.publicFind(withdraw).isClosed()) {
                    return ("Account is closed.");
                }
            }
            return withdraw.getHolder().toString() + " " +
                    withdraw.getType() + " is not in the database.";
        }
        return "Withdraw - balance updated.";
    }

    /**
     * handles print commands
     * @param cmd
     * @return Accounts is DB.
     */
    public String printCmd(String cmd){
        if(db.isEmpty()){
            return "Account Database is empty";
        }else if(cmd.equals("P")){
            String output = "*list of accounts in the database*\n";
            output+=db.print();
            output+="*end of list*";
            return output;
        }else if(cmd.equals("PI")){
            return db.printFeeAndInterest();
        }else if(cmd.equals("PT")){
            return db.printByAccountType();
        }
        return db.updateAccounts();
    }

}
