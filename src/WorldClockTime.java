import java.time.Instant;
import java.time.Duration;

public class WorldClockTime extends Time {
    private final Instant startTime;
    private static WorldClockTime instance;

    public WorldClockTime(int days, int hours, int minutes) {
        super(days, hours, minutes);
        this.startTime = Instant.now(); 
    }

    //access point
    public static synchronized WorldClockTime getInstance() {
        if (instance == null){
            instance = new WorldClockTime(0,0,0);
        }
        return instance;
    }

    public void syncWithRealTime() {
        Duration elapsed = Duration.between(startTime, Instant.now());
        long totalMinutes = elapsed.toMinutes();

        this.days = (int) (totalMinutes / 1440);
        this.hours = (int) ((totalMinutes % 1440) / 60);
        this.minutes = (int) (totalMinutes % 60);
    }

    public String getFormattedDisplay(Settings settings, Realm realm){
        return settings.getFormattedTime(this,realm);
    }
}