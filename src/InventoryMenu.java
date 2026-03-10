import java.util.List;
import java.util.Scanner;

public class InventoryMenu extends MenuTemplate{
    private Character character;

    public InventoryMenu(Scanner scanner, User currentUser, Character character){
        super(scanner, currentUser);
        this.character = character;
    }

    @Override
    protected void printMenuHeader(){
        System.out.println("\n --- INVENTORY: " + character.getName() + " ---");

    }

    @Override
    protected void printList(){
        List<String> items = character.getInventory(); 
            if (items.isEmpty()) {
                System.out.println("[ Inventory is empty ]");
            } else {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + ". " + items.get(i));
                }
            }
    }

    @Override
    protected void printMenuOptions(){
        System.out.println("\nSelect Item # to Manage | Add[A] | Back[B]");
    }

    @Override
    protected void handleInput(String input){
         if (input.equals("A")) {
                System.out.print("Enter Item Name: ");
                String newItem = scanner.nextLine();
                character.addItem(newItem);
                System.out.println(newItem + " added!");
                waitSeconds(2);
                return;
        }

        try{
            int idx = Integer.parseInt(input) - 1;
            List<String> items = character.getInventory();
            if(idx >= 0 && idx < items.size()){
                manageSpecificItem(idx, items.get(idx));
            } else {
                System.out.println("No Item in Inventory Slot" + idx+1);
            }
        } catch(Exception e){
            System.out.println("Invalid Input");
            waitSeconds(2);
        }

    }

    private void manageSpecificItem(int idx, String selectedItem){
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
    }
    
}