package ua.com.foxminded.sqljdbcschool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.springframework.boot.*;
import org.springframework.stereotype.*;
import org.apache.commons.lang3.ArrayUtils;

import com.mifmif.common.regex.Generex;

@Component
public class Bean implements CommandLineRunner {

    public void run(String... args) throws IOException {
        String pathToFile = getClass().getClassLoader().getResource("").getPath();
        String groupsSql = pathToFile + "groups.sql";
        String studentsSql = pathToFile + "students.sql";

        if (Boolean.FALSE.equals(sqlFileExist(groupsSql))) {
            createSqlFile(groupsSql);
            generateGroups(groupsSql);
        }

        if (Boolean.FALSE.equals(sqlFileExist(studentsSql))) {
            createSqlFile(studentsSql);
            generateStudents(studentsSql);
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
        String pathToFile = getClass().getClassLoader().getResource("").getPath();
        String firstNamesPath = pathToFile + "first-names.txt";
        String lastNamesPath = pathToFile + "last-names.txt";
        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        List<Integer> groupsNumber = new ArrayList<>();
        Generex generateNumbers = new Generex("[0-9]");

        for (int i = 0; i < 10; i++) {
            firstNames.addAll(readNames(firstNamesPath));
            lastNames.addAll(readNames(lastNamesPath));
        }

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

            groupsNumber.add(groupNumber + 1);
        }

        Collections.shuffle(firstNames);
        Collections.shuffle(lastNames);

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

    private List<String> readNames(String namesFile) {
        List<String> names = new ArrayList<>();
        try {
            File file = new File(namesFile);
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

}
