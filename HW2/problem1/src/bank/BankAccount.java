package bank;
import bank.event.*;
import java.util.Scanner;

class BankAccount {
    private Event[] events = new Event[maxEvents];
    final static int maxEvents = 100;

    private int event_num;
    private int accountId;
    private int balance;
    BankAccount(int accountID, int balance) {
        //TODO: Problem 1.1
         // how do you construct Bank Account obj?
        this.accountId = accountID; //this or newBank?
        this.balance = balance;
    }

    int getAccountId() {
        return accountId;
    }
    int getBalance() {
        return balance;
    }

    Event[] getEvents() {
        Event[] events = new Event[event_num];
        for (int i=0; i<event_num; i++)
            events[i] = this.events[i];
        return events;
    }

    void deposit(int amount) {
        //TODO: Problem 1.1
        this.balance += amount;
        DepositEvent newDeposit = new DepositEvent();
        events[event_num] = newDeposit; //is this right?
        event_num++;
    }

    boolean withdraw(int amount, String membership) {
        //TODO: Problem 1.1
        if(membership.equals("Normal")) {
            amount += 5;
        }
        if(this.balance - amount < 0) { //is this.balance correct
            return false;
        }
        this.balance -= amount;
        WithdrawEvent newWithdraw = new WithdrawEvent();
        events[event_num] = newWithdraw;
        event_num ++;
        return true;
    }

    void receive(int amount) {
        //TODO: Problem 1.1
        this.balance += amount;
        ReceiveEvent newReceive = new ReceiveEvent();
        events[event_num] = newReceive;
        event_num ++;
    }

    boolean send(int amount, String membership) {
        //TODO: Problem 1.1
        if(membership.equals("Normal")) {
            amount += 5;
        }
        if(this.balance - amount < 0) { //is this.balance correct
            return false;
        }
        this.balance -= amount;
        SendEvent newSend = new SendEvent();
        events[event_num] = newSend;
        event_num ++;
        return true;
    }
}
