public class HotWeatherAlertWindows {

    public static void main(String[] args) {
        int[] readings = {2, 2, 2, 2, 5, 5, 5, 8};
        int k = 3;
        int threshold = 4;

        int result = countAlerts(readings, k, threshold);
        System.out.println(result);
    }

    public static int countAlerts(int[] readings, int k, int threshold) {
        if (readings == null || readings.length < k || k <= 0) {
            return 0;
        }

        long requiredSum = (long) k * threshold;
        long currentSum = 0;

        for (int i = 0; i < k; i++) {
            currentSum += readings[i];
        }

        int count = 0;
        if (currentSum >= requiredSum) {
            count++;
        }

        for (int i = k; i < readings.length; i++) {
            currentSum += readings[i] - readings[i - k];
            if (currentSum >= requiredSum) {
                count++;
            }
        }

        return count;
    }
}
