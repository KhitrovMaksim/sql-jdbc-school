package ua.com.foxminded.sqljdbcschool.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Courses {

    @Id
    private int course_id;
    private String course_name;
    @Column(columnDefinition = "text")
    private String course_description;
    
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Students> students = new HashSet<>();

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }

}
