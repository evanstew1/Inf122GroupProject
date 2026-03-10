import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private String characterClass;
    private int level;
    private Inventory  inventory;
    private int x = 0;
    private int y = 0;

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

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void movePosition(int moveX, int moveY){
        int newX = this.x + moveX;
        int newY = this.y + moveY;

        if (newX >= 0 && newX < 100){
            this.x = newX;
        }

        if(newY >= 0 && newY < 100){
            this.y = newY;
        }
    }
}