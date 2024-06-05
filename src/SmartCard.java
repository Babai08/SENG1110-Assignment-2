/*
Author: Lachlan Muddle - c3428808, Jacob Saunders - c3412899
Date: 14/03/2024 - 07/06/2024
Task: SENG1110 Programming Assignment 2
SmartCard class handles simply getting and setting the values given from the SystemInterface
*/
public class SmartCard {

    // Sets up the private variables, journeys as an array instead of being variables by themselves.
    private int cardID;
    private char type;
    private double balance;
    private Journey[] journeys;

    // Getters and setters.
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
