
package model;

import java.util.ArrayList;

/**
 * Courses class for storing course data on objects.
 * @author Christian Dave Janiola
 */
public class Courses {

    private String courseCode;
    private String courseName;
    
    public ArrayList<String> studentList = new ArrayList<>();
    
    public Courses(String courseCode, String courseName){
        this.courseCode = courseCode;
        this.courseName = courseName;
    }
    
    void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }
    void setCourseName(String courseName){
        this.courseName = courseName;
    }
    
    String getCourseCode(){
        return this.courseCode;
    }
    String getCourseName(){
        return this.courseName;
    }
}
