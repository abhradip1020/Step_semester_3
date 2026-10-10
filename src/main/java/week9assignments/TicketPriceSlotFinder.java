public class TicketPriceSlotFinder {

    public static void main(String[] args) {
        int[] prices = {120, 150, 200, 260};

        System.out.println(findSlot(prices, 150));
        System.out.println(findSlot(prices, 210));
        System.out.println(findSlot(prices, 300));
    }

    public static int findSlot(int[] prices, int newPrice) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int low = 0;
        int high = prices.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (prices[mid] == newPrice) {
                return mid;
            } else if (prices[mid] < newPrice) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return low;
    }
}
