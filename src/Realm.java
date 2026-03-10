import java.util.List;
import java.util.ArrayList;

public class Realm {
    private String name;
    private String description;
    private int mapID;
    private Time localTimeOffset;

    public Realm(String name, String description, int mapID){
        this.name = name;
        this.description = description;
        this.mapID = mapID;
        this.localTimeOffset = new Time(0, mapID, 0);
    }

    public void setLocalTimeOffset(Time offset){
        this.localTimeOffset = offset;
    }

    public Time getLocalTimeOffset(){
        return localTimeOffset;
    }

    public String getName(){
        return name;
    }
}