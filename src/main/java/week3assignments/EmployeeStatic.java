class EmployeeStatic {

    String empName;
    double salary;

    static String companyName = "Bright Horizon Technologies";
    static int employeeCount = 0;

    EmployeeStatic(String empName, double salary) {
        this.empName = empName;
        this.salary = salary;
        employeeCount++;
    }

    static void printCompanyInfo() {
        System.out.println(companyName);
        System.out.println("Employees on record: " + employeeCount);
    }
}

class EmployeeStaticTest {

    public static void main(String[] args) {

        EmployeeStatic employee1 =
            new EmployeeStatic("Rahul", 50000);

        EmployeeStatic employee2 =
            new EmployeeStatic("Priya", 60000);

        EmployeeStatic employee3 =
            new EmployeeStatic("Arjun", 55000);

        System.out.println("3 Employee objects created");

        EmployeeStatic.printCompanyInfo();
    }
}
