package bank;

import security.key.BankPublicKey;
import security.key.BankSymmetricKey;
import security.*;

public class MobileApp {

    private String randomUniqueStringGen(){
        return Encryptor.randomUniqueStringGen();
    }
    private final String AppId = randomUniqueStringGen();
    public String getAppId() {
        return AppId;
    }

    private BankSymmetricKey bankSymmetricKey;

    String id, password;
    int accountID;
    public MobileApp(String id, String password, int accountID){
        this.id = id;
        this.password = password;
        this.accountID = accountID;
    }

    public Encrypted<BankSymmetricKey> sendSymKey(BankPublicKey publickey){
        //TODO: Problem 1.3
        //goal: create an encrypted<symmetricKey> and send to bank?
        String randStr = randomUniqueStringGen();
        //create symmetric key first
        this.bankSymmetricKey = new BankSymmetricKey(randStr);
        //encrypt symKey using publicKey
        Encrypted<BankSymmetricKey> encBsymk = new Encrypted<BankSymmetricKey>(bankSymmetricKey, publickey);
        return encBsymk;
    }

    public Encrypted<Message> deposit(int amount){
        //TODO: Problem 1.3
        //encrypt msg using symKey
        Message msg = new Message("deposit", id, password, accountID, amount);
        Encrypted<Message> encmsg = new Encrypted<Message>(msg, bankSymmetricKey);
        return encmsg;
    }

    public Encrypted<Message> withdraw(int amount){
        //TODO: Problem 1.3
        Message msg = new Message("withdraw", id, password, accountID, amount);
        Encrypted<Message> encmsg = new Encrypted<Message>(msg, bankSymmetricKey);
        return encmsg;
    }

    public boolean processResponse(Encrypted<Boolean> obj){
        //TODO: Problem 1.3
        if(obj == null) {return false;}
        Boolean decObj = obj.decrypt(bankSymmetricKey);
        if(decObj == null) {return false;}
        return decObj;
    }

}

