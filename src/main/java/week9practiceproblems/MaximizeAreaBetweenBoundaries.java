public class MaximizeAreaBetweenBoundaries {

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int result = maxContainerArea(heights);
        System.out.println(result);
    }

    public static int maxContainerArea(int[] heights) {
        if (heights == null || heights.length < 2) {
            return 0;
        }

        int maxArea = 0;
        int left = 0;
        int right = heights.length - 1;

        while (left < right) {
            int currentHeight = Math.min(heights[left], heights[right]);
            int currentWidth = right - left;
            int currentArea = currentHeight * currentWidth;

            if (currentArea > maxArea) {
                maxArea = currentArea;
            }

            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
