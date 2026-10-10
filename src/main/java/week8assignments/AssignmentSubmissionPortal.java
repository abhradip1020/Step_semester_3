import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AssignmentSubmissionPortal {

    public static void main(String[] args) {
        LocalDate mar10 = LocalDate.of(2026, 3, 10);
        LocalDate mar12 = LocalDate.of(2026, 3, 12);
        LocalDate mar14 = LocalDate.of(2026, 3, 14);

        Assignment linkedListLab = new CodingAssignment("Linked List Lab", 50, mar10);
        Assignment designEssay = new WrittenAssignment("Design Essay", 50, mar12);

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Submission ashaSubmission = linkedListLab.submit(asha, mar10);
        Submission raviSubmission = designEssay.submit(ravi, mar14);

        if (ashaSubmission != null) {
            ashaSubmission.grade(45);
        }

        if (raviSubmission != null) {
            raviSubmission.grade(40);
        }

        linkedListLab.submit(asha, mar14);
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

enum SubmissionStatus {
    SUBMITTED("Submitted"),
    GRADED("Graded");

    private final String displayName;

    SubmissionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

abstract class Assignment {
    private String title;
    private int maxMarks;
    private LocalDate dueDate;
    private Submission currentSubmission;

    public Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public abstract double getDailyPenaltyPercentage();

    public Submission submit(Student student, LocalDate submissionDate) {
        if (currentSubmission != null && currentSubmission.getStatus() == SubmissionStatus.GRADED) {
            System.out.printf("Cannot resubmit: '%s' has already been graded.%n", title);
            return null;
        }

        long lateDays = ChronoUnit.DAYS.between(dueDate, submissionDate);
        if (lateDays < 0) {
            lateDays = 0;
        }

        currentSubmission = new Submission(student, this, submissionDate, (int) lateDays);

        String timing = (lateDays == 0) ? "(on time)" : "(" + lateDays + " days late)";
        System.out.printf("%s's submission for '%s' received %s. Status: %s.%n",
                student.getName(), title, timing, currentSubmission.getStatus().getDisplayName());

        return currentSubmission;
    }
}

class CodingAssignment extends Assignment {
    public CodingAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public double getDailyPenaltyPercentage() {
        return 0.10;
    }
}

class WrittenAssignment extends Assignment {
    public WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public double getDailyPenaltyPercentage() {
        return 0.20;
    }
}

class Submission {
    private Student student;
    private Assignment assignment;
    private LocalDate submissionDate;
    private int lateDays;
    private SubmissionStatus status;
    private int finalMarks;

    public Submission(Student student, Assignment assignment, LocalDate submissionDate, int lateDays) {
        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.lateDays = lateDays;
        this.status = SubmissionStatus.SUBMITTED;
    }

    public Student getStudent() {
        return student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public int getFinalMarks() {
        return finalMarks;
    }

    public void grade(int rawMarks) {
        if (status == SubmissionStatus.GRADED) {
            return;
        }

        double penaltyRate = lateDays * assignment.getDailyPenaltyPercentage();
        if (penaltyRate > 1.0) {
            penaltyRate = 1.0;
        }

        int penaltyPercent = (int) Math.round(penaltyRate * 100);
        double deduction = rawMarks * penaltyRate;
        this.finalMarks = (int) Math.round(rawMarks - deduction);
        this.status = SubmissionStatus.GRADED;

        if (penaltyPercent > 0) {
            System.out.printf("%s graded: %d/%d after %d%% late penalty. Status: %s.%n",
                    student.getName(), finalMarks, assignment.getMaxMarks(),
                    penaltyPercent, status.getDisplayName());
        } else {
            System.out.printf("%s graded: %d/%d. Status: %s.%n",
                    student.getName(), finalMarks, assignment.getMaxMarks(),
                    status.getDisplayName());
        }
    }
}
