import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private String characterClass;
    private int level;
    private Inventory  inventory;

    //initalize character
    public Character(String name, String characterClass, int level){
        this.name = name;
        this.characterClass = characterClass;
        this.level = level;
        this.inventory = new Inventory();
    }

    public List<String> getInventory() {
        List<String> names = new ArrayList<>();
        for (Item i : this.inventory.getItems()) {
            names.add(i.getName());
        }
        return names;
    }

    public String getName(){
        return this.name;
    }
    public String getCharacterClass(){
        return this.characterClass;
    }
    public int getLevel(){
        return this.level;
    }

    public void addItem(String itemName){
        this.inventory.addInventoryItem(new Item(itemName));
    }

    public void updateItem(int index, String newName){
        this.inventory.updateItemName(index, newName);
    }

    public void removeItem(String itemName){
        this.inventory.removeInventoryItemName(itemName);
    }
}