import java.util.List;
import java.util.ArrayList;

public class Realm {
    private String name;
    private String description;
    private int mapID;
    private TimeRule localTimeRule;

    public Realm(String name, String description, int mapID){
        this.name = name;
        this.description = description;
        this.mapID = mapID;
        Time offsetTime = new Time(0, mapID, 0);
        this.localTimeRule = new TimeRule(this, offsetTime);
    }

    public void setLocalTimeRule(TimeRule rule){
        this.localTimeRule = rule;
    }

    public TimeRule getLocalTimeRule(){
        return localTimeRule;
    }

    public String getName(){
        return name;
    }
}