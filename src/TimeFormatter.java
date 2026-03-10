public class TimeFormatter{
    public String formatWorldTime(WorldClockTime worldTime) {
        return "World Time: " + worldTime.days + "d " + worldTime.hours + "h " + worldTime.minutes + "m";
    }

    public String formatLocalTime(WorldClockTime worldTime, Realm realm) {
        if(realm == null){
            return "No Realm Selected";
        }
        RealmLocalTime local = new RealmLocalTime(worldTime.days, worldTime.hours, worldTime.minutes, realm.getLocalTimeOffset());
        local.updateTime(worldTime);        
        return "Local Time (" + realm.getName() + "): " + local.days + "d " + local.hours + "h " + local.minutes + "m";
    }

    public String formatBoth(WorldClockTime worldTime, Realm realm){
        return formatWorldTime(worldTime) + " | " + formatLocalTime(worldTime,realm);
    }
}