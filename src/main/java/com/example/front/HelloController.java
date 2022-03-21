package com.example.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    public RadioButton MM,CC,Checking,S;
    public TextField nameInput,nameInput1,dateInput,amountInput,campusCode,loyalty;
    public Button enterCmd;

    private Model model = new Model();



    public RadioButton O,D,P,PI,UB,C,PT,W;


    private String acctType;
    private String cmd;
    private boolean isCloseCmd;
    private boolean isPrintCmd;
    private boolean isCmdInPlaceState;
    private boolean isOpencmd;

    @FXML
    protected void execute(ActionEvent event){
        if (cmd == null || acctType == null) {
            System.out.println("Missing info for execution");
            return;
        }else if((nameInput.getText()==null||nameInput1.getText() == null ||
                amountInput.getText()==null)&& !isPrintCmd){
            System.out.println("Missing info for execution");
            return;
        }else if(isPrintCmd){
            model.printCmd(cmd);
            return;
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
    private void setBooleansForCmd(){
        isCloseCmd = false;
        isCmdInPlaceState = true;
        isOpencmd = false;
        isPrintCmd = false;
    }
    public void getCmd(ActionEvent event){
        if(event.getSource() == C){
            this.cmd = "C";
            closeState();
            isCloseCmd = true;
            isCmdInPlaceState = true;
            isOpencmd = false;
        }
        if (event.getSource() == D) {
            this.cmd = "D";
            this.depositState();
            setBooleansForCmd();
        }
        if(event.getSource() == W){
            this.cmd = "W";
            this.depositState();
            setBooleansForCmd();
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