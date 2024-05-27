/*
Author: Lachlan Muddle - c3428808, Jacob Saunders - c3412899
Date: 14/03/2024 - 07/06/2024
Task: SENG1110 Programming Assignment 1
*/
import java.util.Scanner;

public class SystemInterface {

    private SmartCard[] wallet;
    private Journey[] InvalidJourneys;
    private final Journey InvalidJourney = new Journey();
    private final SmartCard InvalidCard = new SmartCard();


    private void run() {
        Scanner keyboard = new Scanner(System.in);
        InvalidJourney.setJourneyID(0);
        InvalidJourney.setStartOfJourney(0);
        InvalidJourney.setEndOfJourney(0);
        InvalidJourney.setTransportMode("NaN");
        InvalidJourney.setDistanceOfJourney();
        InvalidJourneys = new Journey[3];
        for (int i = 0; i < 3; i++) {
            InvalidJourneys[i] = InvalidJourney;
        }
        InvalidCard.setJourneys(InvalidJourneys);
        InvalidCard.setType('n');
        InvalidCard.setSmartCardID(0);
        InvalidCard.setBalance(0);
        wallet = new SmartCard[10];
        for (int i = 0; i < 10; i++) {
            wallet[i] = InvalidCard;
        }
        SmartCard LegitCard = new SmartCard();
        LegitCard.setJourneys(InvalidJourneys);
        LegitCard.setBalance(43);
        LegitCard.setSmartCardID(54326);
        LegitCard.setType('a');

        wallet[1] = LegitCard;


        CardSetter(keyboard);
        CardDeleter(keyboard);
    }

    private void CardSetter(Scanner keyboard) {
        int count = 0;
        for (SmartCard card : wallet) {
            if (card.getCardID() == 0) {
                break;
            }
            count++;
        }
        if (count == 10) {
            System.out.println("The maximum amount of SmartCard's has already been reached.");
        } else {
            int[] Ids = new int[10];
            for (int i = 0; i < 1; i++) {
                Ids[i] = wallet[i].getCardID();
            }

            SmartCard card = new SmartCard();
            int CardID = 0;
            char type = 'n';
            double balance = 0;
            while (CardID == 0 || IDChecker(CardID, Ids)) {
                System.out.print("Card ID: ");
                CardID = keyboard.nextInt();
                if (CardID == 0 || IDChecker(CardID, Ids)) {
                    System.out.println("Invalid Card ID.");
                }
            }
            while (type != 'c' && type != 'a' && type != 's') {
                System.out.print("SmartCard type: ");
                type = keyboard.next().toLowerCase().charAt(0);
                if (type != 'c' && type != 'a' && type != 's') {
                    System.out.println("Invalid type, correct types are: C, A and S.");
                }
            }
            while (balance < 5) {
                System.out.print("Balance: ");
                balance = keyboard.nextDouble();
                if (balance < 5) {
                    System.out.println("Please input a value above 5.");
                }
            }
            card.setSmartCardID(CardID);
            card.setType(type);
            card.setBalance(balance);
            card.setJourneys(InvalidJourneys);
            wallet[count] = JourneySetter(card, keyboard);
        }
    }

    private SmartCard JourneySetter(SmartCard card, Scanner keyboard) {
        int maxJourneys = 0;

        Journey[] journeys = card.getJourneys();
        if (card.getType() == 'c') {
            maxJourneys = 1;
        } else if (card.getType() == 'a') {
            maxJourneys = 2;
        } else if (card.getType() == 's') {
            maxJourneys = 3;
        }
        for (int i = 0; i < maxJourneys; i++) {
            while (card.getJourneys()[i] == InvalidJourney) {
                Journey journey = new Journey();
                int journeyID = 0;
                String transportMode = "";
                int startOfJourney = 0;
                int endOfJourney = 0;
                while (journeyID < 1 || JourneyChecker(journey, card)) {
                    System.out.print("Journey ID: ");
                    journeyID = keyboard.nextInt();
                    journey.setJourneyID(journeyID);
                    if (journeyID < 1) {
                        System.out.println("Please input a unique ID greater than 0.");
                    }
                }
                while (!transportMode.equals("train") && !transportMode.equals("tram") && !transportMode.equals("bus")) {
                    System.out.print("Transport mode: ");
                    transportMode = keyboard.next().toLowerCase();
                    if (!transportMode.equals("train") && !transportMode.equals("tram") && !transportMode.equals("bus")) {
                        System.out.println("Please input a valid transport mode (train, tram or bus).");
                    }
                }
                while (startOfJourney < 1 || startOfJourney > 10) {
                    System.out.print("Starting point for journey (1-10): ");
                    startOfJourney = keyboard.nextInt();
                    if (startOfJourney < 1 || startOfJourney > 10) {
                        System.out.println("Please input a valid station/stop.");
                    }
                }
                while (endOfJourney < 1 || endOfJourney > 10 || endOfJourney == startOfJourney) {
                    System.out.print("Ending point for journey (1-10): ");
                    endOfJourney = keyboard.nextInt();
                    if (endOfJourney < 1 || endOfJourney > 10) {
                        System.out.println("Please input a valid station/stop.");
                    }
                    if (endOfJourney == startOfJourney) {
                        System.out.println("Please input a different station/stop. Cannot start and stop at the same station.");
                    }
                }
                journey.setTransportMode(transportMode);
                journey.setStartOfJourney(startOfJourney);
                journey.setEndOfJourney(endOfJourney);
                journey.setDistanceOfJourney();
                if (JourneyChecker(journey, card)) {
                    System.out.println("This journey already exists");
                } else {
                    journeys[i] = journey;
                }
            }
        }
        card.setJourneys(journeys);
        return card;
    }

    private void CardDeleter(Scanner keyboard) {
        System.out.print("The available SmartCard's are: ");
        for (SmartCard card : wallet) {
            if (card.getCardID() != 0) {
                System.out.print(card.getCardID() + ", ");
            }
        }
        System.out.print("which would you like to delete? ");
        int deleted = keyboard.nextInt();
        int count = 0;
        for (SmartCard card : wallet) {
            if (deleted != 0 && card.getCardID() == deleted) {
                break;
            }
            count++;
        }
        if (count == 10) {
            System.out.println("SmartCard not found.");
        } else {
            wallet[count] = InvalidCard;
            System.out.println("SmartCard " + deleted + " has been deleted.");
        }
    }

    private SmartCard JourneyDeleter(SmartCard card, Scanner keyboard) {
        System.out.print("The journey's on this card are: ");
        for (Journey journey : card.getJourneys()) {
            if (journey.getJourneyID() != 0) {
                System.out.print(journey.getJourneyID() + ", ");
            }
        }
        System.out.print("which journey would you like to delete?");
        int deletion = keyboard.nextInt();
        if (deletion == 0) {
            System.out.println("No journeys were deleted.");
            return card;
        }
        int count = 0;
        for (Journey journey : card.getJourneys()) {
            if (journey.getJourneyID() == deletion) {
                Journey[] journeys = card.getJourneys();
                journeys[count] = InvalidJourney;
                System.out.println("Journey with ID " + deletion + "has been deleted.");
                card.setJourneys(journeys);
                return card;
            }
            count++;
        }
        System.out.println("No journeys were deleted");
        return card;
    }

    private void CardLister() {
        for (SmartCard card : wallet) {
            if (card != InvalidCard) {
                int count = 0;
                for (Journey journey : card.getJourneys()) {
                    if (journey != InvalidJourney) {
                        count++;
                    }
                }
                System.out.println("Smartcard " + card.getCardID() + " has type " + card.getType() + " and " + count + "journeys.");
                for (Journey journey : card.getJourneys()) {
                    if (journey != InvalidJourney) {
                        System.out.println("Journey " + journey.getJourneyID() + " has transport mode " + journey.getTransportMode() + ".");
                    }
                }
            }
        }
    }

    private boolean IDChecker(int CardID, int[] Ids) {
        for (int card : Ids) {
            if (card == CardID) {
                return true;
            }
        }
        return false;
    }

    private boolean JourneyChecker(Journey journey, SmartCard card) {
        Journey[] journeys = card.getJourneys();
        for (Journey jour : journeys) {
            if (jour.getJourneyID() == journey.getJourneyID() || (jour.getDistanceOfJourney() == journey.getDistanceOfJourney() && jour.getTransportMode().equals(journey.getTransportMode()) && jour.getEndOfJourney() == journey.getEndOfJourney() && jour.getStartOfJourney() == journey.getStartOfJourney())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SystemInterface systemUI = new SystemInterface();
        systemUI.run();
    }
}