import java.util.Map;
import java.util.HashMap;

public class Module extends KeyableMap<Assessment> implements Keyable {

    public Module(String moduleCode) {
        super(moduleCode);
    }

    @Override
    Module put(Assessment item) {
        return (Module)super.put(item);
    }

}
