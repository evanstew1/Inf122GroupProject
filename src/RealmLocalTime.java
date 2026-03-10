public class RealmLocalTime extends Time {
    private Time timeOffset;

    public RealmLocalTime (int days, int hours, int minutes, Time offset){
        super(days, hours, minutes);
        this.timeOffset = offset;

    }
    public void updateTime(WorldClockTime time) {
        this.days = time.days + timeOffset.days;
        this.hours = time.hours + timeOffset.hours;
        this.minutes = time.minutes + timeOffset.minutes;

        //make sure hours < 24 min < 60
        if(this.minutes >= 60){
            this.hours += this.minutes / 60;
            this.minutes %= 60;
        }

        if(this.hours >= 24) {
            this.days += this.hours/24;
            this.hours %= 24;
        }
    };
}