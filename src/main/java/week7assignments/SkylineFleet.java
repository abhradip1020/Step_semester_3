
public class SkylineFleet {

    public static void main(String[] args) {
        DeliveryDrone d = new DeliveryDrone("DR-1");
        ScoutDrone s = new ScoutDrone("SC-1");
        GroundRobot g = new GroundRobot("GR-1");

        System.out.println(getLocationIfTrackable(d));
        System.out.println(getLocationIfTrackable(s));
        System.out.println(getLocationIfTrackable(g));
    }

    static String getLocationIfTrackable(Object o) {
        if (o instanceof Trackable) {
            Trackable t = (Trackable) o;
            return t.getLocation();
        }

        return "Tracking not available";
    }
}

abstract class Drone {

    protected String id;

    public Drone(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be blank");
        }

        this.id = id;
    }

    public abstract String fly();
}

interface Trackable {
    String getLocation();
}

class DeliveryDrone extends Drone implements Trackable {

    public DeliveryDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return id + " delivering packages";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

class ScoutDrone extends Drone {

    public ScoutDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return id + " scouting the area";
    }
}

class GroundRobot implements Trackable {

    private String id;

    public GroundRobot(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be blank");
        }

        this.id = id;
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}
