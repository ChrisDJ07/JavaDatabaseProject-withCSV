
package model;

/**
 * Students class for storing individual student data on objects.
 * @author Christian Dave Janiola
 */
public class Students {

    private String name;
    private String gender;
    private String id;
    private String yearLevel;
    private String courseCode;
    
    private String courseName;
    
    public Students(String name, String gender, String id, String yearLevel, String courseCode){
        this.name = name;
        this.gender = gender;
        this.id = id;
        this.yearLevel = yearLevel;
        this.courseCode = courseCode;
    }
    
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
