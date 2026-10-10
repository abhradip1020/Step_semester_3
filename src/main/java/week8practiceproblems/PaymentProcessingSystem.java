// Implement: Payment Processing for a Shopping System

import java.util.ArrayList;
import java.util.List;

public class PaymentProcessingSystem {

    public static void main(String[] args) {
        Customer customerX = new Customer("C-X", "Customer X");
        Customer customerY = new Customer("C-Y", "Customer Y");
        Customer customerZ = new Customer("C-Z", "Customer Z");

        Product productA = new Product("P-A", "Product A", 25.0);
        Product productB = new Product("P-B", "Product B", 50.0);
        Product productC = new Product("P-C", "Product C", 40.0);

        // Customer X creates an order with Product A (qty 2) and Product B (qty 1)
        Order orderX = new Order("Order X", customerX);
        orderX.addItem(productA, 2);
        orderX.addItem(productB, 1);
        System.out.printf("Order created for %s.%n", customerX.getName());

        // Customer X attempts to pay for the order using Credit Card (successful)
        PaymentMethod creditCard = new CreditCardPayment("4111-2222-3333-4444", true);
        orderX.processPayment(creditCard);

        // Customer Y creates an empty order
        Order orderY = new Order("Order Y", customerY);
        // Customer Y attempts to pay for the empty order
        orderY.processPayment(creditCard);

        // Customer Z creates an order with Product C (qty 1)
        Order orderZ = new Order("Order Z", customerZ);
        orderZ.addItem(productC, 1);
        System.out.printf("Order created for %s.%n", customerZ.getName());

        // Customer Z attempts to pay for the order using PayPal (fails)
        PaymentMethod payPal = new PayPalPayment("user@example.com", false);
        orderZ.processPayment(payPal);
    }
}

class Customer {
    private String id;
    private String name;

    public Customer(String id, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be blank");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}

enum OrderStatus {
    PENDING("Pending"),
    PAID("Paid");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

interface PaymentMethod {
    String getMethodName();
    boolean processPayment(double amount);
}

class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private boolean simulateSuccess;

    public CreditCardPayment(String cardNumber, boolean simulateSuccess) {
        this.cardNumber = cardNumber;
        this.simulateSuccess = simulateSuccess;
    }

    @Override
    public String getMethodName() {
        return "Credit Card";
    }

    @Override
    public boolean processPayment(double amount) {
        return simulateSuccess;
    }
}

class PayPalPayment implements PaymentMethod {
    private String email;
    private boolean simulateSuccess;

    public PayPalPayment(String email, boolean simulateSuccess) {
        this.email = email;
        this.simulateSuccess = simulateSuccess;
    }

    @Override
    public String getMethodName() {
        return "PayPal";
    }

    @Override
    public boolean processPayment(double amount) {
        return simulateSuccess;
    }
}

class BankTransferPayment implements PaymentMethod {
    private String bankAccountNumber;
    private boolean simulateSuccess;

    public BankTransferPayment(String bankAccountNumber, boolean simulateSuccess) {
        this.bankAccountNumber = bankAccountNumber;
        this.simulateSuccess = simulateSuccess;
    }

    @Override
    public String getMethodName() {
        return "Bank Transfer";
    }

    @Override
    public boolean processPayment(double amount) {
        return simulateSuccess;
    }
}

class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
    }

    public double calculateTotal() {
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public boolean processPayment(PaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println("Cannot process payment for an empty order.");
            return false;
        }

        System.out.printf("Payment initiated via %s for %s.%n",
                paymentMethod.getMethodName(), orderId);

        boolean success = paymentMethod.processPayment(calculateTotal());
        if (success) {
            this.status = OrderStatus.PAID;
            System.out.printf("Payment for %s successful. Order status: %s.%n",
                    orderId, status.getDisplayName());
            return true;
        } else {
            System.out.printf("Payment for %s failed. Order status: %s.%n",
                    orderId, status.getDisplayName());
            return false;
        }
    }
}
