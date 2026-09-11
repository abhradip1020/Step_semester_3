import java.util.Arrays;

final class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;
        this.bookIds = bookIds.clone();
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone();
    }

    public LoanReceipt withUpdatedBook(int index, String newBookId) {

        String[] updatedBooks = bookIds.clone();
        updatedBooks[index] = newBookId;

        return new LoanReceipt(memberId, updatedBooks);
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private String referenceRoom;

    public ReferenceOnlyLoanReceipt(String memberId,
                                    String[] bookIds,
                                    String referenceRoom) {
        super(memberId, bookIds);
        this.referenceRoom = referenceRoom;
    }

    public String getReferenceRoom() {
        return referenceRoom;
    }
}

public class LoanReceiptTest {

    static String processNightlyCirculation(LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        String[] books = {"BK-200"};

        LoanReceipt[] receipts = {
                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        books,
                        "Reading Room 3"
                ),
                null,
                new LoanReceipt(
                        "LIB-002",
                        new String[]{"BK-201"}
                )
        };

        System.out.println(
                processNightlyCirculation(receipts)
        );
    }
}
