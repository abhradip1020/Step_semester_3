import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MergingTwoTokenQueues {

    public static void main(String[] args) {
        List<Integer> counterA1 = Arrays.asList(3, 8, 15, 20);
        List<Integer> counterB1 = Arrays.asList(5, 8, 12);
        System.out.println(mergeTokens(counterA1, counterB1));

        List<Integer> counterA2 = Collections.emptyList();
        List<Integer> counterB2 = Arrays.asList(4, 9);
        System.out.println(mergeTokens(counterA2, counterB2));
    }

    public static List<Integer> mergeTokens(List<Integer> counterA, List<Integer> counterB) {
        List<Integer> merged = new ArrayList<>();
        int i = 0;
        int j = 0;
        int sizeA = counterA != null ? counterA.size() : 0;
        int sizeB = counterB != null ? counterB.size() : 0;

        while (i < sizeA && j < sizeB) {
            int valA = counterA.get(i);
            int valB = counterB.get(j);

            if (valA <= valB) {
                merged.add(valA);
                i++;
            } else {
                merged.add(valB);
                j++;
            }
        }

        while (i < sizeA) {
            merged.add(counterA.get(i));
            i++;
        }

        while (j < sizeB) {
            merged.add(counterB.get(j));
            j++;
        }

        return merged;
    }
}
