public class BookCopyCirculationGuard {

    private int copiesTotal;
    private int copiesAvailable;

    public BookCopyCirculationGuard(int copiesTotal) {

        if (copiesTotal <= 0) {
            throw new IllegalArgumentException("Copies must be positive");
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    public void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public static void main(String[] args) {

        BookCopyCirculationGuard book =
                new BookCopyCirculationGuard(3);

        book.checkOut();
        book.checkOut();
        book.checkOut();
        book.checkOut();

        System.out.println("After checkouts: "
                + book.getCopiesAvailable());

        book.checkIn();
        book.checkIn();
        book.checkIn();
        book.checkIn();

        System.out.println("After check-ins: "
                + book.getCopiesAvailable());
    }
}
