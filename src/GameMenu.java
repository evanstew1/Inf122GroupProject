import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMenu {
    private Scanner scanner = new Scanner(System.in);
    private List<User> userSlots = new ArrayList<>();
    private User currentUser = null;
    private final int MAX_SLOTS = 4;
    private WorldClockTime globalClock = WorldClockTime.getInstance();

    public void start(){
        while (true){
            if (currentUser == null) {
                showLoadScreen();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoadScreen() {
        boolean valid = false;

        System.out.println("\n\n\n\n\n\n\n\n ----- GUILDQUEST: USER SELECTON -----\n");
        for(int i = 0; i < MAX_SLOTS; i++) {
            String UserSlotI = (i < userSlots.size()) ? "User: " + userSlots.get(i).getUserID() : "Empty";
            System.out.println((i + 1) + ". " + UserSlotI);     
        }

        System.out.println("5. Create New User");
        System.out.println("6. Delete a User");
        System.out.println("7. Exit Game");

        while (!valid) {
            try {
                // Read the line and remove any accidental leading/trailing spaces
                String input = scanner.nextLine().trim();

                // If the user just pressed Enter, skip to the catch block manually or handle here
                if (input.isEmpty()) {
                    System.out.println("ERROR: No input detected. Please enter a number.");
                    continue;
                }

                int selection = Integer.parseInt(input);

                // 1. Handle selection of existing User Slots
                if (selection >= 1 && selection <= userSlots.size()) {
                    currentUser = userSlots.get(selection - 1);
                    valid = true;
                } 
                // 2. Create New User
                else if (selection == 5) {
                    if (userSlots.size() < MAX_SLOTS) {
                        User newUser = new User(userSlots.size() + 1);
                        userSlots.add(newUser);
                        System.out.println("User Created");
                    } else {
                        System.out.println("Error: All 4 slots are full");
                    }
                    valid = true;
                } 
                // 3. Delete a User
                else if (selection == 6) {
                    System.out.println("Enter User Slot to delete (1-4):");
                    try {
                        int toDel = Integer.parseInt(scanner.nextLine().trim());
                        if (toDel > 0 && toDel <= userSlots.size()) {
                            userSlots.remove(toDel - 1);
                            System.out.println("User removed.");
                        } else {
                            System.out.println("Invalid slot number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Invalid slot format.");
                    }
                } 
                // 4. Exit Game
                else if (selection == 7) {
                    valid = true;
                    System.out.println("Exiting GuildQuest...");
                    System.exit(0);
                } 
                else {
                    System.out.println("ERROR: Selection out of range. Try again.");
                    waitSeconds(1);
                }

            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid input. Please enter a valid number (1-7).");
            }
        }}

    private void showMainMenu() {
        displayBanner();
        System.out.println("\n\n\n\n\n\n\n\n ----- GUILDQUEST: Main Menu -----\n");
        System.out.println("1. Campaign Menu");
        System.out.println("2. Character Menu");
        System.out.println("3. Settings");
        System.out.println("4. Mini Adventure");
        System.out.println("5. Logout");
        System.out.println("Selection(press # to select): ");
        boolean selected = false;

        int selection = Integer.parseInt(scanner.nextLine());
        switch (selection) {
            case 1 -> new CampaignMenu(scanner, currentUser).startMenu();
            case 2 -> new CharacterMenu(scanner, currentUser).startMenu();
            case 3 -> new SettingsMenu(scanner, currentUser).startMenu();
            case 4 -> prepareMiniAdventure();
            case 5 -> {
                currentUser = null;
                return;
            }
            default -> {
                System.out.println("Invalid choice.");
                waitSeconds(2);
            }
        }
    }

    private void displayBanner(){
        globalClock.syncWithRealTime();
        System.out.println("\n\n\n\n\n\n\n\n----- GUILDQUEST -----" );

        String formattedTime = currentUser.getSettings().getFormattedTime(globalClock, currentUser.getSettings().getCurrentRealm());
        System.out.println(formattedTime);
        System.out.print("User: " + currentUser.getUserID());
        System.out.println("\n----------------------");
    }

    private void waitSeconds(int s){
        try {
            Thread.sleep(s * 1000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private void prepareMiniAdventure() {
        System.out.println("\n ----- MINI ADVENTURE SELECT -----");
        System.out.println("1. Timed Raid Window");
        System.out.println("2. Relic Hunt");
        System.out.println("3. Back");
        System.out.print("Choice: ");
        
        String choice = scanner.nextLine();
        if (choice.equals("3")) return;

        if (choice.equals("1") || choice.equals("2")) {
            if (userSlots.size() < 2) {
                System.out.println("There must be at least two users to proceed.");
                waitSeconds(2);
                return;
            }

            System.out.println("\nSelect User Slot for Player 2:");
            for (int i = 0; i < userSlots.size(); i++) {
                if (userSlots.get(i) != currentUser) {
                    System.out.println((i + 1) + ". User: " + userSlots.get(i).getUserID());
                }
            }
            System.out.print("Selection: ");
            
            try {
                int p2Index = Integer.parseInt(scanner.nextLine()) - 1;
                User p2 = userSlots.get(p2Index);

                if (p2 == currentUser) {
                    System.out.println("Player 1 and Player 2 Cannot be the Same.");
                } else if (choice.equals("1")) {
                    TimedRaidWindow raid = new TimedRaidWindow();
                    raid.initializePlayers(currentUser, p2);
                    raid.startGame();
                } else {
                    RelicHunt relicHunt = new RelicHunt();
                    relicHunt.initializePlayers(currentUser, p2);
                    relicHunt.startGame();
                }
            } catch (Exception e) {
                System.out.println("Invalid selection. Returning to menu.");
            }
        }
    }
}
