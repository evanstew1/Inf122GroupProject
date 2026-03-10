import java.util.List;
import java.util.Scanner;

public class SettingsMenu extends MenuTemplate{
    public SettingsMenu(Scanner scanner, User currentUser) {
        super(scanner, currentUser);
    }

    @Override
    protected void printMenuHeader() {
        System.out.println("----- GUILDQUEST: Settings Menu -----");
        Settings userSettings = currentUser.getSettings();
        System.out.println("Current Realm: " + userSettings.getCurrentRealm().getName());
        System.out.println("Current Theme: " + userSettings.getTheme()); 
        System.out.println("---------------------------------------");
    }

    @Override
    protected void printList(){}

    @Override
    protected void printMenuOptions(){
        System.out.println("1. Toggle Time Display (World/Local/Both)");
        System.out.println("2. Update Current Realm");
        System.out.println("3. Change App Theme (Classic/Modern)");
        System.out.println("Select Option | Back[B]: ");
    }
    @Override
    protected void handleInput(String input){

        Settings userSettings = currentUser.getSettings();

        if (input.equals("1")) {
                System.out.println(" Select Preference:  1. World Clock  2. Realm-Local 3. Both");
                int timeSel = Integer.parseInt(scanner.nextLine());
                if (timeSel == 1) {
                     userSettings.updateTimeDisplayPreference(Settings.TimeDisplay.WORLD_CLOCK_TIME);
                } else if (timeSel == 2) {
                    userSettings.updateTimeDisplayPreference(Settings.TimeDisplay.REALM_LOCAL_TIME);
                } else if (timeSel == 3) {
                    userSettings.updateTimeDisplayPreference(Settings.TimeDisplay.BOTH);
                } else {
                    System.out.println("Invalid input.");
                    waitSeconds(1);
                }
            }else if (input.equals("2")) {
                System.out.print("Enter name of the new Realm: ");
                String realmName = scanner.nextLine();
                System.out.print("Enter description of the new Realm: ");
                String realmDesc = scanner.nextLine();
                System.out.print("Enter Realm Time Offset (e.g., -4 or 4): ");
                int offset = Integer.parseInt(scanner.nextLine());
                userSettings.setCurrentRealm(new Realm(realmName, realmDesc , offset));
                System.out.println("Location updated to " + realmName);
            } else if (input.equals("3")) {
                System.out.println("Select Theme: 1. Classic 2. Modern");
                String themeSel = scanner.nextLine();
                if (themeSel.equals("1")) userSettings.setTheme("Classic");
                else if (themeSel.equals("2")) userSettings.setTheme("Modern");
                System.out.println("Theme updated.");
                waitSeconds(1);
            } else if (input.equals("4")) {
                return;
            } else {
                System.out.println("Invalid Input try again");
                waitSeconds(1);
            }
    }
}