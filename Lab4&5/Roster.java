import java.util.Map;
import java.util.HashMap;

public class Roster extends KeyableMap<Student> {
    public Roster(String rosterName) {
        super(rosterName);
    }
    
    String getGrade(String studentName, String moduleName, String assessmentName) {
        
        String error = String.format("No such record: %s %s %s",
                                      studentName, moduleName, assessmentName);
        Student s = this.get(studentName);
        if (s == null) {
            return error;
        }

        Module m = s.get(moduleName);
        if (m == null) { 
            return error;
        }

        Assessment a = m.get(assessmentName);
        if (a == null) {
            return error;
        }
        
        return a.getGrade();
    }

    @Override
    Roster put(Student item) {
        return (Roster)super.put(item);
    }
}
