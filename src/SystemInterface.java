/*
Author: Lachlan Muddle - c3428808, Jacob Saunders - c3412899
Date: 14/03/2024 - 07/06/2024
Task: SENG1110 Programming Assignment 2
*/
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class SystemInterface {

    private SmartCard[] wallet;
    // These are 3 variables that are used in initialising and deleting.
    private Journey[] InvalidJourneys;
    private final Journey InvalidJourney = new Journey();
    private final SmartCard InvalidCard = new SmartCard();
    private boolean running = false;


    private void run() {
        Scanner keyboard = new Scanner(System.in);

        // Sets up the properties for the invalid variables
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
        // Sets uninitialised SmartCards to the InvalidCard by default to avoid null exceptions.
        wallet = new SmartCard[10];
        for (int i = 0; i < 10; i++) {
            wallet[i] = InvalidCard;
        }

        running = true;
        System.out.println("Welcome to the SmartCard system!");
        // Gives the choice to read data from a file, default is no.
        System.out.print("Would you like to import data from an external file? (Y/N) ");
        String choice = keyboardString(keyboard);
        if (choice.equals("y")) {
            System.out.print("What is the name of the file you would like to read from? ");
            String FileName = keyboardString(keyboard);
            FileReader(FileName);
        } else {
            System.out.println("Starting without reading a file.");
        }
        // Sets up the loop to keep the program running until requested to end.
        while (running) {
            FunctionSelector(keyboard);
        }
    }

    // This function allows users to set up SmartCards. It simply looks at thw wallet and find the first Invalid SmartCard and replaces it with the data inputted.
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
            int[] Ids = new int[]{wallet[0].getCardID(), wallet[1].getCardID(), wallet[2].getCardID(), wallet[3].getCardID(), wallet[4].getCardID(), wallet[5].getCardID(), wallet[6].getCardID(), wallet[7].getCardID(), wallet[8].getCardID(), wallet[9].getCardID()};

            SmartCard card = new SmartCard();
            int CardID = 0;
            char type = 'n';
            double balance = 0;
            while (CardID == 0 || IDChecker(CardID, Ids)) {
                System.out.print("Card ID: ");
                CardID = keyboardInt(keyboard);
                if (CardID == 0) {
                    System.out.println("Invalid Card ID.");
                }
                if (IDChecker(CardID, Ids)) {
                    System.out.println("SmartCard already exists, generating random ID.");
                    CardID = (int) (Math.random()*10000) + 1;
                }
            }
            while (type != 'c' && type != 'a' && type != 's') {
                System.out.print("SmartCard type: ");
                type = keyboardChar(keyboard);
                if (type != 'c' && type != 'a' && type != 's') {
                    System.out.println("Invalid type, correct types are: C, A and S.");
                }
            }
            while (balance < 5) {
                System.out.print("Balance: ");
                balance = keyboardDouble(keyboard);
                if (balance < 5) {
                    System.out.println("Please input a value above 5.");
                }
            }
            card.setSmartCardID(CardID);
            card.setType(type);
            card.setBalance(balance);
            card.setJourneys(new Journey[]{InvalidJourney, InvalidJourney, InvalidJourney});
            wallet[count] = JourneySetter(card, keyboard);
        }
    }

    /*This function allows users to set up Journeys on SmartCards. It contains a small amount of logic that is not
    required from the specifications to allow for Journeys to be replaced if they have been deleted from a SmartCard,
    however it does not affect how it is meant to be used from the assignment.*/
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
                while (journeyID < 1 || JourneyIDChecker(journeyID, journeys)) {
                    System.out.print("Journey ID: ");
                    journeyID = keyboardInt(keyboard);
                    if (journeyID < 1) {
                        System.out.println("Please input a unique ID greater than 0.");
                    }
                    if (JourneyIDChecker(journeyID, journeys)) {
                        System.out.println("Journey with this ID already exists, generating a random ID.");
                        journeyID = (int) (Math.random()*10000) + 1;
                    }
                }
                while (!transportMode.equals("train") && !transportMode.equals("tram") && !transportMode.equals("bus")) {
                    System.out.print("Transport mode: ");
                    transportMode = keyboardString(keyboard);
                    if (!transportMode.equals("train") && !transportMode.equals("tram") && !transportMode.equals("bus")) {
                        System.out.println("Please input a valid transport mode (train, tram or bus).");
                        transportMode = "";
                    }
                }
                while (startOfJourney < 1 || startOfJourney > 10) {
                    System.out.print("Starting point for journey (1-10): ");
                    startOfJourney = keyboardInt(keyboard);
                    if (startOfJourney < 1 || startOfJourney > 10) {
                        System.out.println("Please input a valid station/stop.");
                    }
                }
                while (endOfJourney < 1 || endOfJourney > 10 || endOfJourney == startOfJourney) {
                    System.out.print("Ending point for journey (1-10): ");
                    endOfJourney = keyboardInt(keyboard);
                    if (endOfJourney < 1 || endOfJourney > 10) {
                        System.out.println("Please input a valid station/stop.");
                    }
                    if (endOfJourney == startOfJourney) {
                        System.out.println("Please input a different station/stop. Cannot start and stop at the same station.");
                    }
                }
                journey.setJourneyID(journeyID);
                journey.setTransportMode(transportMode);
                journey.setStartOfJourney(startOfJourney);
                journey.setEndOfJourney(endOfJourney);
                journey.setDistanceOfJourney();
                if (JourneyChecker(journey, card)) {
                    System.out.println("This journey already exists.");
                } else {
                    journeys[i] = journey;
                }
            }
        }
        card.setJourneys(journeys);
        return card;
    }

    // This function asks the user what card they with to delete via the cardID. When a valid cardID is chosen, the SmartCard with that cardID is set to the InvalidCard.
    private void CardDeleter(Scanner keyboard) {
        System.out.print("The available SmartCard's are: ");
        for (SmartCard card : wallet) {
            if (card.getCardID() != 0) {
                System.out.print(card.getCardID() + ", ");
            }
        }
        System.out.print("which would you like to delete? ");
        int deleted = keyboardInt(keyboard);
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

    // This function asks the user what Journey they would like to delete off of a selected SmartCard using the journeyID, similar to above.
    private SmartCard JourneyDeleter(SmartCard card, Scanner keyboard) {
        System.out.print("The journey's on this card are: ");
        for (Journey journey : card.getJourneys()) {
            if (journey.getJourneyID() != 0) {
                System.out.print(journey.getJourneyID() + ", ");
            }
        }
        System.out.print("which journey would you like to delete?");
        int deletion = keyboardInt(keyboard);
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

    // This function lists the cards that are set with the number of journeys set on them. It then lists the Journeys.
    private void CardLister() {
        for (SmartCard card : wallet) {
            if (card != InvalidCard) {
                int count = 0;
                for (Journey journey : card.getJourneys()) {
                    if (journey != InvalidJourney) {
                        count++;
                    }
                }
                System.out.println("SmartCard " + card.getCardID() + " has type " + card.getType() + " and " + count + " journey(s).");
                for (Journey journey : card.getJourneys()) {
                    if (journey != InvalidJourney) {
                        System.out.println("    Journey " + journey.getJourneyID() + " has transport mode " + journey.getTransportMode() + ".");
                    }
                }
            }
        }
    }

    // This function simply compares every Journey to the InvalidJourney, if it is not the InvalidJourney, it is displayed.
    private void JourneyLister() {
        for (SmartCard card : wallet) {
            for (Journey journey : card.getJourneys()) {
                if (journey != InvalidJourney) {
                    System.out.println("Journey " + journey.getJourneyID() + " has transport mode " + journey.getTransportMode() + " starting from " + journey.getStartOfJourney() + " and ending at " + journey.getEndOfJourney() + " with journey distance of " + journey.getDistanceOfJourney() + " station(s)/stop(s).");
                }
            }
        }
    }

    // This function simply checks the transportMode of all the journeys, if it is the inputted String, it is displayed.
    private void TransportModeFinder(String mode) {
        for (SmartCard card : wallet) {
            for (Journey journey : card.getJourneys()) {
                if (journey.getTransportMode().equals(mode)) {
                    System.out.println(" Journey " + journey.getJourneyID() + " has that transport mode, and it belongs to SmartCard " + card.getCardID() + ".");
                }
            }
        }
    }

    /* This function calculates all the fares. There is a lot of logic in this to ensure that only valid Journeys are
    displayed and that each of the transportModes are grouped together and calculated. Simply as it is requested via
    the description provided.*/
    private void FareCalculator() {
        double[][] Prices = new double[3][10];
        double multiplicationFactor = 1;
        for (int i = 0; i < 10; i++) {
            if (wallet[i].getType() == 'c') {
                multiplicationFactor = 1.86;
            }
            if (wallet[i].getType() == 'a') {
                multiplicationFactor = 2.24;
            }
            if (wallet[i].getType() == 's') {
                multiplicationFactor = 1.6;
            }
            for (int j = 0; j < 3; j++) {
                if (wallet[i].getJourneys()[j].getTransportMode().equals("train")) {
                    Prices[0][i] += multiplicationFactor * wallet[i].getJourneys()[j].getDistanceOfJourney();
                }
                if (wallet[i].getJourneys()[j].getTransportMode().equals("bus")) {
                    Prices[1][i] += multiplicationFactor * wallet[i].getJourneys()[j].getDistanceOfJourney();
                }
                if (wallet[i].getJourneys()[j].getTransportMode().equals("tram")) {
                    Prices[2][i] += multiplicationFactor * wallet[i].getJourneys()[j].getDistanceOfJourney();
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (Prices[i][j] != 0) {
                    Prices[i][j] += 1.5;
                }
            }
        }

        System.out.println("Total transport mode journeys cost/fare:");
        System.out.println("---------------------------------------------------------");
        double TrainTotal = 0;
        double BusTotal = 0;
        double TramTotal = 0;
        for (int i = 0; i < 10; i++) {
            TrainTotal += Prices[0][i];
            BusTotal += Prices[1][i];
            TramTotal += Prices[2][i];
        }
        if (TrainTotal != 0) {
            System.out.print("Total cost of train journeys is $");
            System.out.printf("%.2f", TrainTotal);
            System.out.println();
        }
        if (BusTotal != 0) {
            System.out.print("Total cost of bus journeys is $");
            System.out.printf("%.2f", BusTotal);
            System.out.println();
        }
        if (TramTotal != 0) {
            System.out.print("Total cost of tram journeys is $");
            System.out.printf("%.2f", TramTotal);
            System.out.println();
        }
        System.out.println("---------------------------------------------------------");
        System.out.println();
        System.out.println("Breakdown by SmartCard:");
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < 10; i++) {
            if (wallet[i] != InvalidCard) {
                System.out.println("SmartCard " + wallet[i].getCardID());
                if (Prices[0][i] != 0) {
                    System.out.print("    Total cost of train journeys is $");
                    System.out.printf("%.2f", Prices[0][i]);
                    System.out.println();
                }
                if (Prices[1][i] != 0) {
                    System.out.print("    Total cost of bus journeys is $");
                    System.out.printf("%.2f", Prices[1][i]);
                    System.out.println();
                }
                if (Prices[2][i] != 0) {
                    System.out.print("    Total cost of tram journeys is $");
                    System.out.printf("%.2f", Prices[2][i]);
                    System.out.println();
                }
            }
        }
        System.out.println("---------------------------------------------------------");
    }

    // This function reads the file with the specified name when the program is started. It is not too complicated and explains itself better than a description up here would.
    private void FileReader(String FileName) {
        boolean cardMode = true;

        SmartCard card = new SmartCard();
        int cardCount = -1;
        int CardID = 0;
        char type = 'n';
        double balance = 0;

        Journey journey = new Journey();
        int JourneyCount = -1;
        int JourneyID = 0;
        String mode = "NaN";
        int start = 0;
        int end = 0;
        // This creates a matrix of values to avoid memory reference issues.
        Journey[][] journeys = new Journey[10][3];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                journeys[i][j] = InvalidJourney;
            }
        }

        try {
            Scanner inputStream = new Scanner(new File(FileName));
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();
                if (line.contains("SmartCard")) {
                    cardMode = true;
                    if (cardCount > -1) {
                        card.setSmartCardID(CardID);
                        card.setType(type);
                        card.setBalance(balance);
                        wallet[cardCount] = card;
                    }
                    JourneyCount = -1;
                    cardCount++;
                    card = new SmartCard();
                }
                if (line.contains("Journeys")) {
                    cardMode = false;
                }
                if (cardMode) {
                    if (line.contains("ID")) {
                        String[] ID = line.split(" ");
                        CardID = Integer.parseInt(ID[1]);
                    }
                    if (line.contains("Type")) {
                        String[] Type = line.split(" ");
                        type = Type[1].toLowerCase().charAt(0);
                    }
                    if (line.contains("Balance")) {
                        String[] Balance = line.split(" ");
                        balance = Double.parseDouble(Balance[1]);
                    }
                } else {
                    if (line.contains("ID")) {
                        JourneyCount++;
                        journey = new Journey();
                        String[] ID = line.split(" ");
                        JourneyID = Integer.parseInt(ID[1]);
                    }
                    if (line.contains("Mode")) {
                        String[] Mode = line.split(" ");
                        mode = Mode[1].toLowerCase();
                    }
                    if (line.contains("Start")) {
                        String[] Start = line.split(" ");
                        start = Integer.parseInt(Start[1]);
                    }
                    if (line.contains("End")) {
                        String[] End = line.split(" ");
                        end = Integer.parseInt(End[1]);
                    }
                    // Since distanceOfJourney is not a variable you input, when the word distance comes up we simply set the values for the journey and continue.
                    if (line.contains("Distance")) {
                        journey.setJourneyID(JourneyID);
                        journey.setTransportMode(mode);
                        journey.setStartOfJourney(start);
                        journey.setEndOfJourney(end);
                        journey.setDistanceOfJourney();
                        journeys[cardCount][JourneyCount] = journey;
                    }
                }
            }
            card.setSmartCardID(CardID);
            card.setType(type);
            card.setBalance(balance);
            if (cardCount > -1) {
                wallet[cardCount] = card;
            }
            inputStream.close();
            System.out.println("File read successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        for (int i = 0; i < 10; i++) {
            wallet[i].setJourneys(journeys[i]);
        }
    }

    // This function simply writes a file when the program is terminated saving the data. It is incredibly simple, simply running through the wallet.
    private void FileSaver() {
        String fileName = "TravelStats.txt";
        PrintWriter outputStream;
        try {
            outputStream = new PrintWriter(fileName);
            for (SmartCard card : wallet) {
                if (card != InvalidCard) {
                    outputStream.println("SmartCard");
                    outputStream.println("ID " + card.getCardID());
                    outputStream.println("Type " + card.getType());
                    outputStream.println("Balance " + card.getBalance());
                    outputStream.println();
                    outputStream.println("Journeys");
                    outputStream.println();
                    if (card.getJourneys() != InvalidJourneys) {
                        for (Journey journey : card.getJourneys()) {
                            if (journey != InvalidJourney) {
                                outputStream.println("ID " + journey.getJourneyID());
                                outputStream.println("Mode " + journey.getTransportMode());
                                outputStream.println("Start " + journey.getStartOfJourney());
                                outputStream.println("End " + journey.getEndOfJourney());
                                outputStream.println("Distance " + journey.getDistanceOfJourney());
                                outputStream.println();
                            }
                        }
                    }
                }
            }
            outputStream.close();
        } catch (Exception e) {
            System.out.println("A");
        }
    }

    // A simple check to see if a cardID has already been used.
    private boolean IDChecker(int CardID, int[] Ids) {
        for (int card : Ids) {
            if (card == CardID) {
                return true;
            }
        }
        return false;
    }

    // A simple check to see if a Journey is unique on a SmartCard.
    private boolean JourneyChecker(Journey journey, SmartCard card) {
        Journey[] journeys = card.getJourneys();
        for (Journey jour : journeys) {
            if (jour.getDistanceOfJourney() == journey.getDistanceOfJourney() && jour.getTransportMode().equals(journey.getTransportMode()) && jour.getEndOfJourney() == journey.getEndOfJourney() && jour.getStartOfJourney() == journey.getStartOfJourney()) {
                return true;
            }
        }
        return false;
    }

    // A simple check to see if a JourneyID has already been used on a SmartCard.
    private boolean JourneyIDChecker(int JourneyID, Journey[] journeys) {
        for (Journey journey : journeys) {
            if (JourneyID == journey.getJourneyID() && JourneyID != 0) {
                return true;
            }
        }
        return false;
    }

    // These 4 functions save space in functions, getting rid of a lot of try-catch statements.
    private int keyboardInt(Scanner keyboard) {
        try {
            return keyboard.nextInt();
        } catch (Exception e) {
            keyboard.next();
            return 0;
        }
    }

    private double keyboardDouble(Scanner keyboard) {
        try {
            return keyboard.nextDouble();
        } catch (Exception e) {
            keyboard.next();
            return 0;
        }
    }

    private String keyboardString(Scanner keyboard) {
        try {
            return keyboard.next().toLowerCase();
        } catch (Exception e) {
            keyboard.next();
            return "";
        }
    }

    private char keyboardChar(Scanner keyboard) {
        try {
            return keyboard.next().toLowerCase().charAt(0);
        } catch (Exception e) {
            keyboard.next();
            return 'n';
        }
    }

    /* This function was not asked for, nor was there any indication on how you wanted the function to work, so this
    is the way in which the program is interacted with. There is a switch case that runs all the options and allows
    users to choose what they wish to do.*/
    private void FunctionSelector(Scanner keyboard) {
        String mode;
        int cardCount = 0;
        int JourneySelectorVariable = 0;
        int[] IDs = new int[10];
        for (int i = 0; i < 10; i++) {
            if (wallet[i] != InvalidCard) {
                cardCount++;
            }
            IDs[i] = wallet[i].getCardID();
        }
        int Index = 0;
        System.out.println("Options:");
        System.out.println("(1) Set SmartCards");
        System.out.println("(2) Set Journeys");
        System.out.println("(3) Delete SmartCard");
        System.out.println("(4) Delete Journey");
        System.out.println("(5) List SmartCards");
        System.out.println("(6) List Journeys");
        System.out.println("(7) Find Journeys with specific transport type");
        System.out.println("(8) Calculate Fares");
        System.out.println("(9) Exit");
        System.out.print("What would you like to do (1-9)? ");
        int choice = keyboardInt(keyboard);
        switch (choice) {
            case 1:
                CardSetter(keyboard);
                break;
            case 2:
                if (cardCount == 0) {
                    System.out.println("There are no SmartCards to set Journeys on.");
                } else {
                    while (JourneySelectorVariable < 1 && IDChecker(JourneySelectorVariable, IDs)) {
                        System.out.print("The available SmartCards to set Journeys on are: ");
                        for (SmartCard card : wallet) {
                            if (card != InvalidCard) {
                                System.out.print(card.getCardID() + ", ");
                            }
                        }
                        JourneySelectorVariable = keyboardInt(keyboard);
                        if (JourneySelectorVariable < 1 && IDChecker(JourneySelectorVariable, IDs)) {
                            System.out.println("Please input a valid value.");
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        if (JourneySelectorVariable == IDs[i]) {
                            break;
                        }
                        Index++;
                    }
                    wallet[Index] = JourneySetter(wallet[Index], keyboard);
                }
                break;
            case 3:
                CardDeleter(keyboard);
                break;
            case 4:
                if (cardCount == 0) {
                    System.out.println("There are no SmartCards to delete Journeys from.");
                } else {
                    while (JourneySelectorVariable < 1 && IDChecker(JourneySelectorVariable, IDs)) {
                        System.out.print("The available SmartCards to delete Journeys from are: ");
                        for (SmartCard card : wallet) {
                            if (card != InvalidCard) {
                                System.out.print(card.getCardID() + ", ");
                            }
                        }
                        JourneySelectorVariable = keyboardInt(keyboard);
                        if (JourneySelectorVariable < 1 && IDChecker(JourneySelectorVariable, IDs)) {
                            System.out.println("Please input a valid value.");
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        if (JourneySelectorVariable == IDs[i]) {
                            break;
                        }
                        Index++;
                    }
                    wallet[Index] = JourneyDeleter(wallet[Index], keyboard);
                }
                break;
            case 5:
                CardLister();
                break;
            case 6:
                JourneyLister();
                break;
            case 7:
                System.out.print("What transport mode would you like to know about? ");
                mode = keyboardString(keyboard);
                TransportModeFinder(mode);
                break;
            case 8:
                FareCalculator();
                break;
            case 9:
                FileSaver();
                running = false;
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Not a valid input.");
                break;
        }
    }

    public static void main(String[] args) {
        SystemInterface systemUI = new SystemInterface();
        systemUI.run();
    }
}