import java.util.List;
import java.util.ArrayList;

public class QuestEvent{
    private String name;
    private WorldClockTime startTime;
    private WorldClockTime endTime;
    private Realm realm;
    private Campaign campaign;
    private List<Character> charactersParticipated;

    public QuestEvent(String name, WorldClockTime startTime, Realm realm, Campaign campaign) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.realm = realm;
        this.campaign = campaign;
        this.charactersParticipated = new ArrayList<>();
    }

    public void shareQuestEvent(User recipient, Campaign.Permission p) {
        //im not adding this
    }
    public void editName(String name) {
        this.name = name;
    }
    public void editTime(WorldClockTime time) {
        this.startTime = time;
    }
    public void editRealm(Realm realm) {
        this.realm=realm;
    }
    public Time getEventTime(){
        return startTime;
    }

    public void grantItem(String itemName, Character c) {
        c.addItem(itemName); 
    }

    public void removeItem(String itemName, Character c) {
        c.removeItem(itemName);
    }

    public String getName() {
        return this.name;
    }

    public Realm getRealm() {
        return this.realm;
    }
}