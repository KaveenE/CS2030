package Lab5;

public class Assessment implements Keyable {

    private final String assessmentName;
    private final String grade;

    public Assessment(String assessmentName, String grade) {
        this.assessmentName = assessmentName;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String getKey() {
        return assessmentName;
    }
    
    
    @Override
    public String toString() {
        return String.format("{%s: %s}",assessmentName, grade);
    }

}
