public class WeeklyCheckIn {

    public static void main(String[] args) {

        GymMemberAssignment5 m1 = new GymMemberAssignment5(1000);

        System.out.println(m1.membershipNumber);
        System.out.println(GymMemberAssignment5.getMembersEnrolled());

        System.out.println(
                GymMemberAssignment5.isValidReferralCode("G45B")
        );
        System.out.println(
                GymMemberAssignment5.isValidReferralCode("G4B")
        );
        System.out.println(
                GymMemberAssignment5.isValidReferralCode("X45B")
        );

        m1.payFee(500);
        m1.payFee(500, "UPI");

        System.out.println(m1.getFeesPaid());

        GymMemberAssignment5[] members = {
                new GroupClassMemberAssignment5(1500, "Zumba"),
                null,
                new GymMemberAssignment5(1000)
        };

        System.out.println(
                GymMemberAssignment5.processWeeklyCheckIn(members)
        );
    }
}

class GymMemberAssignment5 {

    private static int membersEnrolled = 0;

    public final String membershipNumber;

    protected int monthlyFee;
    protected int feesPaid;
    private String paymentMode;

    public GymMemberAssignment5(int monthlyFee) {

        if (monthlyFee <= 0) {
            throw new IllegalArgumentException("Monthly fee must be positive");
        }

        membersEnrolled++;
        membershipNumber = "GYM-" + (2000 + membersEnrolled);
        this.monthlyFee = monthlyFee;
    }

    public void payFee(int amount) {
        feesPaid += amount;
    }

    public void payFee(int amount, String mode) {
        paymentMode = mode;
        payFee(amount);
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public static boolean isValidReferralCode(String code) {

        if (code == null || code.length() != 4) {
            return false;
        }

        return code.charAt(0) == 'G'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isUpperCase(code.charAt(3));
    }

    public static int getMembersEnrolled() {
        return membersEnrolled;
    }

    public static String processWeeklyCheckIn(
            GymMemberAssignment5[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (GymMemberAssignment5 member : members) {

            if (member == null) {
                nullSkipped++;
            } else {
                processed++;

                if (member instanceof GroupClassMemberAssignment5) {
                    group++;
                } else {
                    individual++;
                }
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + group + " group | "
                + individual + " individual";
    }
}

class GroupClassMemberAssignment5 extends GymMemberAssignment5 {

    private String className;

    public GroupClassMemberAssignment5(
            int monthlyFee,
            String className) {

        super(monthlyFee);
        this.className = className;
    }
}
