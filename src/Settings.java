public class Settings {
    public enum Theme { Classic, Modern }
    public enum TimeLineView { Day, Week, Month, Year }
    public enum TimeDisplay { WORLD_CLOCK_TIME, REALM_LOCAL_TIME, BOTH }

    private Realm currentRealm;
    private Theme theme = Theme.Classic;
    private TimeDisplay timeDisplayPreference = TimeDisplay.WORLD_CLOCK_TIME;
    private TimeLineView timeLineView = TimeLineView.Day;
    private TimeFormatter formatter = new TimeFormatter();

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
        return switch(timeDisplayPreference){
            case WORLD_CLOCK_TIME -> formatter.formatWorldTime(worldTime);
            case REALM_LOCAL_TIME -> formatter.formatLocalTime(worldTime, realm);
            case BOTH -> formatter.formatBoth(worldTime, realm);
        };
    }
}