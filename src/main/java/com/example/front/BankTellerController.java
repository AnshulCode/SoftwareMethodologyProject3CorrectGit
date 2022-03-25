package com.example.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

/**
 * Controller Class
 * @author Anshul Prasad, Alexander Reyes
 */
public class BankTellerController {

    @FXML
    public RadioButton moneyMarketButton, collegeCheckingButton, checkingButton, savingsButton;


    @FXML
    public TextField fNameInput, lNameInput, dateInput, amountInput, campusCode, loyalty;

    @FXML
    public TextArea outputText;


    @FXML
    public Button enterCmd;

    @FXML
    public RadioButton O, D, P, PI, UB, C, PT, W;

    private ManagerForAccountDB model = new ManagerForAccountDB();

    private String acctType;
    private String cmd;
    private boolean isCloseCmd;
    private boolean isCmdInPlaceState;
    private final int CAMDENCODE = 2;
    private boolean isPrintCmd;
    private boolean isOpencmd;

    /**
     * check for missing fields
     * @return Tru if there are missing fields
     */
    public boolean missingFields() {

        if(!isPrintCmd){
                if ((this.cmd == null || this.acctType == null)) {
                    return true;
                } else if (!isCloseCmd) {
                    if(((lNameInput.getText().isEmpty()) || (fNameInput.getText().isEmpty()) ||
                            (dateInput.getText().isEmpty())
                            || (amountInput.getText().isEmpty()))){
                        return true;

                    }
                }else{
                    if(((lNameInput.getText().isEmpty()) || (fNameInput.getText().isEmpty()) ||
                            (dateInput.getText().isEmpty()))){
                        return true;
                    }
                }
        }
        return false;
    }
    /**
     * Code for action Button
     * Checks if any field is null or empty
     * executes checkMoreFaults
     * @param buttonOnClicked
     */
    @FXML
    public void onClick(ActionEvent buttonOnClicked) {
        if (buttonOnClicked.getSource() == enterCmd) {
            if(missingFields()) {
                outputText.setText("Missing Information");
                return;
            }else if (!dateCheck(dateInput.getText()) && !dateInput.getText().isEmpty() &&(!isPrintCmd) ) {
                outputText.setText("Date of birth invalid");
                return;
            }else  if (cmd.equals("UB") ||cmd.equals("PI") || cmd.equals("PT")||cmd.equals("P")) {
                outputText.setText(model.printCmd(cmd));
                return;
            }
            checkForMoreFaults();
        }
    }

    /**
     * check is if loyalty code is valid, sends error message to UI if false
     * @return True if Valid
     */
    private boolean checkLoyaltyCode() {
        if (loyalty.getText().isEmpty()) {
            outputText.setText("Improper Loyalty Code");
            return false;
        }

        try {
            int isLoyal = Integer.parseInt(loyalty.getText());
            if (!(isLoyal == 0 || isLoyal == 1)) {
                outputText.setText("Improper Loyalty Code");
                return false;
            }
        } catch (NumberFormatException e) {
            outputText.setText("Improper Loyalty Code");
            return false;
        }
        return true;
    }

    /**
     * Checks if campus code is vaild,sends error to Text Area
     * @return True if Valid
     */
    private boolean checkCampusCode() {
        if (campusCode.getText() == null) {
            outputText.setText("Improper Campus Code");
            return false;
        }
        try {
            int code = Integer.parseInt(campusCode.getText().trim());
            if (!(code == 0 || code == 1 || code == CAMDENCODE)) {
                outputText.setText("Improper Campus Code");
                return false;
            }
        } catch (NumberFormatException e) {
            outputText.setText("Improper Campus Code");
            return false;
        }
        return true;
    }

    /**
     * checks if date is valid
     * @param dob
     * @return True if valid, prints error message in UI if false
     */
    private boolean dateCheck(String dob) {
        Date DOB = new Date(dob.trim());

        Date curr = new Date();
        if (!DOB.isValid()) {
            return false;
        } else if (curr.compareTo(DOB) <= 0) {
            return false;
        }else if(DOB.getYear()<1900){
            return false;
        }
        return true;
    }

    /**
     * Checks for proper loyalty codes and campus codes, check is if amount is a double
     * prints errors to View
     */
    private void checkForMoreFaults() {
        try {
            
            double amount = Double.parseDouble(amountInput.getText().trim());
            if (acctType.equals("College Checking") && cmd.equals("O")) {
                if (!checkCampusCode()) {
                    return;
                }
                cleanUpExeptions(fNameInput.getText().trim(), lNameInput.getText().trim(),
                        amount, 0, dateInput.getText(), Integer.parseInt(campusCode.getText()), acctType);
            } else if (acctType.equals("Savings") && cmd.equals("O")) {
                if (!checkLoyaltyCode()) {
                    return;
                }
                cleanUpExeptions(fNameInput.getText().trim(), lNameInput.getText().trim(),
                        amount, Integer.parseInt(loyalty.getText()), dateInput.getText(), 0, acctType);
            } else {
                cleanUpExeptions(fNameInput.getText().trim(), lNameInput.getText().trim(),
                        amount, 0, dateInput.getText(), 0, acctType);

            }
        } catch (NumberFormatException e) {
            if(!isPrintCmd){
                outputText.setText("Invalid Amount");
            }else if(isCloseCmd){
                cleanUpExeptions(fNameInput.getText().trim(), lNameInput.getText().trim(),
                        0, 0, dateInput.getText(), 0, acctType);
            }
        }

    }

    /**
     * Executes comoneyMarketButtonands for AccountDB
     * Includes all required params for running the comoneyMarketButtonands
     * Sends correct responses to view
     * @param fname
     * @param lname
     * @param amount
     * @param loyal
     * @param date
     * @param code
     * @param acctType
     */
    public void cleanUpExeptions(String fname, String lname, double amount, int loyal, String date,
                                 int code, String acctType) {

            String resp = "";
            if (cmd.equals("O")) {
                resp = model.open(fname, lname, amount, loyal, date, code, acctType);
                outputText.setText(resp);
            } else if (cmd.equals("C")) {
                resp = model.close(fname, lname, date, acctType);
                outputText.setText(resp);
            } else if (cmd.equals("D")) {
                resp = model.deposit(acctType, fname, lname, date, amount);
                outputText.setText(resp);
            } else if (cmd.equals("W")){
                resp = model.withdraw(acctType, fname, lname, date, amount);
                outputText.setText(resp);
            }


    }

    /**
     * Event listener for Account Type
     * @param event, this is when Radio Button for Account Type is selected
     */
    public void getAccountType(ActionEvent event) {
        if (cmd == null || isOpencmd) {
            if (event.getSource() == moneyMarketButton) {
                acctType = ("Money Market");
                moneyMarketAndCheckingState();
            }
            if (event.getSource() == collegeCheckingButton) {
                acctType = ("College Checking");
                this.collegeCheckingState();
            }
            if (event.getSource() == checkingButton) {
                acctType = "Checking";
                this.moneyMarketAndCheckingState();
            }
            if (event.getSource() == savingsButton) {
                acctType = ("Savings");
                this.savingsState();
            }
            if (isCloseCmd) {
                this.closeState();
            }
        } else {
            if (event.getSource() == moneyMarketButton) {
                acctType = ("Money Market");
            } else if (event.getSource() == collegeCheckingButton) {
                acctType = ("College Checking");
            } else if (event.getSource() == checkingButton) {
                acctType = "Checking";
            } else if (event.getSource() == savingsButton) {
                acctType = ("Savings");
            } else if (isCloseCmd) {
                this.closeState();
            }
        }


    }

    /**
     * set up IU for opening MoneyMarket  and Checking Account
     */
    public void moneyMarketAndCheckingState() {
        this.fNameInput.setDisable(false);
        this.lNameInput.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.moneyMarketButton.setDisable(false);
        this.collegeCheckingButton.setDisable(false);
        this.checkingButton.setDisable(false);
        this.savingsButton.setDisable(false);
    }

    /**
     * set UI for opening Savings account
     */
    public void savingsState() {
        this.fNameInput.setDisable(false);
        this.lNameInput.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(false);
        this.checkingButton.setDisable(false);
        this.savingsButton.setDisable(true);
        this.moneyMarketButton.setDisable(false);
        this.collegeCheckingButton.setDisable(false);
        this.savingsButton.setDisable(false);

    }

    /**
     * set UI for opening Callege Checking Account
     */
    public void collegeCheckingState() {
        this.fNameInput.setDisable(false);
        this.lNameInput.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(false);
        this.loyalty.setDisable(true);
        this.moneyMarketButton.setDisable(false);
        this.collegeCheckingButton.setDisable(false);
        this.checkingButton.setDisable(false);
        this.savingsButton.setDisable(false);
    }

    /**
     * Set UI for close cmd
     */
    public void closeState() {
        this.amountInput.setDisable(true);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.moneyMarketButton.setDisable(false);
        this.collegeCheckingButton.setDisable(false);
        this.checkingButton.setDisable(false);
        this.savingsButton.setDisable(false);
    }

    /**
     * reset UI after print cmd
     */
    public void resetState() {
        this.fNameInput.setDisable(false);
        this.lNameInput.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(false);
        this.loyalty.setDisable(false);
        this.moneyMarketButton.setDisable(false);
        this.collegeCheckingButton.setDisable(false);
        this.checkingButton.setDisable(false);
        this.savingsButton.setDisable(false);
    }

    /**
     * set UI for print cmd
     */
    public void printState() {
        this.fNameInput.setDisable(true);
        this.lNameInput.setDisable(true);
        this.amountInput.setDisable(true);
        this.dateInput.setDisable(true);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.moneyMarketButton.setDisable(true);
        this.collegeCheckingButton.setDisable(true);
        this.checkingButton.setDisable(true);
        this.savingsButton.setDisable(true);
    }

    /**
     * Set UI for deposit and withdraw
     */
    public void depositState() {
        this.fNameInput.setDisable(false);
        this.lNameInput.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.moneyMarketButton.setDisable(false);
        this.collegeCheckingButton.setDisable(false);
        this.checkingButton.setDisable(false);
        this.savingsButton.setDisable(false);
    }

    /**
     * activated on open radio button clicked, alters UI
     */
    public void changeState() {
        if (this.acctType != null) {
            if (this.acctType.equals("Money Market") || this.acctType.equals("Checking")) {
                moneyMarketAndCheckingState();
            } else if (acctType.equals("Savings")) {
                savingsState();
            } else if (acctType.equals("College Checking")) {
                collegeCheckingState();
            }
        }
    }

    /**
     * filler code to help aler UI for open,close deposit and withdraw
     */
    private void setBooleans() {
        isCloseCmd = true;
        isCmdInPlaceState = true;
        isOpencmd = false;
        isPrintCmd = false;
    }

    /**
     * Code for Radio Buttons for ComoneyMarketButtonands
     * @param event waits for radio buttons to be selected
     */
    public void getCmd(ActionEvent event) {
        if (event.getSource() == C) {
            this.cmd = "C";
            resetState();
            closeState();
            setBooleans();
        }else if (event.getSource() == D || event.getSource() == W) {
            this.cmd = ((RadioButton) event.getSource()).getId().trim();
            resetState();
            this.depositState();
            setBooleans();
        }else if (event.getSource() == O) {
            this.cmd = "O";
            resetState();
            this.changeState();
            isOpencmd = true;
            isCloseCmd = false;
        }else if (event.getSource() == P || event.getSource() == PI || event.getSource() == PT ||
                event.getSource() == UB) {
            this.cmd = ((RadioButton) event.getSource()).getId().trim();
            this.printState();
            isCloseCmd = false;
            isOpencmd = false;
            isPrintCmd = true;
        }
    }


}