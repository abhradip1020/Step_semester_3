public class FitZoneMembershipDesk {

    public static void main(String[] args) {
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        MembershipPlan quarterly = new QuarterlyPlan();
        MembershipPlan monthly = new MonthlyPlan();

        Membership ashaMembership = asha.buyMembership(quarterly);
        Membership raviMembership = ravi.buyMembership(monthly);

        ashaMembership.checkIn();
        ashaMembership.freeze();
        ashaMembership.checkIn();

        raviMembership.expire();
        raviMembership.freeze();
    }
}

class Member {
    private String name;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Membership buyMembership(MembershipPlan plan) {
        Membership membership = new Membership(this, plan);
        System.out.printf("%s membership created for %s. Fee: ₹%,.2f. Status: %s.%n",
                plan.getName(), name, membership.getFee(), membership.getStatus().getDisplayName());
        return membership;
    }
}

enum MembershipStatus {
    ACTIVE("Active"),
    FROZEN("Frozen"),
    EXPIRED("Expired");

    private final String displayName;

    MembershipStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

interface MembershipPlan {
    String getName();
    double calculateFee();
}

class MonthlyPlan implements MembershipPlan {
    @Override
    public String getName() {
        return "Monthly";
    }

    @Override
    public double calculateFee() {
        return 1000.00;
    }
}

class QuarterlyPlan implements MembershipPlan {
    @Override
    public String getName() {
        return "Quarterly";
    }

    @Override
    public double calculateFee() {
        return 3 * 1000.00 * 0.90;
    }
}

class AnnualPlan implements MembershipPlan {
    @Override
    public String getName() {
        return "Annual";
    }

    @Override
    public double calculateFee() {
        return 12 * 1000.00 * 0.75;
    }
}

class Membership {
    private Member member;
    private MembershipPlan plan;
    private double fee;
    private MembershipStatus status;

    public Membership(Member member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;
        this.fee = plan.calculateFee();
        this.status = MembershipStatus.ACTIVE;
    }

    public Member getMember() {
        return member;
    }

    public MembershipPlan getPlan() {
        return plan;
    }

    public double getFee() {
        return fee;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public void checkIn() {
        if (status == MembershipStatus.ACTIVE) {
            System.out.printf("%s checked in successfully.%n", member.getName());
        } else {
            System.out.printf("Check-in denied: %s's membership is %s.%n",
                    member.getName(), status.getDisplayName());
        }
    }

    public void freeze() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println("Cannot freeze an Expired membership.");
            return;
        }
        if (status == MembershipStatus.ACTIVE) {
            status = MembershipStatus.FROZEN;
            System.out.printf("%s's membership frozen. Status: %s.%n",
                    member.getName(), status.getDisplayName());
        }
    }

    public void unfreeze() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println("Cannot unfreeze an Expired membership.");
            return;
        }
        if (status == MembershipStatus.FROZEN) {
            status = MembershipStatus.ACTIVE;
            System.out.printf("%s's membership unfrozen. Status: %s.%n",
                    member.getName(), status.getDisplayName());
        }
    }

    public void expire() {
        status = MembershipStatus.EXPIRED;
        System.out.printf("%s's membership expired. Status: %s.%n",
                member.getName(), status.getDisplayName());
    }
}
