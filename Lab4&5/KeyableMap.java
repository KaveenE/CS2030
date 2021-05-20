import java.util.Map;
import java.util.HashMap;

public class KeyableMap<T extends Keyable> implements Keyable {

    private final String key;
    private final Map<String, T> mapping;

    public KeyableMap(String key) {
        this.key = key;
        mapping = new HashMap<>();
    }

    //Copy constructor. Totally irrelevant since map is mutable
    private KeyableMap(KeyableMap<T> another) {
        this.key = another.key;
        this.mapping = new HashMap<>(another.mapping);
    }

    private KeyableMap<T> copy() {
        return new KeyableMap<>(this);
    }

    @Override
    public String getKey() {
        return key;
    }

    KeyableMap<T> put(T item) {
        //Initially, I made it immutable out of habit
        //KeyableMap<T> copy = copy();
        //copy.mapping.put(item.getKey(),item);
        //return copy;

        mapping.put(item.getKey(),item);
        return this;
    }

    public T get(String itemName) {
        return mapping.get(itemName);
    }

    private String stringOfMap() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,T> e: mapping.entrySet()) {
            sb.append(e.getValue() + ", ");
        }

        String newString = sb.toString().strip();
        
        if (!newString.isBlank()) {
            newString = newString.substring(0,newString.length() - 1);
        }

        return newString;
    }

    @Override
    public String toString() {
        return String.format("%s: {%s}",key, stringOfMap());
    }

}
