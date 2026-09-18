public class GymMembershipTiers {

    public static void main(String[] args) {

        GymMemberAssignment2 standard =
                new GymMemberAssignment2("MEM1", 1000);

        PremiumMemberAssignment2 premium =
                new PremiumMemberAssignment2("MEM2", 2000, "Coach Riya");

        EliteMemberAssignment elite =
                new EliteMemberAssignment(
                        "MEM3", 3000, "Coach Arjun", "L12");

        GroupClassMemberAssignment group =
                new GroupClassMemberAssignment(
                        "MEM4", 1500, "Zumba");

        standard.displayInfo();
        premium.displayInfo();
        elite.displayInfo();
        group.displayInfo();

        System.out.println(
                GymMemberAssignment2.classifyGeneration(elite)
        );

        System.out.println(
                GymMemberAssignment2.classifyGeneration(group)
        );

        premium.attendSession();
        premium.attendSession();
        premium.attendSession();

        elite.attendSession();
        elite.attendSession();

        group.attendSession();
        group.attendSession();
        group.attendSession();
        group.attendSession();

        GymMemberAssignment2[] members = {
                premium, elite, group
        };

        System.out.println(
                GymMemberAssignment2.getTotalSessionsAttended(members)
        );
    }
}

class GymMemberAssignment2 {

    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    public GymMemberAssignment2(String memberId, int monthlyFee) {

        if (memberId == null || memberId.trim().isEmpty()
                || memberId.length() < 4) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (monthlyFee <= 0) {
            throw new IllegalArgumentException("Monthly fee must be positive");
        }

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }

    public void displayInfo() {
        System.out.println(
                "Standard Member | Sessions: " + sessionsAttended
        );
    }

    public static String classifyGeneration(
            GymMemberAssignment2 member) {

        if (member instanceof EliteMemberAssignment) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (member instanceof GroupClassMemberAssignment) {
            return "Hierarchical sibling (independent branch)";
        }

        if (member instanceof PremiumMemberAssignment2) {
            return "Second generation";
        }

        return "Standard member";
    }

    public static int getTotalSessionsAttended(
            GymMemberAssignment2[] members) {

        int total = 0;

        for (GymMemberAssignment2 member : members) {
            total += member.getSessionsAttended();
        }

        return total;
    }
}

class PremiumMemberAssignment2 extends GymMemberAssignment2 {

    private String trainerName;

    public PremiumMemberAssignment2(
            String memberId,
            int monthlyFee,
            String trainerName) {

        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    @Override
    public void displayInfo() {
        System.out.println(
                "Premium Member | Trainer: "
                        + trainerName
                        + " | Sessions: "
                        + sessionsAttended
        );
    }
}

class EliteMemberAssignment extends PremiumMemberAssignment2 {

    private String lockerNumber;

    public EliteMemberAssignment(
            String memberId,
            int monthlyFee,
            String trainerName,
            String lockerNumber) {

        super(memberId, monthlyFee, trainerName);
        this.lockerNumber = lockerNumber;
    }

    @Override
    public void displayInfo() {
        System.out.println(
                "Elite Member | Trainer: "
                        + getTrainerName()
                        + " | Locker: "
                        + lockerNumber
                        + " | Sessions: "
                        + sessionsAttended
        );
    }
}

class GroupClassMemberAssignment extends GymMemberAssignment2 {

    private String className;

    public GroupClassMemberAssignment(
            String memberId,
            int monthlyFee,
            String className) {

        super(memberId, monthlyFee);
        this.className = className;
    }

    @Override
    public void displayInfo() {
        System.out.println(
                "Group Class Member | Class: "
                        + className
                        + " | Sessions: "
                        + sessionsAttended
        );
    }
}
