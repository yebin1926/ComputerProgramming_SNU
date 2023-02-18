package hand.agent;

public class Buyer extends Agent{
    public Buyer(double priceLimit) {
        super(priceLimit);
    }

    @Override
    public boolean willTransact(double price) {
        //TODO: Problem 2.1
        if(!this.hadTransaction && price <= this.expectedPrice) {return true;}
        return false;
    }

    @Override
    public void reflect() {
        //TODO: Problem 2.1
        if(this.hadTransaction) {
            this.expectedPrice -= this.adjustment;
            if((this.adjustment +5) <= this.adjustmentLimit) {this.adjustment += 5;}
            else {this.adjustment = this.adjustmentLimit;}
        } else {
            if(this.expectedPrice+this.adjustment <= this.priceLimit) {
                this.expectedPrice += this.adjustment;
                if((this.adjustment - 5) >= 0) {this.adjustment -= 5;}
                else {this.adjustment = 0;}
            } else {this.expectedPrice = this.priceLimit;}
        }
        this.hadTransaction = false;
    }
}
