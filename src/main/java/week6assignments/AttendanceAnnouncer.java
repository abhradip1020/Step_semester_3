public class AttendanceAnnouncer {

    public static void main(String[] args) {

        AttendanceMember standard =
                new AttendanceMember("MEM6", 1000);

        PremiumAttendanceMember premium =
                new PremiumAttendanceMember(
                        "MEM7", 2000, "Coach Riya");

        AttendanceMember[] members = {
                standard, premium
        };

        System.out.println(
                AttendanceMember.batchPrint(members)
        );

        AttendanceMember plain =
                new AttendanceMember("MEM8", 1000);

        try {
            PremiumAttendanceMember bad =
                    (PremiumAttendanceMember) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}

class AttendanceMember {

    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    public AttendanceMember(String memberId, int monthlyFee) {

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
                "Standard Member | Sessions: "
                        + sessionsAttended
        );
    }

    public static String batchPrint(
            AttendanceMember[] members) {

        StringBuilder announcement = new StringBuilder();

        for (AttendanceMember member : members) {

            member.displayInfo();

            if (member instanceof PremiumAttendanceMember) {

                PremiumAttendanceMember premium =
                        (PremiumAttendanceMember) member;

                announcement.append("Premium | Trainer: ")
                        .append(premium.getTrainerName())
                        .append(" | Sessions: ")
                        .append(member.getSessionsAttended())
                        .append(" [Trainer via downcast: ")
                        .append(premium.getTrainerName())
                        .append("] | ");

            } else {

                announcement.append("Standard | Sessions: ")
                        .append(member.getSessionsAttended())
                        .append(" | ");
            }
        }

        return announcement.toString();
    }
}

class PremiumAttendanceMember extends AttendanceMember {

    private String trainerName;

    public PremiumAttendanceMember(
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
