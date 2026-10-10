public class ClassTopperFinder {

    public static void main(String[] args) {
        int[][] marks = {
            {78, 85, 90},
            {88, 92, 79},
            {65, 70, 95}
        };

        TopperResult result = findTopper(marks);
        System.out.println(result);
    }

    public static TopperResult findTopper(int[][] marks) {
        if (marks == null || marks.length == 0) {
            return new TopperResult(-1, 0);
        }

        int bestRow = 0;
        int maxTotal = -1;

        for (int r = 0; r < marks.length; r++) {
            int currentTotal = 0;
            for (int c = 0; c < marks[r].length; c++) {
                currentTotal += marks[r][c];
            }

            if (currentTotal > maxTotal) {
                maxTotal = currentTotal;
                bestRow = r;
            }
        }

        return new TopperResult(bestRow, maxTotal);
    }
}

class TopperResult {
    private int rowIndex;
    private int total;

    public TopperResult(int rowIndex, int total) {
        this.rowIndex = rowIndex;
        this.total = total;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "(" + rowIndex + ", " + total + ")";
    }
}
