package library.table;

import java.util.*;
import java.util.function.Predicate;

public class LibraryUtils {

    public static List<LibraryItems> sortByTitle(List<LibraryItems> items) {
        Comparator<LibraryItems> titleComparator = Comparator.comparing(LibraryItems::getTitle);
        items.sort(titleComparator);
        return items;
    }

    public static List<LibraryItems> sortByYearPublished(List<LibraryItems> items) {
        Comparator<LibraryItems> yearPublishedComparator = Comparator.comparing(LibraryItems::getYearPublished);
        items.sort(yearPublishedComparator);
        return items;
    }

    public static List<LibraryItems> filterByYearPublished(List<LibraryItems> items, int year) {
        Predicate<LibraryItems> yearPublishedFilter = item -> item.getYearPublished() > year;
        List<LibraryItems> filteredItems = new ArrayList<>();
        for (LibraryItems item : items) {
            if (yearPublishedFilter.test(item)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

}
