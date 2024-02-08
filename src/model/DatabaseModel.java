
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main Model for the database software.
 * @author Christian Dave Janiola
 */
public class DatabaseModel {
     public File studentFile;   //file for the studentDB.csv
     public File courseFile;    //file for the courseDB.csv
     
     public ArrayList<String> studentList = new ArrayList<>(); //List of Students for Table
     public ArrayList<String> courseCodeList = new ArrayList<>();   //List of Course Code for matching
     
     public ArrayList<Students> studentObjects = new ArrayList<>(); //List of Student Objects for OOP
     public ArrayList<Courses> courseObjects = new ArrayList<>();  //List of Course Objects for OOP
     
     public String[][] tableData;   //2d string for Table Construction
    
    /*Class Constructor*/
    //What else should it do??
    public DatabaseModel(){
        studentFile = new File("src/studentDB.csv");
        courseFile = new File("src/courseDB.csv");
    }
    
    /*Create New Student File*/
    //Unecessary??
    public void createNewFile(int type){
        try {
            if(type == 0){
                studentFile.createNewFile();
            }
            if(type == 1){
                courseFile.createNewFile();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /*Create new Student Object*/
    public void createNewStudent(String name, String gender, String id, String yearLevel, String courseCode){
        studentObjects.add(new Students(name, gender, id, yearLevel, courseCode));
        studentList.add(name);
    }
    /*Save Students Data into studentDB.csv*/
    public void saveData(int type){
        BufferedWriter writer = null;
         try {
             if(type == 0){
                writer = new BufferedWriter (new FileWriter(studentFile));
                for(Students student: studentObjects){
                    writer.write(student.getName()+","+student.getGender()+","+student.getId()+","+student.getYear()+","+student.getCourseCode()+"\n");
                }
             }
             if(type == 1){
                writer = new BufferedWriter (new FileWriter(courseFile));
                for(Courses course: courseObjects){
                    writer.write(course.getCourseCode()+","+course.getCourseName()+"\n");
                }
             }
             
         } catch (IOException ex) {
             ex.printStackTrace();
         }
         finally{
            try {
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
         }
    }
    /*Method to extract Student data and input to Objects*/
    public void extractStudentData(){
        BufferedReader reader = null;
        String line = "";
         try {
             reader = new BufferedReader(new FileReader(studentFile));
             int index = 0;
             while((line = reader.readLine()) != null){
                    String[] studentData = line.split(",");
                    studentObjects.add(new Students(studentData[0], studentData[1], studentData[2], studentData[3], studentData[4]));
                    studentList.add(studentData[0]);
                 index++;
             }
         } catch (FileNotFoundException ex) {
             ex.printStackTrace();
         } catch (IOException ex) {
             ex.printStackTrace();
         }
         finally{
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
         }
    }
    /*Returns the student data of index specified student object*/
    public String[] getStudentData(int index){
        String[] studentData = {studentObjects.get(index).getName(), studentObjects.get(index).getId(), studentObjects.get(index).getYear()
                                ,studentObjects.get(index).getGender(), studentObjects.get(index).getCourseCode()};
        return studentData;
    }
    /*Set data of selected student*/
    public void setStudentData(int index, String[] data){
        studentObjects.get(index).setName(data[0]);
        studentObjects.get(index).setGender(data[1]);
        studentObjects.get(index).setId(data[2]);
        studentObjects.get(index).setYear(data[3]);
        studentObjects.get(index).setCourseCode(data[4]);
        
        studentList.set(index, data[0]);
    }
    /*Remove each instance of specified student object and data*/
    public void deleteStudent(int index){
        studentObjects.remove(index);
        studentList.remove(index);
    }
    
    /*Create a new course object and set its code and name*/
    public void createNewCourse(String courseCode, String courseName){
        courseObjects.add(new Courses(courseCode, courseName));
        courseCodeList.add(courseCode);
    }
    /*Method to extract Course Data*/
    public void extractCourseData(){
        BufferedReader reader = null;
        String line = "";
         try {
             reader = new BufferedReader(new FileReader(courseFile));
             int index = 0;
             while((line = reader.readLine()) != null){
                    String[] courseData = line.split(",");
                    courseObjects.add(new Courses(courseData[0], courseData[1]));
                    courseCodeList.add(courseData[0]);
                 index++;
             }
         } catch (FileNotFoundException ex) {
             ex.printStackTrace();
         } catch (IOException ex) {
             ex.printStackTrace();
         }
         finally{
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
         }
    }
    /*Returns the course name of the selected student index*/
    public String getCourseName (int index){
        return studentObjects.get(index).getCourseName();
    }
    
    /*Match course code of student-course and assign values to each objects*/
    public void matchCourseCode(){
        for(Students student: studentObjects){
            for(Courses course: courseObjects){
                if(student.getCourseCode().equals(course.getCourseCode())){
                    student.setCourseName(course.getCourseName());
                    course.studentList.add(student.getName());
                    break;
                }
            }
        }
    }
    
    /*Populating Table Data for Table Display*/
    public void populateTable(int type){
        if(type == 0){
            tableData = new String[studentObjects.size()][5];
            int iterator = 0;
            for(Students student: studentObjects){
                String[] individualData = {student.getName(), student.getGender(), student.getId(), student.getYear(), student.getCourseName()};
                tableData[iterator] = individualData;
                iterator++;
            }
        }
        if(type == 1){
            tableData = new String[courseObjects.size()][2];
            int iterator = 0;
            for(Courses course: courseObjects){
                String[] individualData = {course.getCourseCode(), course.getCourseName()};
                tableData[iterator] = individualData;
                iterator++;
            }
        }
    }
}
