public class CirculationReport {

    static String batchPrint(ReportMember[] members) {
        StringBuilder report = new StringBuilder();

        for (ReportMember member : members) {
            member.displayInfo();

            if (member instanceof ReportStudentMember) {
                ReportStudentMember student = (ReportStudentMember) member;

                report.append("Student | Course: ")
                        .append(student.getCourse())
                        .append(" | Books: ")
                        .append(student.getBooksBorrowed())
                        .append(" [Course via downcast: ")
                        .append(student.getCourse())
                        .append("] | ");
            } else {
                report.append("General | Books: ")
                        .append(member.getBooksBorrowed())
                        .append(" | ");
            }
        }

        return report.toString();
    }

    public static void main(String[] args) {
        ReportMember general =
                new ReportMember("LB5", 3);

        ReportStudentMember student =
                new ReportStudentMember("STU6", 3, "ECE");

        ReportMember[] members = {general, student};

        System.out.println(batchPrint(members));
    }
}

class ReportMember {

    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;

    public ReportMember(String memberId, int borrowLimit) {
        this.memberId = memberId;
        this.borrowLimit = borrowLimit;
        this.booksBorrowed = 0;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public void displayInfo() {
        System.out.println("General Member | Books Borrowed: " + booksBorrowed);
    }
}

class ReportStudentMember extends ReportMember {

    private String course;

    public ReportStudentMember(String memberId, int borrowLimit,
                               String course) {
        super(memberId, borrowLimit);
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public void displayInfo() {
        System.out.println("Student Member | Course: " + course
                + " | Books Borrowed: " + booksBorrowed);
    }
}
