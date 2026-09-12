import java.util.Scanner;

public class PinLengthValidator {

    void checkPinLength(String pin) {
        if (pin.length() != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        PinLengthValidator obj = new PinLengthValidator();
        obj.checkPinLength(pin);

        sc.close();
    }
}
