package ua.com.foxminded.sqljdbcschool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.mifmif.common.regex.Generex;

public class SchoolData {
    private String pathToResources = getClass().getClassLoader().getResource("").getPath();

    public void generate() {
        String groupsSql = pathToResources + "groups.sql";
        String studentsSql = pathToResources + "students.sql";
        String studentsCoursesSql = pathToResources + "students-courses.sql";

        if (Boolean.FALSE.equals(sqlFileExist(groupsSql))) {
            createSqlFile(groupsSql);
            generateGroups(groupsSql);
        }

        if (Boolean.FALSE.equals(sqlFileExist(studentsSql))) {
            createSqlFile(studentsSql);
            generateStudents(studentsSql);
        }

        if (Boolean.FALSE.equals(sqlFileExist(studentsCoursesSql))) {
            createSqlFile(studentsCoursesSql);
            generateStudentsCourses(studentsCoursesSql);
        }
    }

    private Boolean sqlFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    private void createSqlFile(String fileName) {
        try {
            File file = new File(fileName);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateGroups(String groupsSql) {
        Generex generateLetters = new Generex("[a-zA-Z]{2}");
        Generex generateNumbers = new Generex("[0-9]{2}");

        try {
            FileWriter file = new FileWriter(groupsSql);

            for (int i = 1; i < 11; i++) {
                file.write("INSERT INTO groups(group_id, group_name) VALUES(" + i + ", '" + generateLetters.random()
                        + "-" + generateNumbers.random() + "');\n");
            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateStudents(String studentsSql) {
        String firstNamesPath = pathToResources + "first-names.txt";
        String lastNamesPath = pathToResources + "last-names.txt";
        List<String> firstNames = generateNames(firstNamesPath);
        List<String> lastNames = generateNames(lastNamesPath);
        List<Integer> groupsNumber = generateGroupsNumbers();

        try {
            FileWriter file = new FileWriter(studentsSql);
            int j = 0;
            for (int i = 1; i < 201; i++) {
                file.write("INSERT INTO students(student_id, first_name, last_name, group_id) VALUES(" + i + ", '"
                        + firstNames.get(j) + "', '" + lastNames.get(j) + "', " + groupsNumber.get(j) + ");\n");
                j++;
            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<String> generateNames(String pathToFileWithNames) {
        List<String> names = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            names.addAll(readNames(pathToFileWithNames));
        }

        Collections.shuffle(names);
        return names;
    }

    private List<String> readNames(String pathToFileWithNames) {
        List<String> names = new ArrayList<>();
        try {
            File file = new File(pathToFileWithNames);
            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {
                names.add(myReader.nextLine());
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return names;
    }

    private List<Integer> generateGroupsNumbers() {
        List<Integer> groupsNumbers = new ArrayList<>();
        Generex generateNumbers = new Generex("[0-9]");

        for (int i = 0; i < 200; i++) {
            int[] counterGroupNumbers = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            int groupNumber = Integer.parseInt(generateNumbers.random());
            int[] groupNumbers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
            int indexOfNumber = ArrayUtils.indexOf(groupNumbers, groupNumber);
            counterGroupNumbers[indexOfNumber] += 1;

            if (counterGroupNumbers[indexOfNumber] == 30
                    || (i == 199 && IntStream.of(counterGroupNumbers).anyMatch(x -> x < 10))) {
                groupNumber = -1;
            }

            groupsNumbers.add(groupNumber + 1);
        }

        return groupsNumbers;
    }

    private void generateStudentsCourses(String studentsCoursesSql) {
        Generex generateNumberOfCourses = new Generex("[0-2]");
        Generex generateCourseId = new Generex("[0-9]");
        int studentId = 1;

        HashSet<Integer> courses = new HashSet<>();
        
        try {
            FileWriter file = new FileWriter(studentsCoursesSql);

            for (int i = 0; i < 200; i++) {
                
                for (int j = 0; j <= Integer.parseInt(generateNumberOfCourses.random()); j++) {
                    courses.add(Integer.parseInt(generateCourseId.random()) + 1);
                }
                
                for (int courseId : courses) {
                    
                    file.write("INSERT INTO students_courses(student_id, course_id) VALUES(" + studentId + ", "
                            + courseId + ");\n");
                }
                
                courses.clear();
                studentId++;
            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
