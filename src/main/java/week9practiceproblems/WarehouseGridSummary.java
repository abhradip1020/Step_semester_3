public class WarehouseGridSummary {

    public static void main(String[] args) {
        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };

        GridSummaryResult result = warehouseSummary(grid);
        System.out.println(result);
    }

    public static GridSummaryResult warehouseSummary(int[][] grid) {
        int totalItems = 0;
        int maxItems = -1;
        int maxRow = 0;
        int maxCol = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int current = grid[r][c];
                totalItems += current;
                if (current > maxItems) {
                    maxItems = current;
                    maxRow = r;
                    maxCol = c;
                }
            }
        }

        return new GridSummaryResult(totalItems, maxRow, maxCol);
    }
}

class GridSummaryResult {
    private int totalItems;
    private int maxRow;
    private int maxCol;

    public GridSummaryResult(int totalItems, int maxRow, int maxCol) {
        this.totalItems = totalItems;
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxCol() {
        return maxCol;
    }

    @Override
    public String toString() {
        return "(" + totalItems + ", (" + maxRow + ", " + maxCol + "))";
    }
}
