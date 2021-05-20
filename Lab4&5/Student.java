import java.util.Map;
import java.util.HashMap;

public class Student extends KeyableMap<Module> {

    public Student(String studentName) {
        super(studentName);
    }

    @Override
    Student put(Module item) {
        return (Student)super.put(item);
    }
}
