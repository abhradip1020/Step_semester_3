public class GymMembership {

    public static void main(String[] args) {

        try {
            new GymMemberAssignment("GM1", 1000);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        PremiumMemberAssignment p =
                new PremiumMemberAssignment("MEM01", 2000, "Coach Riya");

        p.attendSession();
        p.attendSession();

        System.out.println(p.getSessionsAttended());

        System.out.println(
                GymMemberAssignment.signUpBatch(
                        new String[]{"MEM1", "GM1", "MEM2", " ", "MEM3"},
                        1000
                )
        );
    }
}

class GymMemberAssignment {

    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    public GymMemberAssignment(String memberId, int monthlyFee) {

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

    public static String signUpBatch(String[] memberIds, int monthlyFee) {

        int signedUp = 0;
        int rejected = 0;

        for (String memberId : memberIds) {
            try {
                new GymMemberAssignment(memberId, monthlyFee);
                signedUp++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Signed Up: " + signedUp + " | Rejected: " + rejected;
    }
}

class PremiumMemberAssignment extends GymMemberAssignment {

    private String trainerName;

    public PremiumMemberAssignment(
            String memberId,
            int monthlyFee,
            String trainerName) {

        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }
}
