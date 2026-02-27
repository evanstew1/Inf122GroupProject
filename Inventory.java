import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Inventory {
    private List<Item> items = new ArrayList<>();

    public void addInventoryItem(Item i) {
        items.add(i);
        System.out.println("Item " + i.getName() + " added to inventory");
    }

    public void removeInventoryItem(Item i) {
        items.remove(i);
    }

    public void removeInventoryItemName(String name) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(name)) {
                iterator.remove();
            }
        }
    }

    public void updateItemName(int index, String newName) {
    if (index >= 0 && index < items.size()) {
        items.get(index).setName(newName);
    }
}

    public List<Item> getItems() {
        return items;
    }
}