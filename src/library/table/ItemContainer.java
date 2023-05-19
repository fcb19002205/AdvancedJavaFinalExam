package library.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemContainer<T extends LibraryItems> {

    private Map<Integer, T> items = new HashMap<>();

    public void addItem(T item) {
        items.put(item.getId(), item);
    }

    public void remove(int id) {
        items.remove(id);
    }

    public T getItem(int id) {
        return items.get(id);
    }

    public List<T> getAllItems() {

        return new ArrayList<>(items.values());
    }

}
