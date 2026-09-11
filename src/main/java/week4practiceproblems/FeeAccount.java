class FeeAccount {

    String accountType;

    FeeAccount(String accountType) {
        this.accountType = accountType;
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount() {
        super("Hostel");
    }
}

public class FeeAccountTest {

    static void processPayment(FeeAccount account, double amount) {

        if (account instanceof HostelFeeAccount) {
            System.out.println(
                "Paid in two installments (hostel account)"
            );
        } else {
            System.out.println(
                "Paid in one go (day-scholar account)"
            );
        }
    }

    public static void main(String[] args) {

        FeeAccount[] accounts = {
            new HostelFeeAccount(),
            new HostelFeeAccount(),
            new FeeAccount("Day Scholar"),
            new FeeAccount("Day Scholar")
        };

        double amount = 60000;

        int hostelCount = 0;
        int dayScholarCount = 0;

        for (FeeAccount account : accounts) {

            processPayment(account, amount);

            if (account instanceof HostelFeeAccount) {
                hostelCount++;
            } else {
                dayScholarCount++;
            }
        }

        System.out.println(
            "Hostel accounts processed: " + hostelCount
            + " | Day-scholar accounts processed: " + dayScholarCount
        );
    }
}
