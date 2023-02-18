package bank;
import java.util.Scanner;

public class Client {
    private int numAccounts;
    final static int maxAccounts = 10;
    private BankAccount[] accounts = new BankAccount[maxAccounts];

    private String id;
    private String password;
    private String membership = "Normal";

    public String getPassword() {
        return password;
    }

    public BankAccount[] getBankAccounts() {
        return accounts;
    }

    private boolean authenticated = false;

    Client(String id, String password) {
        //TODO: Problem 1.1
        // how to construct the client object?
        this.id = id;
        this.password = password;
    }

    String getId(){
        return this.id;
    }

    int getNumAcc() {return this.numAccounts;}

    void addNumAcc() {this.numAccounts ++;}

    String getMembership(){
        //TODO: Problem 1.1
        int totalBalance = 0;
        for(int i=0; i<numAccounts; i++) {
            totalBalance += accounts[i].getBalance();
        }
        if(totalBalance <= 10000) {return "Normal";}
        return "VIP";
    }

    boolean authenticate(String password){
        //TODO: Problem 1.1
        if(password.equals(this.password)) {
            this.authenticated = true;
            return true; //do i return true as well? or just change authenticated to true
        }
        return false;
    }

    void expireAuthenticatedState(){
        //TODO: Problem 1.1
        this.authenticated = false;
    }

    BankAccount findAccount(int accountID){
        //TODO: Problem 1.1
        for(BankAccount account : accounts) {
            if (account != null && accountID == account.getAccountId()) {return account;}
        }
        return null;
    }

    boolean createAccount(int accountID, int initBalance){
        //TODO: Problem 1.1
        if(findAccount(accountID)==null && numAccounts <maxAccounts) {
            BankAccount newAcc = new BankAccount(accountID, initBalance);
            accounts[numAccounts] = newAcc;
            numAccounts++;
            return true;
        }
        return false;
    }
}
