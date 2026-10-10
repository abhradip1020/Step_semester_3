import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PairWithTargetSumUnsortedArray {

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;

        int[] nums2 = {3, 4, 6};
        int target2 = 20;

        System.out.println(hasPairWithSumHashSet(nums1, target1));
        System.out.println(hasPairWithSumTwoPointers(nums1, target1));

        System.out.println(hasPairWithSumHashSet(nums2, target2));
        System.out.println(hasPairWithSumTwoPointers(nums2, target2));
    }

    public static boolean hasPairWithSumHashSet(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            int complement = target - num;
            if (seen.contains(complement)) {
                return true;
            }
            seen.add(num);
        }

        return false;
    }

    public static boolean hasPairWithSumTwoPointers(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);

        int left = 0;
        int right = sorted.length - 1;

        while (left < right) {
            int currentSum = sorted[left] + sorted[right];
            if (currentSum == target) {
                return true;
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }

        return false;
    }

    public static boolean hasPairWithSumBruteForce(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }

        return false;
    }
}
