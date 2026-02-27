import java.time.Instant;
import java.time.Duration;

public class WorldClockTime extends Time {
    private final Instant startTime;

    public WorldClockTime(int days, int hours, int minutes) {
        super(days, hours, minutes);
        this.startTime = Instant.now(); 
    }

    public void syncWithRealTime() {
        Duration elapsed = Duration.between(startTime, Instant.now());
        long totalMinutes = elapsed.toMinutes();

        this.days = (int) (totalMinutes / 1440);
        this.hours = (int) ((totalMinutes % 1440) / 60);
        this.minutes = (int) (totalMinutes % 60);
    }
}