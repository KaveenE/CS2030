package Lab5;

import java.util.Optional;

public class Roster extends KeyableMap<Student> {
    public Roster(String rosterName) {
        super(rosterName);
    }
    
    String getGrade(String studentName, String moduleName, String assessmentName) {
        
        String error = String.format("No such record: %s %s %s",
                                      studentName, moduleName, assessmentName);

        Optional<Student> student = this.get(studentName);
        
        //student->module->assessment->getGrade().
        String grade = student.flatMap(s -> s.get(moduleName))
                              .flatMap(m -> m.get(assessmentName))
                              .map(a -> a.getGrade()).orElse(error);

        
        return grade;
    }

    @Override
    Roster put(Student item) {
        return (Roster)super.put(item);
    }
}
