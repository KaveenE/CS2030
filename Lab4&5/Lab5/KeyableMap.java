package Lab5;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

//This class is mutable. No immutability-related stuff is enforced.
public class KeyableMap<T extends Keyable> implements Keyable {

    private final String key;
    private final Map<String, T> mapping;

    public KeyableMap(String key) {
        this.key = key;
        mapping = new HashMap<>();
    }

    @Override
    public String getKey() {
        return key;
    }

    KeyableMap<T> put(T item) {
        mapping.put(item.getKey(),item);
        return this;
    }

    public Optional<T> get(String itemName) {
        return Optional.ofNullable(mapping.get(itemName));
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
