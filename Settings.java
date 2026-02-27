public class Settings {
    public enum Theme { Classic, Modern }
    public enum TimeLineView { Day, Week, Month, Year }
    public enum TimeDisplay { WORLD_CLOCK_TIME, REALM_LOCAL_TIME, BOTH }

    private Realm currentRealm;
    private Theme theme = Theme.Classic;
    private TimeDisplay timeDisplayPreference = TimeDisplay.WORLD_CLOCK_TIME;
    private TimeLineView timeLineView = TimeLineView.Day;

    public Settings(Realm startRealm) {
        this.currentRealm = startRealm;
    }
    public void setCurrentRealm(Realm r) {
        this.currentRealm = r;
    }

    public Realm getCurrentRealm() {
        return currentRealm;
    }

    public void updateTheme(Theme t) {
        this.theme = t;
    }

    public void setTheme(String name) {
        if(name.equalsIgnoreCase("Modern")) {
            this.theme = Theme.Modern;
        } else {
            this.theme = Theme.Classic;
        }
    }
    public Theme getTheme() {
        return this.theme;
    }

    public void updateTimeDisplayPreference(TimeDisplay preference) {
        this.timeDisplayPreference = preference;
    }

    public void updateTimeLineView(TimeLineView view) {
        this.timeLineView = view;
    }

    public String getFormattedTime(WorldClockTime worldTime, Realm realm) {
        if (realm == null) {
            return "World Time: " + worldTime.days + "d " + worldTime.hours + "h";
        }

        String worldStr = "World Time: " + worldTime.days + "d " + worldTime.hours + "h " + worldTime.minutes + "m";
    
        RealmLocalTime local = new RealmLocalTime(worldTime.days, worldTime.hours, worldTime.minutes, realm.getLocalTimeRule());
        local.updateTime(worldTime);
        String localStr = "Local Time (" + realm.getName() + "): " + local.days + "d " + local.hours + "h " + local.minutes + "m";

        if (timeDisplayPreference == TimeDisplay.REALM_LOCAL_TIME) {
            return localStr;
        } else if (timeDisplayPreference == TimeDisplay.BOTH) {
            return worldStr + " | " + localStr;
        } else {
            return worldStr;
        }
    }
}