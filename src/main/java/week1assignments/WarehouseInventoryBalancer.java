import java.util.Scanner;

public class WarehouseInventoryBalancer {

    void analyzeInventory(int[] sectionA, int[] sectionB) {
        int totalA = 0;
        int totalB = 0;

        int highestQuantity = sectionA[0];
        String highestSection = "Section A";
        int highestIndex = 0;

        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];

            if (sectionA[i] > highestQuantity) {
                highestQuantity = sectionA[i];
                highestSection = "Section A";
                highestIndex = i;
            }
        }

        for (int i = 0; i < sectionB.length; i++) {
            totalB += sectionB[i];

            if (sectionB[i] > highestQuantity) {
                highestQuantity = sectionB[i];
                highestSection = "Section B";
                highestIndex = i;
            }
        }

        String status;

        if (totalA == totalB) {
            status = "Balanced";
        } else {
            status = "Not Balanced";
        }

        System.out.println("Section A Total: " + totalA
                + " | Section B Total: " + totalB
                + " | Status: " + status);

        System.out.println("Highest Quantity: " + highestQuantity
                + " (" + highestSection + ", Item " + (highestIndex + 1) + ")");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        int[] sectionA = new int[n];
        int[] sectionB = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter quantity for Section A, Item " + (i + 1) + ": ");
            sectionA[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            System.out.print("Enter quantity for Section B, Item " + (i + 1) + ": ");
            sectionB[i] = sc.nextInt();
        }

        WarehouseInventoryBalancer obj = new WarehouseInventoryBalancer();
        obj.analyzeInventory(sectionA, sectionB);

        sc.close();
    }
}
