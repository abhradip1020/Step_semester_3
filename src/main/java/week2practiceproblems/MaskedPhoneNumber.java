import java.util.Scanner;

public class MaskedPhoneNumber {

    String maskPhoneNumber(String phone) {
        if (phone.length() != 10) {
            return "Invalid phone number";
        }

        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }

        StringBuilder result = new StringBuilder("XXXXXX");
        result.insert(6, "-" + phone.substring(6));

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        MaskedPhoneNumber obj = new MaskedPhoneNumber();

        System.out.println(obj.maskPhoneNumber(phone));

        sc.close();
    }
}
