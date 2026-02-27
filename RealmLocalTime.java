public class RealmLocalTime extends Time {
    private TimeRule timeRule;

    public RealmLocalTime (int days, int hours, int minutes, TimeRule rule){
        super(days, hours, minutes);
        this.timeRule = rule;

    }
    public void updateTime(WorldClockTime time) {
        Time offset = this.timeRule.getOffset();

        this.days = time.days + offset.days;
        this.hours = time.hours + offset.hours;
        this.minutes = time.minutes + offset.minutes;

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