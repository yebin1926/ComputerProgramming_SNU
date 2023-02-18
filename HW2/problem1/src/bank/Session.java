package bank;

public class Session {

    private String sessionKey;
    private Bank bank;
    private boolean valid;
    private int transLimit = 3;
    private int numCall = 0;

    Session(String sessionKey,Bank bank){
        this.sessionKey = sessionKey;
        this.bank = bank;
        valid = true;
    }

    public boolean deposit(int amount) {
        //TODO: Problem 1.2
        if(!valid) {return false;}
        Boolean p = bank.deposit(sessionKey, amount);
        numCall++;
        if(numCall >= transLimit) {expireSession();}
        return p;
    }

    public boolean withdraw(int amount) {
        //TODO: Problem 1.2
        if(!valid) {return false;}
        Boolean p = bank.withdraw(sessionKey, amount);
        numCall++;
        if(numCall >= transLimit) {expireSession();}
        return p;
    }

    public boolean transfer(String targetId, int targetAccountID, int amount) {
        //TODO: Problem 1.2
        if(!valid) {return false;}
        Boolean p = bank.transfer(sessionKey, targetId, targetAccountID, amount);
        numCall++;
        if(numCall >= transLimit) {
            expireSession();
        }
        return p;
    }

    public void expireSession(){
        this.valid = false;
    }

}
