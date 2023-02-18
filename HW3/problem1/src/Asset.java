import java.util.*;

public class Asset implements Comparable<Asset>{
    private int id;
    private String item;
    private float price;
    private String artist;
    private List<Buyer> owners;

    public Asset(int id, String item, float price, String artist, Buyer buyer){
        this.id = id;
        this.item = item;
        this.price = price;
        this.artist = artist;
        this.owners = new ArrayList<>();
        this.owners.add(buyer);
        buyer.addAsset(this, 1);
    }

    public List<Buyer> getOwners(){return owners;}
    // TODO sub-problem 1~4

    public float getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "" + id;
    }

    public String getItem() {return item;}

    public String getArtist() {return artist;}

    public int getId() {return id;}

    public void changePrice(float amount) {
        //made
        this.price *= amount;
    }

    @Override
    public int compareTo(Asset asset2) {
        if (this.getId() > asset2.getId()) {
            // if current object is greater,then return 1
            return 1;
        } else if (asset2.getId() > this.getId()) {
            // if current object is greater,then return -1
            return -1;
        } else {return 0;}
    }

}
