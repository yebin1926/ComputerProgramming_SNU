import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NFTMarket {

    private Map<Integer, Asset> idAsset = new HashMap<>();
    private Map<String, Buyer> nameBuyer = new HashMap<>();
    public Map<Integer, Asset> getIdAsset() {return idAsset;}
    public Map<String, Buyer> getNameBuyer() {return nameBuyer;}

    public Buyer master;

    public NFTMarket(Buyer master) {
        this.master = master;
        nameBuyer.put("Master", master);
    }

    public boolean addAsset(int id, String item, float price, String artist){
        // TODO sub-problem 1
        if(findAsset(id) != null) {return false;}
        if(id < 0.0f ) {return false;}
        if(price<0.0f || price>100000.0f) {return false;}
        Asset newAs = new Asset(id,item,price,artist,master);
        idAsset.put(id, newAs);
        return true;
    }
    public boolean addBuyer(String buyername){
        // TODO sub-problem 1
        if(buyername == null) {return false;}
        if(buyername.equals("")){return false;}
        if(findBuyer(buyername) != null) {return false;}
        Buyer newBuyer = new Buyer(buyername, 100000);
        nameBuyer.put(buyername, newBuyer);
        return true;
    }
    public Asset findAsset(int id){
        // TODO sub-problem 1
        if(idAsset.containsKey(id)){return idAsset.get(id);}
        return null;
    }
    public Buyer findBuyer(String buyername){
        // TODO sub-problem 1
        if(nameBuyer.containsKey(buyername)) {return nameBuyer.get(buyername);}
        return null;
    }
    public List<Asset> findAssetsWithConditions(int minprice, int maxprice, String item, String artist){
        // TODO sub-problem 2
        List<Asset> filteredAssets = new ArrayList<>();
        boolean priceCheck = false;
        boolean itemCheck = false;
        boolean artistCheck = false;
        for(Asset a : idAsset.values()) {
            //all the different conditions
            if(minprice == -1.0f && maxprice == -1.0f) {priceCheck = true;}
            if(minprice*maxprice < 0.0f) {return null;}
            if(a.getPrice() >= minprice && a.getPrice() <= maxprice) {priceCheck = true;}
            if(item.equals("All") || a.getItem().equals(item)) {itemCheck = true;}
            if(a.getArtist().equals(artist) || artist.equals("All")) {artistCheck = true;}

            // if all conditions satisfy, add the asset into filtered assets list
            if(priceCheck && itemCheck && artistCheck) {
                filteredAssets.add(a);
            }
            priceCheck = false;
            itemCheck = false;
            artistCheck = false;
            Collections.sort(filteredAssets, Asset::compareTo);
        }

        return filteredAssets;

    }

    public boolean trade(Buyer seller, Buyer buyer, int id, float portion){
        // TODO sub-problem 3
        Asset a = findAsset(id);

        if(a == null || seller == null || buyer == null){ return false;}
        if(a.getOwners().isEmpty()){return false;}
        if(seller.findPortfolio(id) == null) {return false;}

        //if client does not have existing asset
        if(buyer.findPortfolio(id) == null){
            //add asset to buyer's list
            buyer.addAsset(findAsset(id), portion);
            //add buyer to asset's owners list
            findAsset(id).getOwners().add(buyer);
            //remove portion from seller's list
            seller.findPortfolio(id).get(id).value -= portion;
        } else {
            //if client does have existing asset
            buyer.findPortfolio(id).get(id).value += portion;
            seller.findPortfolio(id).get(id).value -= portion;
            //do i update it to add/subtract or update it to equal the portion specified
        }
        Pair<Asset, Float> af = buyer.findPortfolio(id).get(id);
        //if seller no longer has portion, seller no longer owns asset
        if(seller.findPortfolio(id).get(id).value <= 0) {
            seller.findPortfolio(id).remove(id);
            a.getOwners().remove(seller);
        }
        //Adjust balances of seller/client
        float amount = af.value*a.getPrice();
        seller.changeBalance(amount);
        buyer.changeBalance(-1*amount);

        return true;
    }

    public void reflectIssues(Asset asset, float effectFactor) {
        // TODO sub-problem 4
        asset.changePrice(effectFactor);
    }

    public void reflectIssues(String artist, float effectFactor) {
        // TODO sub-problem 4
        for(Asset a : idAsset.values()) {
            if(a.getArtist().equals(artist)) {
                a.changePrice(effectFactor);
            }
        }
    }
}
