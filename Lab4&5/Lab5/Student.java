package Lab5;

public class Student extends KeyableMap<Module> {

    public Student(String studentName) {
        super(studentName);
    }

    @Override
    Student put(Module item) {
        return (Student)super.put(item);
    }
}
