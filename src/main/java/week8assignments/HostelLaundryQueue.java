public class HostelLaundryQueue {

    public static void main(String[] args) {
        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashType quick = new QuickWash();
        WashType normal = new NormalWash();
        WashType heavy = new HeavyWash();

        m1.startWash(asha, quick);
        m1.startWash(ravi, heavy);
        m2.startWash(ravi, heavy);
        m1.completeCycle();
        m1.startWash(neha, normal);
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface WashType {
    String getName();
    int getDurationMinutes();
    double getCharge();
}

class QuickWash implements WashType {
    @Override
    public String getName() {
        return "Quick";
    }

    @Override
    public int getDurationMinutes() {
        return 30;
    }

    @Override
    public double getCharge() {
        return 20.00;
    }
}

class NormalWash implements WashType {
    @Override
    public String getName() {
        return "Normal";
    }

    @Override
    public int getDurationMinutes() {
        return 45;
    }

    @Override
    public double getCharge() {
        return 30.00;
    }
}

class HeavyWash implements WashType {
    @Override
    public String getName() {
        return "Heavy";
    }

    @Override
    public int getDurationMinutes() {
        return 60;
    }

    @Override
    public double getCharge() {
        return 45.00;
    }
}

class WashCycle {
    private Student student;
    private WashingMachine machine;
    private WashType washType;

    public WashCycle(Student student, WashingMachine machine, WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }

    public Student getStudent() {
        return student;
    }

    public WashingMachine getMachine() {
        return machine;
    }

    public WashType getWashType() {
        return washType;
    }
}

class WashingMachine {
    private String id;
    private boolean busy;
    private WashCycle currentCycle;

    public WashingMachine(String id) {
        this.id = id;
        this.busy = false;
        this.currentCycle = null;
    }

    public String getId() {
        return id;
    }

    public boolean isBusy() {
        return busy;
    }

    public WashCycle startWash(Student student, WashType washType) {
        if (busy) {
            System.out.printf("Machine %s is currently busy.%n", id);
            return null;
        }

        busy = true;
        currentCycle = new WashCycle(student, this, washType);
        System.out.printf("%s wash started on %s for %s (%d min). Charge: ₹%.2f.%n",
                washType.getName(), id, student.getName(),
                washType.getDurationMinutes(), washType.getCharge());
        return currentCycle;
    }

    public void completeCycle() {
        if (busy) {
            busy = false;
            currentCycle = null;
            System.out.printf("%s cycle completed. %s is now free.%n", id, id);
        }
    }
}
