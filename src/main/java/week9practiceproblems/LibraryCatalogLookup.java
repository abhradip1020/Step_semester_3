import java.util.ArrayList;
import java.util.List;

public class LibraryCatalogLookup {

    public static void main(String[] args) {
        List<BookRecord> catalog = new ArrayList<>();
        catalog.add(new BookRecord("0001112223", "Introduction to Algebra"));
        catalog.add(new BookRecord("0002223334", "Beginning Python"));
        catalog.add(new BookRecord("0003334445", "Classic Mythology"));
        catalog.add(new BookRecord("0004445556", "Data and Society"));
        catalog.add(new BookRecord("0005556667", "European History"));

        String result1 = findBook(catalog, "0003334445");
        System.out.println(result1);

        String result2 = findBook(catalog, "0009998887");
        System.out.println(result2);
    }

    public static String findBook(List<BookRecord> catalog, String targetIsbn) {
        int low = 0;
        int high = catalog.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            BookRecord current = catalog.get(mid);
            int cmp = current.getIsbn().compareTo(targetIsbn);

            if (cmp == 0) {
                return current.getTitle();
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return "Not Found";
    }
}

class BookRecord {
    private String isbn;
    private String title;

    public BookRecord(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }
}
