import java.util.List;
import java.util.ArrayList;

public class User{
    private int userID;
    private List<Campaign> campaigns;
    private List<Campaign> archived;
    private List<Character> characters;
    private Settings settings;

    public User(int userID){
        this.userID = userID;
        this.campaigns = new ArrayList<>();
        this.archived = new ArrayList<>();
        this.characters = new ArrayList<>();
        //Otherwise null is thrown
        Realm defaultRealm = new Realm("Default Realm", "Default", 0);
        this.settings = new Settings(defaultRealm);
        
    }

    public void addCampaign(Campaign c) {
        campaigns.add(c);
    }
    public void removeCampaign(Campaign c) {
        campaigns.remove(c);
    }
    public void addCharacter (Character c) {
        characters.add(c);
    }
    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    public void archiveCampaign(Campaign c){
        if(campaigns.contains(c)){
            campaigns.remove(c);
            archived.add(c);
            c.archiveCampaign();
        }
    }

    public Settings getSettings(){
        return settings;
    }

    public int getUserID(){
        return userID;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}