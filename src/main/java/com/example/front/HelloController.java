package com.example.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    public RadioButton MM, CC, Checking, S;

    @FXML
    public TextField nameInput, nameInput1, dateInput, amountInput, campusCode, loyalty;

    @FXML
    public TextArea outputText;


    @FXML
    public Button enterCmd;

    @FXML
    public RadioButton O, D, P, PI, UB, C, PT, W;

    private Model model = new Model();

    private String acctType;
    private String cmd;
    private boolean isCloseCmd;
    private boolean isCmdInPlaceState;
    private boolean isPrintCmd;
    private boolean isOpencmd;


    @FXML
    public void onClick(ActionEvent buttonOnClicked) {
        if (buttonOnClicked.getSource() == enterCmd) {
            if ((cmd == null || acctType == null || nameInput1.getText() == null || nameInput.getText() == null ||
                    dateInput.getText() == null ||
                    amountInput.getText() == null) && !(isCloseCmd || isPrintCmd)) {
                outputText.setText("Improper data");
                return;
            }
            selectCommand();
        } else {
            System.exit(0);
        }
    }

    private boolean checkLoyaltyCode() {
        if (loyalty.getText() == null) {
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

    private boolean checkCampusCode() {
        if (campusCode.getText() == null) {
            outputText.setText("Improper Campus Code");
            return false;
        }
        try {
            int code = Integer.parseInt(campusCode.getText());
            if (!(code != 0 || code != 1 || code != 2)) {
                outputText.setText("Improper Campus Code");
                return false;
            }
        } catch (NumberFormatException e) {
            outputText.setText("Improper Campus Code");
            return false;
        }
        return true;
    }

    private boolean dateCheck(String dob) {
        Date DOB = new Date(dateInput.getText());
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

    private void selectCommand() {
        try {
            double amount = Double.parseDouble(amountInput.getText());

            if (acctType.equals("College Checking") && cmd.equals("O")) {
                if (!checkCampusCode()) {
                    return;
                }
                cleanUpExeptions(nameInput.getText(), nameInput1.getText(),
                        amount, Integer.parseInt(campusCode.getText()), dateInput.getText(), 0, acctType);
            } else if (acctType.equals("Savings") && cmd.equals("O")) {
                if (!checkLoyaltyCode()) {
                    return;
                }
                cleanUpExeptions(nameInput.getText(), nameInput1.getText(),
                        amount, Integer.parseInt(loyalty.getText()), dateInput.getText(), 0, acctType);
            } else {
                cleanUpExeptions(nameInput.getText(), nameInput1.getText(),
                        amount, 0, dateInput.getText(), 0, acctType);
            }
        } catch (NumberFormatException e) {
            outputText.setText("Invalid Amount");
        }
    }

    /**
     * Executes commands
     *
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

        if (cmd.equals("UB") ||cmd.equals("PI") || cmd.equals("PT")||cmd.equals("P")) {
            outputText.setText(model.printCmd(cmd));
        } else {
            if (!dateCheck(dateInput.getText())) {
                outputText.setText("Date of birth invalid");
                return;
            }
            if (cmd.equals("O")) {
                outputText.setText(model.open(fname, lname, amount, loyal, date, code, acctType));
            } else if (cmd.equals("C")) {
                outputText.setText(model.close(fname, lname, date, acctType));
            } else if (cmd.equals("D")) {
                outputText.setText(model.deposit(acctType, fname, lname, date, amount));
            } else {
                outputText.setText(model.withdraw(acctType, fname, lname, date, amount));
            }
        }

    }

    public void getAccountType(ActionEvent event) {
        if (cmd == null || isOpencmd) {
            if (event.getSource() == MM) {
                acctType = ("Money Market");
                moneyMarketAndCheckingState();
            }
            if (event.getSource() == CC) {
                acctType = ("College Checking");
                this.collegeCheckingState();
            }
            if (event.getSource() == Checking) {
                acctType = "Checking";
                this.moneyMarketAndCheckingState();
            }
            if (event.getSource() == S) {
                acctType = ("Savings");
                this.savingsState();
            }
            if (isCloseCmd) {
                this.closeState();
            }
        } else {
            if (event.getSource() == MM) {
                acctType = ("Money Market");
            } else if (event.getSource() == CC) {
                acctType = ("College Checking");
            } else if (event.getSource() == Checking) {
                acctType = "Checking";
            } else if (event.getSource() == S) {
                acctType = ("Savings");
            } else if (isCloseCmd) {
                this.closeState();
            }
        }


    }

    public void moneyMarketAndCheckingState() {
        this.nameInput.setDisable(false);
        this.nameInput1.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.MM.setDisable(false);
        this.CC.setDisable(false);
        this.Checking.setDisable(false);
        this.S.setDisable(false);
    }

    public void savingsState() {
        this.nameInput.setDisable(false);
        this.nameInput1.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(false);
        this.Checking.setDisable(false);
        this.S.setDisable(true);
        this.MM.setDisable(false);
        this.CC.setDisable(false);
        this.S.setDisable(false);

    }

    public void collegeCheckingState() {
        this.nameInput.setDisable(false);
        this.nameInput1.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(false);
        this.loyalty.setDisable(true);
        this.MM.setDisable(false);
        this.CC.setDisable(false);
        this.Checking.setDisable(false);
        this.S.setDisable(false);
    }

    public void closeState() {
        this.amountInput.setDisable(true);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.MM.setDisable(false);
        this.CC.setDisable(false);
        this.Checking.setDisable(false);
        this.S.setDisable(false);
    }

    public void resetState() {
        this.nameInput.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(false);
        this.loyalty.setDisable(false);
        this.MM.setDisable(false);
        this.CC.setDisable(false);
        this.Checking.setDisable(false);
        this.S.setDisable(false);
    }

    public void printState() {
        this.nameInput.setDisable(true);
        this.nameInput1.setDisable(true);
        this.amountInput.setDisable(true);
        this.dateInput.setDisable(true);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.MM.setDisable(true);
        this.CC.setDisable(true);
        this.Checking.setDisable(true);
        this.S.setDisable(true);
    }

    public void depositState() {
        this.nameInput.setDisable(false);
        this.nameInput1.setDisable(false);
        this.amountInput.setDisable(false);
        this.dateInput.setDisable(false);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.MM.setDisable(false);
        this.CC.setDisable(false);
        this.Checking.setDisable(false);
        this.S.setDisable(false);
    }

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

    private void setBooleans() {
        isCloseCmd = true;
        isCmdInPlaceState = true;
        isOpencmd = false;
    }

    public void getCmd(ActionEvent event) {
        if (event.getSource() == C) {
            this.cmd = "C";
            resetState();
            closeState();
            setBooleans();
        }
        if (event.getSource() == D) {
            this.cmd = "D";
            resetState();
            this.depositState();
            setBooleans();
        }
        if (event.getSource() == W) {
            this.cmd = "W";
            resetState();
            this.depositState();
            setBooleans();
        }
        if (event.getSource() == O) {
            this.cmd = "O";
            resetState();
            this.changeState();
            isOpencmd = true;
            isCloseCmd = false;
        }
        if (event.getSource() == P || event.getSource() == PI || event.getSource() == PT ||
                event.getSource() == UB) {
            this.cmd = ((RadioButton) event.getSource()).getId();
            this.printState();
            isCloseCmd = false;
            isOpencmd = false;
            isPrintCmd = true;
        }

    }


}