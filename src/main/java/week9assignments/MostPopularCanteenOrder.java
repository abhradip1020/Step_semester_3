import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostPopularCanteenOrder {

    public static void main(String[] args) {
        List<String> orders1 = Arrays.asList("dosa", "idli", "vada", "dosa", "idli", "dosa", "tea");
        System.out.println(mostPopular(orders1));

        List<String> orders2 = Arrays.asList("tea", "coffee", "coffee", "tea");
        System.out.println(mostPopular(orders2));
    }

    public static PopularItemResult mostPopular(List<String> orders) {
        if (orders == null || orders.isEmpty()) {
            return new PopularItemResult("", 0);
        }

        Map<String, Integer> frequencyMap = new HashMap<>();
        int maxCount = 0;

        for (String item : orders) {
            int count = frequencyMap.getOrDefault(item, 0) + 1;
            frequencyMap.put(item, count);
            if (count > maxCount) {
                maxCount = count;
            }
        }

        for (String item : orders) {
            if (frequencyMap.get(item) == maxCount) {
                return new PopularItemResult(item, maxCount);
            }
        }

        return new PopularItemResult("", 0);
    }
}

class PopularItemResult {
    private String item;
    private int count;

    public PopularItemResult(String item, int count) {
        this.item = item;
        this.count = count;
    }

    public String getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "(\"" + item + "\", " + count + ")";
    }
}
