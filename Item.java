public class Item{
    private String name;
    private String rarity;
    private String type;
    private String description;

    public Item(String name) {
        this.name = name;
        this.rarity = "Common";
        this.type = "Magic";
        this.description = "This item has no description";
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
}