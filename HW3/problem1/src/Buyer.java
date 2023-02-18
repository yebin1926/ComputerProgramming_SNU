import java.util.*;

public class Buyer {
    private String buyername;
    private float balance;
    private Map<Integer, Pair<Asset, Float>> portfolio;

    public Buyer(String buyername) {
        // initial balance is 100000 for each buyer
        this(buyername, 100000);
    }
    public Buyer(String buyername, float balance){
        this.balance = balance;
        this.buyername = buyername;
        portfolio = new HashMap<Integer, Pair<Asset, Float>>();
    }
    public float getBalance(){return balance;}

    public void changeBalance(float amount) {
        this.balance += amount;
    }

    // TODO sub-problem 1~4
    public void addAsset(Asset asset, float portion) {
        portfolio.put(asset.getId(), new Pair<>(asset, portion));
    }

    public float getTotalValue() {
        float total = 0;
        for( Pair<Asset, Float> a : portfolio.values()) {
            total += (a.key.getPrice()*a.value);
        }
        return total+balance;
    }

    @Override
    public String toString() {
        return buyername;
    }


    public Pair<Asset, Float> getAssetPortion(int id) {
        //made
        Pair<Asset, Float> af = portfolio.get(id);
        return af;
    }
    public Map<Integer, Pair<Asset, Float>> findPortfolio(int id) {
        //made
        if(portfolio.containsKey(id)) {return portfolio;}
        return null;
    }
    public Map<Integer, Pair<Asset, Float>> findPortfolio() {
        //made
        return portfolio;
    }

}