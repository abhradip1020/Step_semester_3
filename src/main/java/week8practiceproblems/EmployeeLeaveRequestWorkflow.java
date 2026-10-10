// Implement: Employee Leave Request Workflow

public class EmployeeLeaveRequestWorkflow {

    public static void main(String[] args) {
        FullTimeEmployee john = new FullTimeEmployee("E01", "John", new StandardFullTimeLeavePolicy());
        PartTimeEmployee jane = new PartTimeEmployee("E02", "Jane", new StandardPartTimeLeavePolicy());

        Manager alice = new Manager("M01", "Alice");
        Manager bob = new Manager("M02", "Bob");

        // FullTimeEmployee John submits leave request for 5 days (Jan 1-5)
        LeaveRequest johnRequest = john.submitLeaveRequest("Jan 1-5", 5);

        // Manager Alice reviews John's request and approves it
        alice.reviewRequest(johnRequest, true);

        // PartTimeEmployee Jane submits leave request for 2 days (Feb 10-11)
        LeaveRequest janeRequest = jane.submitLeaveRequest("Feb 10-11", 2);

        // Manager Bob reviews Jane's request and rejects it
        bob.reviewRequest(janeRequest, false);

        // John attempts to change his approved leave request to Pending
        johnRequest.setStatus(LeaveStatus.PENDING);
    }
}

enum LeaveStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String displayName;

    LeaveStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

interface LeavePolicy {
    boolean validateLeave(Employee employee, int days);
    int getMaxAllowedDays();
}

class StandardFullTimeLeavePolicy implements LeavePolicy {
    @Override
    public boolean validateLeave(Employee employee, int days) {
        return days <= 30;
    }

    @Override
    public int getMaxAllowedDays() {
        return 30;
    }
}

class StandardPartTimeLeavePolicy implements LeavePolicy {
    @Override
    public boolean validateLeave(Employee employee, int days) {
        return days <= 15;
    }

    @Override
    public int getMaxAllowedDays() {
        return 15;
    }
}

class ContractorLeavePolicy implements LeavePolicy {
    @Override
    public boolean validateLeave(Employee employee, int days) {
        return days <= 5;
    }

    @Override
    public int getMaxAllowedDays() {
        return 5;
    }
}

abstract class Employee {
    private String id;
    private String name;
    private LeavePolicy leavePolicy;

    public Employee(String id, String name, LeavePolicy leavePolicy) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be blank");
        }
        this.id = id;
        this.name = name;
        this.leavePolicy = leavePolicy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LeavePolicy getLeavePolicy() {
        return leavePolicy;
    }

    public LeaveRequest submitLeaveRequest(String dateRange, int days) {
        if (leavePolicy != null && !leavePolicy.validateLeave(this, days)) {
            throw new IllegalArgumentException("Requested days exceed policy limit for " + name);
        }
        LeaveRequest request = new LeaveRequest(this, dateRange, days);
        System.out.printf("Leave request submitted for %s (%s). Status: %s.%n",
                name, dateRange, request.getStatus().getDisplayName());
        return request;
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String id, String name, LeavePolicy leavePolicy) {
        super(id, name, leavePolicy);
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String id, String name, LeavePolicy leavePolicy) {
        super(id, name, leavePolicy);
    }
}

class Contractor extends Employee {
    public Contractor(String id, String name, LeavePolicy leavePolicy) {
        super(id, name, leavePolicy);
    }
}

class LeaveRequest {
    private Employee employee;
    private String dateRange;
    private int durationDays;
    private LeaveStatus status;

    public LeaveRequest(Employee employee, String dateRange, int durationDays) {
        this.employee = employee;
        this.dateRange = dateRange;
        this.durationDays = durationDays;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getDateRange() {
        return dateRange;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus newStatus) {
        if ((this.status == LeaveStatus.APPROVED || this.status == LeaveStatus.REJECTED)
                && newStatus == LeaveStatus.PENDING) {
            System.out.printf("Cannot change leave request status from %s to Pending.%n",
                    this.status.getDisplayName());
            return;
        }
        this.status = newStatus;
    }

    public void approve() {
        if (this.status != LeaveStatus.PENDING) {
            System.out.printf("Cannot change leave request status from %s to Approved.%n",
                    this.status.getDisplayName());
            return;
        }
        this.status = LeaveStatus.APPROVED;
    }

    public void reject() {
        if (this.status != LeaveStatus.PENDING) {
            System.out.printf("Cannot change leave request status from %s to Rejected.%n",
                    this.status.getDisplayName());
            return;
        }
        this.status = LeaveStatus.REJECTED;
    }
}

class Manager {
    private String id;
    private String name;

    public Manager(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void reviewRequest(LeaveRequest request, boolean approve) {
        if (approve) {
            request.approve();
            System.out.printf("%s's leave request (%s) approved. Status: %s.%n",
                    request.getEmployee().getName(), request.getDateRange(), request.getStatus().getDisplayName());
        } else {
            request.reject();
            System.out.printf("%s's leave request (%s) rejected. Status: %s.%n",
                    request.getEmployee().getName(), request.getDateRange(), request.getStatus().getDisplayName());
        }
    }
}
