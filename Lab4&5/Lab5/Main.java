package Lab5;

import java.util.Scanner;

public class Main {

    private static final int THREE = 3;
    private static final int FOUR = 4;
    /**
    * Answer queries based on contents of roster.
    * @params Dummy
    */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numRecords = scanner.nextInt();
        scanner.nextLine(); //To read the "\n" remaining on buffer

        Roster roster = new Roster("AY2021");

        //Auxillary variables
        String[] split;

        Student student;
        String studentName;

        Module module;
        String moduleName;

        //Core reading logic
        int lines = 0;
        split = scanner.nextLine().replaceAll(" +"," ").split(" ");
        lines++;
        while (lines <= numRecords - 1) {

            studentName = split[0];
            student = new Student(studentName);

            while (studentName.equals(split[0])) {

                moduleName = split[1];
                module = new Module(moduleName);

                //First condition is to break loop when we spill into the input meant for queries
                while (split.length == FOUR && moduleName.equals(split[1])) {

                    module.put(new Assessment(split[2], split[THREE]));
                    split = scanner.nextLine().replaceAll(" +"," ").split(" ");
                    lines++;
                }

                student.put(module);
            }

            //We're done with this student
            roster.put(student);
        }



        String assessmentName;
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine()) {
            studentName = split[0];
            moduleName = split[1];
            assessmentName = split[2];

            sb.append(roster.getGrade(studentName, moduleName, assessmentName) + "\n");
            split = scanner.nextLine().replaceAll(" +"," ").split(" ");
        }

        sb.append(roster.getGrade(split[0], split[1], split[2]));
        System.out.println(sb.toString().strip());
        scanner.close();
    }

}
