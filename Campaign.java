import java.util.List;

public class Campaign{
    public enum Visibility{Public, Private}
    public enum Permission{View_Only, Collaborative}

    private String name;
    private List<QuestEvent> questEvents;
    private Visibility visibility;
    private boolean isArchived;

    public Campaign(String name) {
        this.name = name;
        this.questEvents = new java.util.ArrayList<>();
        this.visibility = Visibility.Private;
        this.isArchived = false;
    }
    public void renameCampaign(String newName) {
        this.name = newName;
    }
    public void shareCampaign(User recipient, Permission p) {
        //im leaving this one undefined
    }
    public void changeCampaignVisibility(Visibility v) {
        this.visibility = v;
    }
    public void archiveCampaign() {
        this.isArchived = true;
    }
    public void addQuestEvent(QuestEvent event) {
        questEvents.add(event);
    }
    public void removeQuestEvent(QuestEvent event) {
        questEvents.remove(event);
    }
    public String getName() {
        return this.name;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public List<QuestEvent> getQuestEvents() {
        return questEvents;
    }

}