import java.util.Scanner;

public class BankTransactionReference {

    String normalizeReference(String raw) {
        raw = raw.trim();

        if (raw.length() < 3) {
            return raw;
        }

        String bankCode = raw.substring(0, 3).toUpperCase();
        String rest = raw.substring(3);

        return bankCode + rest;
    }

    String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        String bankCode = reference.substring(0, 3);
        String body = reference.substring(3);

        for (int i = 0; i < bankCode.length(); i++) {
            if (!Character.isLetter(bankCode.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        String date = body.substring(0, 6);
        String sequence = body.substring(6);

        StringBuilder result = new StringBuilder();
        result.append("[");
        result.append(bankCode);
        result.append("] DATE: ");
        result.append(date.substring(0, 2));
        result.append("/");
        result.append(date.substring(2, 4));
        result.append("/");
        result.append(date.substring(4, 6));
        result.append(" | SEQ: ");
        result.append(sequence);

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter transaction reference: ");
        String raw = sc.nextLine();

        BankTransactionReference obj = new BankTransactionReference();

        String normalized = obj.normalizeReference(raw);

        System.out.println(obj.validateAndFormat(normalized));

        sc.close();
    }
}
