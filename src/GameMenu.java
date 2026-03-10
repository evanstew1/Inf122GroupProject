import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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

        while(!valid) {
            int selection = Integer.parseInt(scanner.nextLine());
            //From 1 to max intialized users
            if(selection >= 1 && selection <= userSlots.size()) {
                currentUser = userSlots.get(selection - 1);
                valid = true;
            } else if (selection == 5) {
                if(userSlots.size() < MAX_SLOTS) {
                    User newUser = new User(userSlots.size() + 1);
                    userSlots.add(newUser);
                    System.out.println("User Created and Logged In!");
                } else {
                    System.out.println("Error: All 4 slots are full");
                }
                valid = true;
            } else if (selection == 6) {
                System.out.println("Enter User Slot to delete (1-4)");
                int toDel = Integer.parseInt(scanner.nextLine());
                if(toDel > 0 && toDel <= userSlots.size()) {
                     userSlots.remove(toDel - 1);
                }
                valid = true;
            } else if (selection == 7) {
                valid = true;
                System.exit(0);
            } else {
                System.out.println("ERROR: Invalid input try again");
                waitSeconds(2);
            }
        }
    }

    private void showMainMenu() {
        displayBanner();
        System.out.println("\n\n\n\n\n\n\n\n ----- GUILDQUEST: Main Menu -----\n");
        System.out.println("1. Campaign Menu");
        System.out.println("2. Character Menu");
        System.out.println("3. Settings");
        System.out.println("4. Logout");
        System.out.println("Selection(press # to select): ");
        boolean selected = false;

        int selection = Integer.parseInt(scanner.nextLine());
        switch (selection) {
            case 1 -> new CampaignMenu(scanner, currentUser).startMenu();
            case 2 -> new CharacterMenu(scanner, currentUser).startMenu();
            case 3 -> new SettingsMenu(scanner, currentUser).startMenu();
            case 4 -> {
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
    }
}