
public class MorningWakeUp {

    public static void main(String[] args) {
        AlarmClock a = new AlarmClock("7:00 AM");
        Doorbell d = new Doorbell("Front Door");

        System.out.println(a.ring());
        System.out.println(d.ring());

        ringAll(new Ringable[]{a, d});
    }

    static void ringAll(Ringable[] devices) {
        for (Ringable device : devices) {
            System.out.println(device.ring());
        }
    }
}

interface Ringable {
    String ring();
}

class AlarmClock implements Ringable {

    private String time;

    public AlarmClock(String time) {
        if (time == null || time.trim().isEmpty()) {
            throw new IllegalArgumentException("Time cannot be blank");
        }
        this.time = time;
    }

    @Override
    public String ring() {
        return "Alarm ringing for " + time;
    }
}

class Doorbell implements Ringable {

    private String location;

    public Doorbell(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be blank");
        }
        this.location = location;
    }

    @Override
    public String ring() {
        return "Doorbell ringing at " + location;
    }
}
