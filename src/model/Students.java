
package model;

/**
 * Students class for storing individual student data on objects.
 * @author Christian Dave Janiola
 */
public class Students {
    
    //fields for student data: name, gender, id, year level, course code
    private String name;
    private String gender;
    private String id;
    private String yearLevel;
    private String courseCode;
    
    //String field to store name of the enrolled course
    //useful for keeping track of the course name after authenticating with courses objects
    //should I remove this?? DOES THIS DEFEAT THE PURPOSE???
    private String courseName = "Not enrolled"; //default value "not enrolled" when no code match is detected
    
    //contructor for Students class, initializes values for name, gender, id, year level, and course code
    public Students(String name, String gender, String id, String yearLevel, String courseCode){
        this.name = name;
        this.gender = gender;
        this.id = id;
        this.yearLevel = yearLevel;
        this.courseCode = courseCode;
    }
    
    //setter for this class
    void setName(String name){
        this.name = name;
    }
    void setGender(String gender){
        this.gender = gender;
    }
    void setId(String id){
        this.id = id;
    }
    void setYear(String year){
        this.yearLevel = year;
    }
    void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }
    void setCourseName (String courseName){
        this.courseName = courseName;
    }
    
    //getters for this class
    String getName(){
        return this.name;
    }
    String getGender(){
        return this.gender;
    }
    String getId(){
        return this.id;
    }
    String getYear(){
        return this.yearLevel;
    }
    String getCourseCode(){
        return this.courseCode;
    }
    String getCourseName(){
        return this.courseName;
    }
}
