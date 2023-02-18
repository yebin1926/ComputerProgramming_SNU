package hand.market;

import hand.agent.Buyer;
import hand.agent.Seller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Pair<K, V>{
    public K key;
    public V value;
    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class Market {
    public ArrayList<Buyer> buyers;
    public ArrayList<Seller> sellers;

    public Market(int nb, ArrayList<Double> fb, int ns, ArrayList<Double> fs) {
        buyers = createBuyers(nb, fb);
        sellers = createSellers(ns, fs);
    }

    private ArrayList<Pair<Seller, Buyer>> matchedPairs(int day, int round) {
        if(buyers==null || sellers==null) return null;
        ArrayList<Seller> shuffledSellers = new ArrayList<>(sellers);
        ArrayList<Buyer> shuffledBuyers = new ArrayList<>(buyers);
        Collections.shuffle(shuffledSellers, new Random(71 * day + 43 * round + 7));
        Collections.shuffle(shuffledBuyers, new Random(67 * day + 29 * round + 11));
        ArrayList<Pair<Seller, Buyer>> pairs = new ArrayList<>();
        for (int i = 0; i < shuffledBuyers.size(); i++) {
            if (i < shuffledSellers.size()) {
                pairs.add(new Pair<>(shuffledSellers.get(i), shuffledBuyers.get(i)));
            }
        }
        return pairs;
    }

    public double simulate() {
        //TODO: Problem 2.2 and 2.3
        double sum = 0.0;
        int numTrans = 0;
        for (int day = 1; day <= 2000; day++) { // do not change this line
            //for each day, do:
            for (int round = 1; round <= 10; round++) { // do not change this line
                //for each round, do:
                ArrayList<Pair<Seller, Buyer>> pairs = matchedPairs(day, round); // do not change this line
                if (pairs == null) {return -1;}
                for(int i=0; i< pairs.size(); i++) {
                    //for each matched pair, do:
                    Seller seller1 = pairs.get(i).key;
                    Buyer buyer1 = pairs.get(i).value;
                    double price = seller1.getExpectedPrice();
                    if(buyer1.willTransact(price) && seller1.willTransact(price)) {
                        seller1.makeTransaction();
                        buyer1.makeTransaction();
                        //calculate average
                        if(day == 2000) {
                            numTrans ++;
                            sum += seller1.getExpectedPrice();
                        }
                    }
                }
            }
            //reflection
            for(Seller seller1: sellers) {seller1.reflect();}
            for(Buyer buyer1: buyers) {buyer1.reflect();}
        }
        return sum/numTrans;
    }

    private ArrayList<Buyer> createBuyers(int n, ArrayList<Double> f) {
        //TODO: Problem 2.3
        ArrayList<Buyer> nBuyers = new ArrayList<Buyer>();
        if(f==null){return null;}
        for(int i=1; i<=n; i++) {
            double pl = 0.0;
            double x = (double) i/n;
            for(int k=0; k<f.size(); k++) {
                double term = f.get(k)*(Math.pow(x, k));
                pl += term;
            }
            Buyer b = new Buyer(pl);
            nBuyers.add(b);
        }
        return nBuyers;
    }


    private ArrayList<Seller> createSellers(int n, ArrayList<Double> f) {
        //TODO: Problem 2.3
        if(f==null){return null;}
        ArrayList<Seller> nSellers = new ArrayList<Seller>();
        for(int i=1; i<=n; i++) {
            double pl = 0.0;
            double x = (double) i/n;
            for(int k=0; k<f.size(); k++) {
                double term = f.get(k)*(Math.pow(x, k));
                pl += term;
            }
            Seller s = new Seller(pl);
            nSellers.add(s);
        }
        return nSellers;
    }
}
