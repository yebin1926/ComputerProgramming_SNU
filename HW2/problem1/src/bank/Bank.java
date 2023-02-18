package bank;

import bank.event.*;
import security.*;
import security.key.*;

public class Bank {
    private int numClients=0;
    final static int maxClients = 100;
    private Client[] clients = new Client[maxClients];

    public void createClient(String id, String password){
        //TODO: Problem 1.1
        Boolean p = true;
        for(int i=0; i<=numClients; i++) {
            Client c = clients[i];
            if(c!= null && id == c.getId()) {
                p = false;
            }
        }
        if(p){
            Client newClient = new Client(id, password);
            clients[numClients] = newClient;
            numClients++;
        }
    }
    public void createAccount(String id, String password, int accountID) {
        createAccount(id, password, accountID, 0);
    }

    public void createAccount(String id, String password, int accountID, int initBalance) {
        //TODO: Problem 1.1
        //checking which client the id is --> then pw authentication
        Boolean t=false;
        if (checkClientID(id) != null){
            Client client1 = checkClientID(id);
            if (client1.authenticate(password)) {
                client1.createAccount(accountID, initBalance);
            }
            client1.expireAuthenticatedState();
        }
    }

    public Client checkClientID(String id) {
        for(Client client : clients) {
            if(client != null && client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    public boolean deposit(String id, String password, int accountID, int amount) {
        //TODO: Problem 1.1
        //checking which client the id belongs to
        if (checkClientID(id) != null){
            Client client1 = checkClientID(id);
            //check pw authentication of that client
            Boolean p = client1.authenticate(password);
            if(p){
                client1.expireAuthenticatedState();
                //if pw authenticated, check if client has a bankAcc matching the accountID
                for(BankAccount acc : client1.getBankAccounts()) {
                    if(acc != null){
                        if(acc.getAccountId() == accountID) {
                            acc.deposit(amount);
                        } else {return false;}
                    }

                }
            }
        } else {return false;}
        return true;
    }

    public boolean withdraw(String id, String password, int accountID, int amount) {
        //TODO: Problem 1.1
        if (checkClientID(id) != null){
            Client client1 = checkClientID(id);
            //check pw authentication of that client
            Boolean p = client1.authenticate(password);
            if(p){
                client1.expireAuthenticatedState();
                //if pw authenticated, check if client has a bankAcc matching the accountID
                for(BankAccount acc : client1.getBankAccounts()) {
                    if( acc != null && acc.getAccountId() == accountID) {
                        acc.withdraw(amount, client1.getMembership());
                    } else {return false;}
                }
            }
        } else {return false;}
        return true;
    }

    public boolean transfer(String sourceId, String password, int sourceAccountID, String targetId, int targetAccountID, int amount) {
        //TODO: Problem 1.1
        if (checkClientID(sourceId) != null && checkClientID(targetId) != null){
            Client sclient = checkClientID(sourceId);
            Client tclient = checkClientID(targetId);
            BankAccount sacc = find(sourceId, sourceAccountID);
            BankAccount tacc = find(targetId, targetAccountID);

            //run program if sourceId and targetId are valid and exists
            if(sacc == null || tacc == null) {return false;}
            //then run program if source client pw is authenticated
            if(sclient.authenticate(password)) {
                sclient.expireAuthenticatedState();
                //transaction
                Boolean p = sacc.send(amount, sclient.getMembership());
                if(p) {
                    tacc.receive(amount);
                    return true;
                } else {return false;}
            } else {return false;}
        } else {return false;}
    }

    public Event[] getEvents(String id, String password, int accountID) {
        //TODO: Problem 1.1
        Client client1 = checkClientID(id);
        BankAccount acc = find(id, accountID);

        if(client1.authenticate(password) || acc != null) {
            client1.expireAuthenticatedState();
            return acc.getEvents();
        } else {return null;}
    }

    public int getBalance(String id, String password, int accountID) {
        //TODO: Problem 1.1
        //pw authenticate
        Client client1 = checkClientID(id);
        if(client1 != null && client1.authenticate(password)) {
            client1.expireAuthenticatedState();
            //see if accountID exists in that Client's bankAccount list
            for(BankAccount acc : client1.getBankAccounts()) {
                if (acc != null && acc.getAccountId() == accountID) {return acc.getBalance();}
            }
        } else {return -1;}
        return -1; //return -1 if pw doesnt' work as well?
    }

    public Client getClientbyID(String id) {
        for (int i = 0; i < numClients; i++)
            if (clients[i].getId().equals(id)) return clients[i];
        return null;
    }

    private static String randomUniqueStringGen(){
        return Encryptor.randomUniqueStringGen();
    }
    private BankAccount find(String id, int accountID) {

        Client client = getClientbyID(id);
        BankAccount clientAccount = client.findAccount(accountID);

        return clientAccount;
    }

    final static int maxSessionKey = 100;
    int numSessionKey = 0;
    String[] sessionKeyArr = new String[maxSessionKey];
    Client[] bankClientmap = new Client[maxSessionKey];
    BankAccount[] bankAccountmap = new BankAccount[maxSessionKey];

    String generateSessionKey(String id, String password, int accountID){
        Client client = getClientbyID(id);

        if(client == null || !client.authenticate(password) || client.findAccount(accountID)==null){
            return null;
        }

        String sessionkey = randomUniqueStringGen();
        sessionKeyArr[numSessionKey] = sessionkey;
        bankClientmap[numSessionKey] = client;
        bankAccountmap[numSessionKey] = client.findAccount(accountID);
        numSessionKey += 1;
        return sessionkey;
    }

    Client getClient(String sessionkey){
        for(int i=0; i < numSessionKey; i++){
            if(sessionKeyArr[i] != null && sessionKeyArr[i].equals(sessionkey)) {
                return bankClientmap[i];
            }
        }
        return null;
    }

    BankAccount getAccount(String sessionkey){
        for(int i = 0 ;i < numSessionKey; i++){
            if(sessionKeyArr[i] != null && sessionKeyArr[i].equals(sessionkey)){
                return bankAccountmap[i];
            }
        }
        return null;
    }

    boolean deposit(String sessionkey, int amount) {
        //TODO: Problem 1.2
        if(sessionkey == null){return false;}
        BankAccount bankAccS = getAccount(sessionkey);
        bankAccS.deposit(amount);
        return true;
    }

    boolean withdraw(String sessionkey, int amount) {
        //TODO: Problem 1.2
        if(sessionkey == null || getClient(sessionkey) == null){return false;}
        BankAccount bankAccS = getAccount(sessionkey);
        String membershipS = getClient(sessionkey).getMembership();
        if(bankAccS.getBalance() < amount) {return false;}
        bankAccS.withdraw(amount, membershipS);
        return true;
    }

    boolean transfer(String sessionkey, String targetId, int targetAccountID, int amount) {
        //TODO: Problem 1.2
        if(sessionkey == null || getClient(sessionkey) == null) {return false;}
        BankAccount bankAccS = this.getAccount(sessionkey);
        String membershipS = getClient(sessionkey).getMembership();
        //checking if balance is enough and targetId exists
        if(bankAccS.getBalance() < amount || checkClientID(targetId) == null) {return false;}
        //checking if targetAccountID exists
        Client tclient = this.checkClientID(targetId);
        for(BankAccount b: tclient.getBankAccounts()) {
            if(b != null && b.getAccountId() == targetAccountID) {
                bankAccS.send(amount, membershipS);
                b.receive(amount);
                return true;
            }
        }
        return false;
    }

    private BankSecretKey secretKey;

    private BankSymmetricKey bankSymmetricKey;
    public BankPublicKey getPublicKey(){
        BankKeyPair keypair = Encryptor.publicKeyGen(); // generates two keys : BankPublicKey, BankSecretKey
        secretKey = keypair.deckey; // stores BankSecretKey internally
        return keypair.enckey;
    }

    int maxHandshakes = 10000;
    int numSymmetrickeys = 0;
    BankSymmetricKey[] bankSymmetricKeys = new BankSymmetricKey[maxHandshakes];
    String[] AppIds = new String[maxHandshakes];

    public int getAppIdIndex(String AppId){
        for(int i=0; i<numSymmetrickeys; i++){
            if(AppIds[i].equals(AppId)){
                return i;
            }
        }
        return -1;
    }

    String prevAppId;
    int c = 0; //counter of how many times fetchSymKey is called
    public void fetchSymKey(Encrypted<BankSymmetricKey> encryptedKey, String AppId){
        //TODO: Problem 1.3
        if(encryptedKey == null) {return;}
        BankSymmetricKey decBsymk = encryptedKey.decrypt(secretKey);
        if(decBsymk == null) {return;}
        if(getAppIdIndex(AppId) > -1) {
            bankSymmetricKeys[getAppIdIndex(AppId)] = decBsymk;
        } else {
            AppIds[numSymmetrickeys] = AppId;
            bankSymmetricKeys[numSymmetrickeys] = decBsymk;
            numSymmetrickeys ++;
        }
    }

    public Encrypted<Boolean> processRequest(Encrypted<Message> messageEnc, String AppId) {
        //TODO: Problem 1.3
        int appIndex = getAppIdIndex(AppId);
        if(appIndex == -1) {return null;}
        boolean p = false;
        if(bankSymmetricKeys[getAppIdIndex(AppId)] == null) {return null;}
        BankSymmetricKey k = bankSymmetricKeys[getAppIdIndex(AppId)];
        if(messageEnc == null || messageEnc.decrypt(k) == null) {return null;}
        Message rMsg = messageEnc.decrypt(k);
        if(rMsg == null) {return null;}
        if(rMsg.getRequestType().equals("deposit")) {
            p = this.deposit(rMsg.getId(), rMsg.getPassword(), rMsg.getAccountID(), rMsg.getAmount());
        }
        if(rMsg.getRequestType().equals("withdraw")) {
            p = this.withdraw(rMsg.getId(), rMsg.getPassword(), rMsg.getAccountID(), rMsg.getAmount());
        }
        Encrypted<Boolean> response = new Encrypted<Boolean>(p, k);
        return response;
    }
}