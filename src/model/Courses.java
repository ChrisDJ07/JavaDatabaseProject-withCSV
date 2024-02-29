
package model;

import java.util.ArrayList;

/**
 * Courses class for storing course data on objects.
 * @author Christian Dave Janiola
 */
public class Courses {

    private String courseCode; //code of the course (i.e. "BSCS")
    private String courseName; //name of the course (i.e. "Bachelor of Science in Computer Science")
    
    /*arraylist of students enrolled in the subject, containes integer index of each student int
    in the studentOBjects arraylist in DatabaseModel, useful to keep track of affected students 
    in the event the course code is updated*/
    public ArrayList<Integer> studentList = new ArrayList<>();
    
    //constructor for the Courses class, initializes values for courseCode and courseName
    public Courses(String courseCode, String courseName){
        this.courseCode = courseCode;
        this.courseName = courseName;
    }
    
    //setter for courseCode
    void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }
    //setter for courseName
    void setCourseName(String courseName){
        this.courseName = courseName;
    }
    //getter for courseName
    String getCourseCode(){
        return this.courseCode;
    }
    //getter for courseCode
    String getCourseName(){
        return this.courseName;
    }
}
