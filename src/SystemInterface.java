
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
            int Ids[] = new int[10];
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
        int journeyID = 0;
        if (card.getType() == 'c') {
            if (card.getJourneys()[0] == InvalidJourney) {
                System.out.println("You can set 1 journey on this card.");
                while (journeyID < 1) {
                    System.out.print("Journey ID: ");
                    journeyID = keyboard.nextInt();
                    if (journeyID < 1) {
                        System.out.println("Please input a unique ID greater than 0.");
                    }
                }
            }
        }
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

    private boolean IDChecker(int CardID, int[] Ids) {
        for (int card : Ids) {
            if (card == CardID) {
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