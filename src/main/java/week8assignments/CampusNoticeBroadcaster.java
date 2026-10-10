import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CampusNoticeBroadcaster {

    public static void main(String[] args) {
        NoticeBoard noticeBoard = new NoticeBoard();

        NotificationChannel email = new EmailChannel();
        NotificationChannel app = new AppChannel();
        NotificationChannel sms = new SmsChannel();

        Student asha = new Student("Asha", "CSE", Arrays.asList(email, app));
        Student ravi = new Student("Ravi", "ECE", Arrays.asList(sms));

        noticeBoard.registerStudent(asha);
        noticeBoard.registerStudent(ravi);

        noticeBoard.postNotice("Lab Closed Tomorrow", Arrays.asList("CSE"));
        noticeBoard.postNotice("Fee Deadline Extended", Arrays.asList("CSE", "ECE"));
        noticeBoard.postNotice("Sports Day", Collections.emptyList());
    }
}

interface NotificationChannel {
    String getName();
    void send(Student student, String noticeTitle);
}

class EmailChannel implements NotificationChannel {
    @Override
    public String getName() {
        return "Email";
    }

    @Override
    public void send(Student student, String noticeTitle) {
        System.out.printf("[Email → %s] %s.%n", student.getName(), noticeTitle);
    }
}

class AppChannel implements NotificationChannel {
    @Override
    public String getName() {
        return "App";
    }

    @Override
    public void send(Student student, String noticeTitle) {
        System.out.printf("[App → %s] %s.%n", student.getName(), noticeTitle);
    }
}

class SmsChannel implements NotificationChannel {
    @Override
    public String getName() {
        return "SMS";
    }

    @Override
    public void send(Student student, String noticeTitle) {
        System.out.printf("[SMS → %s] %s.%n", student.getName(), noticeTitle);
    }
}

class Student {
    private String name;
    private String department;
    private List<NotificationChannel> preferredChannels;

    public Student(String name, String department, List<NotificationChannel> preferredChannels) {
        this.name = name;
        this.department = department;
        this.preferredChannels = new ArrayList<>(preferredChannels);
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public List<NotificationChannel> getPreferredChannels() {
        return preferredChannels;
    }
}

class NoticeBoard {
    private List<Student> students = new ArrayList<>();

    public void registerStudent(Student student) {
        students.add(student);
    }

    public void postNotice(String title, List<String> targetDepartments) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Cannot post notice: Title is required.");
            return;
        }

        if (targetDepartments == null || targetDepartments.isEmpty()) {
            System.out.println("Cannot post notice: At least one target department is required.");
            return;
        }

        String deptList = String.join(", ", targetDepartments);
        System.out.printf("Notice '%s' posted to %s.%n", title, deptList);

        for (Student student : students) {
            if (targetDepartments.contains(student.getDepartment())) {
                for (NotificationChannel channel : student.getPreferredChannels()) {
                    channel.send(student, title);
                }
            }
        }
    }
}
