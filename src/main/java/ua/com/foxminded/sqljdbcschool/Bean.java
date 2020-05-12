package ua.com.foxminded.sqljdbcschool;

import java.io.File;
import java.io.FileWriter;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.*;
import org.springframework.stereotype.*;

import com.mifmif.common.regex.Generex;

//import com.mifmif.common.regex.Generex;

@Component
public class Bean implements CommandLineRunner {

    public void run(String... args) throws IOException {
        String pathToFile = getClass().getClassLoader().getResource("").getPath();
        String groupsSql = pathToFile + "groups.sql";

        if (Boolean.FALSE.equals(sqlFileExist(groupsSql))) {
            createSqlFile(groupsSql);
            generateGroups(groupsSql);

            // System.out.println("file not foun");
        }

        // Generate letters
//            Generex generex = new Generex("[a-zA-Z]{2}");
//            String secondString = generex.random();
//            System.out.println(secondString);

        // Genrate numbers
//            generex = new Generex("[0-9]{2}");
//            secondString = generex.random();
//            System.out.println(secondString);

        // Перемешать массив
        // Collections.shuffle(list);
//            secondString = generex.getMatchedString(2);
        // Write to file
        // String str = "INSERT INTO groups(group_id, group_name) VALUES(1,
        // 'AA-11');\n";
        // BufferedWriter writer = new BufferedWriter(new
        // FileWriter("C:\\workspace\\sql-jdbc-school\\src\\main\\resources\\db\\data.sql",
        // true));
        // writer.append(str);

        // writer.close();

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
            
            for (int i = 01; i < 11; i++) {
                file.write("INSERT INTO groups(group_id, group_name) VALUES(" + i + ", '" + generateLetters.random() + "-"
                        + generateNumbers.random() + "');\n");
            }
            
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
