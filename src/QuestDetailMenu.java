import java.util.List;
import java.util.Scanner;

public class QuestDetailMenu extends MenuTemplate {
    private QuestEvent quest;

    public QuestDetailMenu(Scanner scanner, User currentUser, QuestEvent quest){
        super(scanner, currentUser);
        this.quest = quest;
    }

    @Override
    protected void printMenuHeader() {
        System.out.println("\n\n\n\n\n\n\n\n --- QUEST DETAIL: " + quest.getName() + " ---");

    }

    @Override
    protected void printList() {}

    @Override
    protected void printMenuOptions() {
        System.out.println("1. Rename Quest");
        System.out.println("2. Change Realm (Current: " + quest.getRealm().getName() + ")");
        System.out.println("3. Grant Item to Character");
        System.out.println("4. Remove Item from Character");
        System.out.println("[B] Back");
    }

    @Override
    protected void handleInput(String input){

        switch (input) {
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
            default -> {
                System.out.println("Invalid selection.");
                waitSeconds(2);
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
}