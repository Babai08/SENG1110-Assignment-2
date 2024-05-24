public class SmartCard {

    private int cardID;
    private char type;
    private double balance;
    private Journey[] journeys;

    public void setSmartCardID(int cardID) {
        this.cardID = cardID;
    }

    public int getCardID() {
        return cardID;
    }

    public void setType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setJourneys(Journey[] journeys) {
        this.journeys = journeys;
    }

    public Journey[] getJourneys() {
        return journeys;
    }
}
