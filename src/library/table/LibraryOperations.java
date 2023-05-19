package library.table;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

public class LibraryOperations {

    public static OptionalDouble getAveragePublicationYear(List<LibraryItems> items) {
        return items.stream()
                .mapToInt(LibraryItems::getYearPublished)
                .average();
    }

    public static LibraryItems getOldestItem(List<LibraryItems> items) {
        return items.stream()
                .min(Comparator.comparing(LibraryItems::getYearPublished))
                .orElse(null);
    }

    public static List<LibraryItems> searchItemsByTitle(List<LibraryItems> items, String keyword) {
        return items.stream()
                .filter(item -> item.getTitle().contains(keyword))
                .toList();
    }

}
