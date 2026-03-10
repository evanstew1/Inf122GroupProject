import java.util.List;
import java.util.Scanner;

public class CharacterMenu extends MenuTemplate {
    public CharacterMenu(Scanner scanner, User currentUser) {
        super(scanner, currentUser);
    }

    @Override
    protected void printMenuHeader(){
        System.out.println("\n ----- GUILDQUEST: Character Menu -----\n");
    }

    @Override
    protected void printList(){
        List<Character> chars = currentUser.getCharacters();
        for(int i = 0; i < chars.size(); i++){
            System.out.println((i+1) + ". " + chars.get(i).getName() + " Class: " + chars.get(i).getCharacterClass() + " LVL: " + chars.get(i).getLevel());
        }
    }

    @Override
    protected void printMenuOptions(){
        System.out.println("\nOpen[O]  Add[A]  Delete[D]  Back[B]");
    }

    @Override
    protected void handleInput(String input){
        switch(input){
            case "O" -> openCharacter();
            case "A" -> addCharacter();
            case "D" -> deleteCharacter();
            default -> {
                System.out.println("Please select a correct option");
                waitSeconds(2);
            }
        }
    }

    private void openCharacter(){
        System.out.println("Select Character #: ");
            try {
                int idx = Integer.parseInt(scanner.nextLine()) - 1;
                List<Character> chars = currentUser.getCharacters();
                if (idx >= 0 && idx < chars.size()) {
                    new InventoryMenu(scanner, currentUser, chars.get(idx)).startMenu();
                }
            } catch (Exception e) { System.out.println("Invalid input."); }
    }

    private void addCharacter(){
        System.out.print("Name: ");
        String n = scanner.nextLine();
        System.out.print("Class: ");
        String c = scanner.nextLine();
        currentUser.addCharacter(new Character(n, c, 1)); // Starting at LVL 1
    }

    private void deleteCharacter(){
        System.out.print("Delete Character #: ");
        List<Character> chars = currentUser.getCharacters();
            try {
               int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < chars.size()) currentUser.removeCharacter(chars.get(idx));
        } catch (Exception e) { System.out.println("Invalid input."); }
    }
}