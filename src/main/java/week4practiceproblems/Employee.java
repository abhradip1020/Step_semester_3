class Employee {

    String id;
    double salary;

    Employee(String id, double salary) {
        this.id = id;
        this.salary = salary;
    }

    void raiseSalary(double salary) {
        this.salary = this.salary + salary;
    }
}

public class EmployeeTest {

    public static void main(String[] args) {

        Employee[] employees = {
            new Employee("E-101", 40000),
            new Employee("E-102", 55000),
            new Employee("E-103", 62000),
            new Employee("E-104", 48000)
        };

        for (Employee employee : employees) {
            employee.raiseSalary(5000);
            System.out.println(
                employee.id + " | Final Salary: Rs " + employee.salary
            );
        }
    }
}
