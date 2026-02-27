import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.Instant;

public class GameMenu {
    private Scanner scanner = new Scanner(System.in);
    private List<User> userSlots = new ArrayList<>();
    private User currentUser = null;
    private final int MAX_SLOTS = 4;
    private Instant sessionStart = Instant.now();
    private WorldClockTime globalClock = new WorldClockTime(0,0,0);

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
            case 1 -> campaignMenu();
            case 2 -> characterMenu();
            case 3 -> settingsMenu();
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

    private void campaignMenu(){
        while (true){
            displayBanner();
            System.out.println("\n ----- GUILDQUEST: Campaign Menu -----\n");
            List<Campaign> camps = currentUser.getCampaigns();
            for(int i = 0; i < camps.size(); i++){
                System.out.println((i+1) + ". " + camps.get(i).getName());
            }
            System.out.println("Open [O] Add[A]  Delete[D] Archive[E] Rename[R] Visibility[V] Back [B]");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("A")) {
                System.out.print("Name: ");
                currentUser.addCampaign(new Campaign(scanner.nextLine()));
            } else if (input.equals("D")) {
                System.out.print("Campaign to Delete (Camp #): ");
                try {
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    if (index >= 0 && index < camps.size()) {
                        currentUser.removeCampaign(camps.get(index));
                    }
                } catch (Exception e) { System.out.println("Invalid number"); }
            } else if (input.equals("O")) {
                System.out.print("Campaign to Open (Camp #): ");
                try {
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    if (index >= 0 && index < camps.size()) {
                        manageQuestEvents(camps.get(index));
                    }
                } catch (Exception e) { System.out.println("Invalid number.");}
            } else if (input.equals("E")) {
                System.out.print("Campaign to Archive (Camp #): ");
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index >= 0 && index < camps.size()) {
                    Campaign arch = camps.get(index);
                    currentUser.archiveCampaign(arch);
                    System.out.println("Campaign Archived.");
                    waitSeconds(2);
                } else {
                    System.out.println("Invalid index");
                    waitSeconds(2);
                }
            } else if (input.equals("R")) {
                System.out.print("Campaign to Rename (Camp #): ");
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index >= 0 && index < camps.size()) {
                    System.out.print("Enter New Name: ");
                    camps.get(index).renameCampaign(scanner.nextLine());
                } else {
                    System.out.println("Invalid index");
                    waitSeconds(2);
                }
            } else if (input.equals("V")) {
                System.out.print("Toggle Visibility for (Camp #): ");
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index >= 0 && index < camps.size()) {
                    Campaign c = camps.get(index);
                    if (c.getVisibility() == Campaign.Visibility.Public) {
                        c.changeCampaignVisibility(Campaign.Visibility.Private);
                        System.out.println("Visibility is now Private");
                        waitSeconds(2);
                    } else {
                        c.changeCampaignVisibility(Campaign.Visibility.Public);
                        System.out.println("Visibility is now Public");
                        waitSeconds(2);
                    }
                }
            } else if (input.equals("B")) {
                
                return;
            } else {
                System.out.println("Please select a correct option");
                waitSeconds(2);
            }
        }
    }

    private void characterMenu() {
        while(true){
            displayBanner();
            System.out.println("\n ----- GUILDQUEST: Character Menu -----\n");
            List<Character> chars = currentUser.getCharacters();
            for(int i = 0; i < chars.size(); i++){
                System.out.println((i+1) + ". " + chars.get(i).getName() + " Class: " + chars.get(i).getCharacterClass() + " LVL: " + chars.get(i).getLevel());
            }
            
            System.out.println("\nOpen[O]  Add[A]  Delete[D]  Back[B]");
            String input = scanner.nextLine().toUpperCase();
            
            if(input.equals("O")) {
                System.out.print("Select Character #: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < chars.size()) manageInventory(chars.get(idx));
                } catch (Exception e) { System.out.println("Invalid input."); }
                
            } else if(input.equals("A")) {
                System.out.print("Name: ");
                String n = scanner.nextLine();
                System.out.print("Class: ");
                String c = scanner.nextLine();
                currentUser.addCharacter(new Character(n, c, 1)); // Starting at LVL 1
                
            } else if(input.equals("D")) {
                System.out.print("Delete Character #: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < chars.size()) currentUser.removeCharacter(chars.get(idx));
                } catch (Exception e) { System.out.println("Invalid input."); }
                
            } else if(input.equals("B")) {
                return;
            }
        }
    }

    private void manageInventory(Character character) {
        while(true) {
            displayBanner();
            System.out.println("\n --- INVENTORY: " + character.getName() + " ---");
            List<String> items = character.getInventory(); 
            
            if (items.isEmpty()) {
                System.out.println("[ Inventory is empty ]");
            } else {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + ". " + items.get(i));
                }
            }

            System.out.println("\nSelect Item # to Manage | Add[A] | Back[B]");
            System.out.print("Selection: ");
            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("B")) {
                return;
            }

            if (choice.equals("A")) {
                System.out.print("Enter Item Name: ");
                String newItem = scanner.nextLine();
                character.addItem(newItem);
                System.out.println(newItem + " added!");
                waitSeconds(2);
                continue;
            }

            int idx = Integer.parseInt(choice) - 1;
            if (idx >= 0 && idx < items.size()) {
                String selectedItem = items.get(idx);
                System.out.println("\nManaging: " + selectedItem);
                System.out.println("1. Update Name");
                System.out.println("2. Remove Item");
                System.out.println("3. Cancel");
                System.out.print("Selection: ");
                    
                String action = scanner.nextLine();
                if (action.equals("1")) {
                    System.out.print("New Name: ");
                    String newName = scanner.nextLine();
                    character.updateItem(idx, newName);
                    System.out.println("Updated!");
                } else if (action.equals("2")) {
                    character.removeItem(selectedItem);
                    System.out.println("Removed!");
                }
                waitSeconds(2);
            } else {
                System.out.println("Invalid item number.");
                waitSeconds(2);
            }
        }
    }
    private void settingsMenu() {
    boolean inSettings = true;
    Settings userSettings = currentUser.getSettings();

    while (inSettings) {
            displayBanner();
            System.out.println("\n\n\n\n\n\n\n\n ----- GUILDQUEST: Settings Menu -----");
            System.out.println("Current Realm: " + userSettings.getCurrentRealm().getName());
            System.out.println("Current Theme: " + userSettings.getTheme()); 
            System.out.println("---------------------------------------");
            System.out.println("1. Toggle Time Display (World/Local/Both)");
            System.out.println("2. Update Current Realm");
            System.out.println("3. Change App Theme (Classic/Modern)");
            System.out.println("4. Back to Main Menu");
            System.out.print("\nSelection: ");

            String selection = scanner.nextLine();

            if (selection.equals("1")) {
                System.out.println("\nSelect Preference:\n1. World Clock\n2. Realm-Local\n3. Both");
                int timeSel = Integer.parseInt(scanner.nextLine());
                if (timeSel == 1) {
                     userSettings.updateTimeDisplayPreference(Settings.TimeDisplay.WORLD_CLOCK_TIME);
                } else if (timeSel == 2) {
                    userSettings.updateTimeDisplayPreference(Settings.TimeDisplay.REALM_LOCAL_TIME);
                } else if (timeSel == 3) {
                    userSettings.updateTimeDisplayPreference(Settings.TimeDisplay.BOTH);
                } else {
                    System.out.println("Invalid selection.");
                    waitSeconds(1);
                }
            }else if (selection.equals("2")) {
                System.out.print("Enter name of the new Realm: ");
                String realmName = scanner.nextLine();
                System.out.print("Enter description of the new Realm: ");
                String realmDesc = scanner.nextLine();
                System.out.print("Enter Realm Time Offset (e.g., -4 or 4): ");
                int offset = Integer.parseInt(scanner.nextLine());
                userSettings.setCurrentRealm(new Realm(realmName, realmDesc , offset));
                System.out.println("Location updated to " + realmName);
            } else if (selection.equals("3")) {
                System.out.println("Select Theme:\n1. Classic\n2. Modern");
                String themeSel = scanner.nextLine();
                if (themeSel.equals("1")) userSettings.setTheme("Classic");
                else if (themeSel.equals("2")) userSettings.setTheme("Modern");
                System.out.println("Theme updated.");
                waitSeconds(1);
            } else if (selection.equals("4")) {
                inSettings = false;
            } else {
                System.out.println("Invalid Input try again");
                waitSeconds(1);
            }
        }
    }

    private void manageQuestEvents(Campaign campaign) {
        while (true) {
            displayBanner();
            System.out.println("\n\n\n\n\n\n\n\n --- QUEST MANAGER: " + campaign.getName() + " ---");
            List<QuestEvent> events = campaign.getQuestEvents();
            
            for (int i = 0; i < events.size(); i++) {
                QuestEvent q = events.get(i);
                String timeStr = currentUser.getSettings().getFormattedTime(
                    (WorldClockTime)q.getEventTime(), q.getRealm());
                System.out.println((i + 1) + ". [" + timeStr + "] " + q.getName());
            }

            System.out.println("\nOpen[O] Add[A] Delete[D] Back[B]");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("B")) return;

            if (input.equals("A")) {
                System.out.print("Quest Title: ");
                String title = scanner.nextLine();
                Realm defaultRealm = new Realm("Default Realm", "Default", 1); 
                globalClock.syncWithRealTime();
                campaign.addQuestEvent(new QuestEvent(title, globalClock, defaultRealm, campaign));
            } else if (input.equals("O")) {
                System.out.print("Quest # to open: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < events.size()) {
                        questMenu(events.get(idx));
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    waitSeconds(1);
                }
            } else if (input.equals("D")) {
                System.out.print("Quest # to delete: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < events.size()) campaign.removeQuestEvent(events.get(idx));
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    waitSeconds(1);
                }
            }
        }
    }

    private void questMenu(QuestEvent quest) {
    while (true) {
        displayBanner();
        System.out.println("\n\n\n\n\n\n\n\n --- QUEST DETAIL: " + quest.getName() + " ---");
        System.out.println("1. Rename Quest");
        System.out.println("2. Change Realm (Current: " + quest.getRealm().getName() + ")");
        System.out.println("3. Grant Item to Character");
        System.out.println("4. Remove Item from Character");
        System.out.println("5. Back");
        System.out.print("Selection: ");

        String choice = scanner.nextLine();
        if (choice.equals("5")) break;

        switch (choice) {
            case "1" -> {
                System.out.print("New Name: ");
                quest.editName(scanner.nextLine());
            }
            case "2" -> {
                System.out.println("New Realm Name: ");
                String realmName = scanner.nextLine();
                System.out.println("New Realm Description: ");
                String realmDesc = scanner.nextLine();
                System.out.println("New Realm Offset(Time): ");
                int realmTime = Integer.parseInt(scanner.nextLine());
                quest.editRealm(new Realm(realmName, realmDesc, realmTime));
            }
            case "3" -> handleLoot(quest, true);
            case "4" -> handleLoot(quest, false);
        }
    }
}

    private void handleLoot(QuestEvent quest, boolean isGranting) {
        List<Character> userChars = currentUser.getCharacters();
        if (userChars.isEmpty()) {
            System.out.println("No characters available.");
            waitSeconds(1);
            return;
        }

        for (int i = 0; i < userChars.size(); i++) {
            System.out.println((i + 1) + ". " + userChars.get(i).getName() + " (" + userChars.get(i).getClass() + ")");
        }
        System.out.print("Select Character #: ");
        
        try {
            int charIdxex = Integer.parseInt(scanner.nextLine()) - 1;
            if (charIdxex >= 0 && charIdxex < userChars.size()) {
                Character target = userChars.get(charIdxex);
                System.out.print("Item Name: ");
                String itemName = scanner.nextLine();
                
                if (isGranting) {
                    quest.grantItem(itemName, target);
                    System.out.println(itemName + " added to " + target.getName());
                } else {
                    quest.removeItem(itemName, target);
                    System.out.println(itemName + " removed.");
                }
                waitSeconds(1);
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
            waitSeconds(1);
        }
    }

    private void waitSeconds(int s) {
    try {
        Thread.sleep(s * 1000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
}