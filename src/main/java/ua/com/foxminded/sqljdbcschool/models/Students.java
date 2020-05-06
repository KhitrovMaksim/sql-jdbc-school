package ua.com.foxminded.sqljdbcschool.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int student_id;
    private int group_id;
    private String first_name;
    private String last_name;

    public Students() {
    }
    
    public Students(String first_name, String last_name, int group_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.group_id = group_id;
    }
    
    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

}
