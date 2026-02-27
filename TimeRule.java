public class TimeRule{
    private Realm realm;
    private Time offset;

    public TimeRule(Realm realm, Time offset) {
        this.realm = realm;
        this.offset = offset;
    }

    public Time getOffset(){
        return offset;
    }
}