package com.example.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    public RadioButton MM,CC,Checking,S;

    @FXML
    public TextField nameInput,nameInput1,dateInput,amountInput,campusCode,loyalty;

    @FXML
    public Button enterCmd;

    @FXML
    public RadioButton O,D,P,PI,UB,C,PT,W;

    private String acctType;
    private String cmd;
    private boolean isCloseCmd;
    private boolean isCmdInPlaceState;
    private boolean isPrintCmd;
    private boolean isOpencmd;

    @FXML
    public void onClick(ActionEvent buttonOnClicked){
        if(buttonOnClicked.getSource() == enterCmd){
            if((cmd == null || acctType == null || nameInput1.getText() == null || nameInput.getText() == null ||
                    dateInput.getText() == null ||
                    amountInput.getText() == null)&&!(isCloseCmd||isPrintCmd)){
                System.out.println("Improper data");
            }
            selectCommand();

        }else{
            System.exit(0);
        }
    }
    private void selectCommand(){
        try{
            double amount = Double.parseDouble(amountInput.getText());
        }catch(NumberFormatException e){
            System.out.println();
        }
    }
    public void getAccountType(ActionEvent event){
        if(cmd == null || isOpencmd){
            if(event.getSource() == MM){
                acctType = ("Money Market");
                moneyMarketAndCheckingState();
            }
            if(event.getSource() == CC){
                acctType = ("College Checking");
                this.collegeCheckingState();
            }
            if(event.getSource() == Checking){
                acctType = "Checking";
                this.moneyMarketAndCheckingState();
            }
            if(event.getSource() == S){
                acctType = ("Savings");
                this.savingsState();
            }
            if (isCloseCmd) {
                this.closeState();
            }
        }else{

            if(event.getSource() == MM){
                acctType = ("Money Market");
            }else if(event.getSource() == CC){
                acctType = ("College Checking");
            }else if(event.getSource() == Checking){
                acctType = "Checking";
            }else if(event.getSource() == S){
                acctType = ("Savings");
            }else if (isCloseCmd) {
                this.closeState();
            }
        }


    }
    public void moneyMarketAndCheckingState(){
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
    public void savingsState(){
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

    public void collegeCheckingState(){
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

    public void closeState(){
        this.amountInput.setDisable(true);
        this.campusCode.setDisable(true);
        this.loyalty.setDisable(true);
        this.MM.setDisable(false);
        this.CC.setDisable(false);
        this.Checking.setDisable(false);
        this.S.setDisable(false);
    }

    public void resetState(){
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
    public void printState(){
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
    public void depositState(){
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
        if(acctType != null) {
            if (acctType.equals("Money Market") || acctType.equals("Checking")) {
                moneyMarketAndCheckingState();
            } else if (acctType.equals("Savings")) {
                savingsState();
            } else if (acctType.equals("College Checking")) {
                collegeCheckingState();
            }
        }
    }
    private void setBooleans(){
        isCloseCmd = true;
        isCmdInPlaceState = true;
        isOpencmd = false;
    }
    public void getCmd(ActionEvent event){
        if(event.getSource() == C){
            this.cmd = "C";
            closeState();
            setBooleans();
        }
        if (event.getSource() == D) {
            this.cmd = "D";
            this.depositState();
            setBooleans();
        }
        if(event.getSource() == W){
            this.cmd = "W";
            this.depositState();
            setBooleans();
        }
        if(event.getSource() == O){
            this.cmd = "O";
            this.changeState();
            isOpencmd  = true;
            isCloseCmd = false;
        }
        if(event.getSource() == P || event.getSource()==PI || event.getSource() == PT ||
                event.getSource() == UB ){
            this.cmd =((RadioButton)event.getSource()).getId();
            this.printState();
            isCloseCmd = false;
            isOpencmd = false;
            isPrintCmd = true;
        }

    }






}