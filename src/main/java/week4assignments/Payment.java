public class Payment {

    public double pay(double amount) {
        System.out.println("Paid (cash): Rs " + amount);
        return amount;
    }

    public static void processTransaction(Payment payment, double amount) {

        if (payment instanceof CardPayment) {

            CardPayment cardPayment = (CardPayment) payment;

            cardPayment.payWithProcessingFee(amount);

        } else {

            payment.pay(amount);
        }
    }

    public static void main(String[] args) {

        Payment[] payments = {
            new CardPayment(),
            new Payment(),
            new CardPayment(),
            new Payment(),
            new CardPayment()
        };

        double[] amounts = {
            100, 50, 200, 75, 120
        };

        double totalCollected = 0;

        for (int i = 0; i < payments.length; i++) {

            if (payments[i] instanceof CardPayment) {

                double charged = amounts[i] * 1.02;

                processTransaction(payments[i], amounts[i]);

                totalCollected += charged;

            } else {

                processTransaction(payments[i], amounts[i]);

                totalCollected += amounts[i];
            }
        }

        System.out.println(
            "Total Collected: Rs " + totalCollected
        );
    }
}

class CardPayment extends Payment {

    public double payWithProcessingFee(double amount) {

        double total = amount * 1.02;

        System.out.println(
            "Charged (card, incl. fee): Rs " + total
        );

        return total;
    }
}
