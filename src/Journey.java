/*
Author: Lachlan Muddle - c3428808, Jacob Saunders - c3412899
Date: 14/03/2024 - 07/06/2024
Task: SENG1110 Programming Assignment 2
Journey class handles simply getting and setting the values given from the SystemInterface
*/
public class Journey {

    // Sets up the private variables.
    private int journeyID;
    private String transportMode;
    private int startOfJourney;
    private int endOfJourney;
    private int distanceOfJourney;

    // Getters and setters.
    public void setJourneyID(int journeyID) {
        this.journeyID = journeyID;
    }

    public int getJourneyID() {
        return journeyID;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setStartOfJourney(int startOfJourney) {
        this.startOfJourney = startOfJourney;
    }

    public int getStartOfJourney() {
        return startOfJourney;
    }

    public void setEndOfJourney(int endOfJourney) {
        this.endOfJourney = endOfJourney;
    }

    public int getEndOfJourney() {
        return endOfJourney;
    }

    // Calculates the distance from the value for start and end, always returns a positive number.
    public void setDistanceOfJourney() {
        if (endOfJourney > startOfJourney) {
            this.distanceOfJourney = endOfJourney - startOfJourney;
        } else {
            this.distanceOfJourney = startOfJourney - endOfJourney;
        }
    }

    public int getDistanceOfJourney() {
        return distanceOfJourney;
    }
}