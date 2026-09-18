public class MembershipAudit {

    public static void main(String[] args) {

        AuditMember m1 = new AuditMember(3);

        System.out.println(m1.memberNumber);
        System.out.println(AuditMember.getMembersEnrolled());

        System.out.println(AuditMember.isValidRenewalCode("R12A"));
        System.out.println(AuditMember.isValidRenewalCode("R1A"));
        System.out.println(AuditMember.isValidRenewalCode("X12A"));

        m1.borrowBook();
        m1.borrowBook("Fiction");

        System.out.println(m1.getBooksBorrowed());

        AuditMember[] members = {
            new AuditFacultyMember(5, "Physics"),
            null,
            new AuditMember(3)
        };

        System.out.println(AuditMember.processNightlyAudit(members));
    }
}

class AuditMember {

    private static int membersEnrolled = 0;

    public final String memberNumber;
    protected int borrowLimit;
    protected int booksBorrowed;
    private String lastGenre;

    public AuditMember(int borrowLimit) {
        if (borrowLimit <= 0) {
            throw new IllegalArgumentException("Borrow limit must be positive");
        }

        membersEnrolled++;
        memberNumber = "LIB-" + (100 + membersEnrolled);
        this.borrowLimit = borrowLimit;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public void borrowBook(String genre) {
        lastGenre = genre;
        borrowBook();
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public static boolean isValidRenewalCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }

        return code.charAt(0) == 'R'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isUpperCase(code.charAt(3));
    }

    public static int getMembersEnrolled() {
        return membersEnrolled;
    }

    public static String processNightlyAudit(AuditMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int faculty = 0;
        int regular = 0;

        for (AuditMember member : members) {
            if (member == null) {
                nullSkipped++;
            } else {
                processed++;

                if (member instanceof AuditFacultyMember) {
                    faculty++;
                } else {
                    regular++;
                }
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + faculty + " faculty | "
                + regular + " regular";
    }
}

class AuditFacultyMember extends AuditMember {

    private String department;

    public AuditFacultyMember(int borrowLimit, String department) {
        super(borrowLimit);
        this.department = department;
    }
}
