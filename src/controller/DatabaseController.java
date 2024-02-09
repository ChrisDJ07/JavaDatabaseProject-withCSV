
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import model.DatabaseModel;
import view.DatabaseFrame;
import view.InputFrame;
import view.SelectFrame;

/**
 * Main controller for the database software.
 * @author Christian Dave Janiola
 */
public class DatabaseController {
    
    //can probably be optimized more
    private DatabaseFrame studentDB;
    private DatabaseFrame courseDB;
    private DatabaseModel modelDB;
    private InputFrame input;
    private SelectFrame selectStudent;
    private SelectFrame selectCourse;
    
    //integer for GUI type, 0-students 1-courses
    int type;
    public static String[] courseList;
    
    public DatabaseController(DatabaseFrame frameDB, DatabaseModel modelDB, int type){
        this.modelDB = modelDB;
        this.modelDB.extractCourseData();
        courseList = modelDB.courseCodeList.toArray(new String[0]);
        
        if(type == 0){
            this.type = 0;
            this.studentDB = frameDB;
            this.studentDB.addAddListener(new addListener());
            this.studentDB.addEditListener(new editListener());
            this.studentDB.addDeleteListener(new deleteListener());
            this.studentDB.addClearListener(new clearListener());

            if(this.modelDB.studentFile.exists() == false){
                this.modelDB.createNewFile(0);
            }
            else{
                this.modelDB.extractStudentData();
                if(modelDB.studentObjects.isEmpty() == false){
                    this.modelDB.matchCourseCode();
                    this.modelDB.populateTable(0);
                    this.studentDB.generateTable(modelDB.tableData, 0);
                }
                else{
                    this.studentDB.generateTable(new String[0][0], 0);
                }
            }
            studentDB.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    modelDB.clearStudents();
                }
            });
        }
        
        if(type == 1){
            this.type = 1;
            this.courseDB = frameDB;
            this.courseDB.addAddListener(new addListener());
            this.courseDB.addEditListener(new editListener());
            this.courseDB.addDeleteListener(new deleteListener());
            this.courseDB.addClearListener(new clearListener());
            
            if(this.modelDB.courseFile.exists() == false){
                this.modelDB.createNewFile(1);
            }
            else{
                if(modelDB.courseObjects.isEmpty() == false){
                    this.modelDB.populateTable(1);
                    this.courseDB.generateTable(modelDB.tableData, 1);
                }
                else{
                    this.courseDB.generateTable(new String[0][0], 1);
                }
            }
        }
    }
    
    /*InputFrame ActionListener*/
    class submitListener implements ActionListener{
        private String actionType;
        private int selectedIndex;
        
        public submitListener(String actionType, int selectedIndex){
            this.actionType = actionType;
            this.selectedIndex = selectedIndex;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(type == 0){
                if(actionType.equals("Add")){
                    modelDB.createNewStudent(input.getNameText(), input.getGenderType(),input.getIdText(),
                                             input.getYearText(), input.getCourseCode(modelDB.courseCodeList.toArray(new String[0])));
                    modelDB.saveData(0);
                    modelDB.matchCourseCode();
                    String[] newStudentData = {input.getNameText(), input.getGenderType(),input.getIdText(),
                                             input.getYearText(), modelDB.getCourseName(modelDB.studentList.size()-1)};
                    studentDB.tableModel.addRow(newStudentData);
                    input.dispose();
                }
                if(actionType.equals("Edit")){
                    String[] studentData = {input.getNameText(), input.getGenderType(),input.getIdText(),
                                             input.getYearText(), input.getCourseCode(modelDB.courseCodeList.toArray(new String[0]))};
                    modelDB.setData(selectedIndex, studentData, 0);
                    modelDB.saveData(0);
                    modelDB.matchCourseCode();
                    studentDB.tableModel.removeRow(selectedIndex);
                    studentData[4] = modelDB.getCourseName(selectedIndex);
                    studentDB.tableModel.insertRow(selectedIndex,studentData);
                    input.dispose();
                }
            }
            if(type == 1){
                if(actionType.equals("Add")){
                    modelDB.createNewCourse(input.getCourseField(), input.getCourseNameField());
                    modelDB.saveData(1);
                    String[] newCourseData = {input.getCourseField(), input.getCourseNameField()};
                    courseDB.tableModel.addRow(newCourseData);
                    courseDataChange();
                    input.dispose();
                }
                if(actionType.equals("Edit")){
                    String[] previousData = modelDB.getData(selectedIndex, 1);
                    String[] courseData = {input.getCourseField(), input.getCourseNameField()};
                    System.out.println(previousData[0]+" =? "+courseData[0]);
                    if(previousData[0] != courseData[0] || previousData[1] != courseData[1]){
                        if(previousData[0].equals(courseData[0])==false){
                            if(courseDB.codeChanged()){
                                modelDB.courseUpdate(modelDB.courseObjects.get(selectedIndex), courseData);
                                modelDB.saveData(0);
                            }
                        }
                        modelDB.setData(selectedIndex, courseData, 1);
                        modelDB.saveData(1);
                        courseDB.tableModel.removeRow(selectedIndex);
                        courseDB.tableModel.insertRow(selectedIndex, courseData);
                        courseDataChange();
                    }
                    input.dispose();
                }
            }
        }
    }
    
    /*SelectFrame ActionListener*/
    class selectListener implements ActionListener{
        String actionType;
        
        public selectListener(String actionType){
            this.actionType = actionType;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex;
            if(type == 0){
                selectedIndex = selectStudent.getSelected();
                if(actionType.equals("Delete")){
                    modelDB.delete(selectedIndex, 0);
                    modelDB.saveData(0);
                    studentDB.tableModel.removeRow(selectedIndex);
                    selectStudent.dispose();
                }
                if(actionType.equals("Edit")){
                    input = new InputFrame("Edit Student Data", 0);
                    input.addSubmitListener(new submitListener("Edit", selectedIndex));
                    input.setCourseCodeList(courseList);

                    String[] currentData = modelDB.getData(selectedIndex, 0);
                    input.setNameText(currentData[0]);
                    input.setIdText(currentData[1]);
                    input.setYearText(currentData[2]);
                    input.setGenderType(currentData[3]);
                    input.setCourseText(currentData[4]);
                    selectStudent.dispose();
                }
            }
            /*Update student enrolled to deleted course*/
            if(type == 1){
                selectedIndex = selectCourse.getSelected();
                if(actionType.equals("Delete")){
                    modelDB.delete(selectedIndex, 1);
                    modelDB.saveData(1);
                    courseDB.tableModel.removeRow(selectedIndex);
                    courseDataChange();
                    selectCourse.dispose();
                }
                if(actionType.equals("Edit")){
                    input = new InputFrame("Edit Course Data", 1);
                    input.addSubmitListener(new submitListener("Edit", selectedIndex));
                    String[] currentData = modelDB.getData(selectedIndex, 1);
                    input.setCourseField(currentData[0]);
                    input.setCourseNameField(currentData[1]);
                    selectCourse.dispose();
                }
            }
        }
    }
    
    /*DatabaseFrame ActionListeners*/
    class addListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(type == 0){
                input = new InputFrame("Add Student Data", 0);
                input.addSubmitListener(new submitListener("Add", 0));
                input.setCourseCodeList(courseList);
            }
            if(type == 1){
                input = new InputFrame("Add Course", 1);
                input.addSubmitListener(new submitListener("Add", 0));
            }
        }
    }
    class editListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(type == 0){
                selectStudent = new SelectFrame("Select Student Data to Edit", 0);
                selectStudent.addSelectListener(new selectListener("Edit"));
                selectStudent.setList(modelDB.studentList.toArray(new String[0]));
            }
            if(type == 1){
                selectCourse = new SelectFrame("Select Course Data to Edit", 1);
                selectCourse.addSelectListener(new selectListener("Edit"));
                selectCourse.setList(courseList);
            }
        }
    }
    class deleteListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(type == 0){
                selectStudent = new SelectFrame("Select Student Data to Delete",1);
                selectStudent.addSelectListener(new selectListener("Delete"));
                selectStudent.setList(modelDB.studentList.toArray(new String[0]));
            }
            if(type == 1){
                selectCourse = new SelectFrame("Select Course Data to Delete", 1);
                selectCourse.addSelectListener(new selectListener("Delete"));
                selectCourse.setList(modelDB.courseCodeList.toArray(new String[0]));
            }
        }
    }
    class clearListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(type == 0){
                modelDB.studentObjects.clear();
                modelDB.saveData(0);
                for(int i=studentDB.tableModel.getRowCount()-1; i>=0; i--){
                    studentDB.tableModel.removeRow(i);
                }
            }
            if(type == 1){
                modelDB.courseObjects.clear();
                modelDB.saveData(1);
                for(int i=courseDB.tableModel.getRowCount()-1; i>=0; i--){
                    courseDB.tableModel.removeRow(i);
                }
                courseDataChange();
            }
        }
    }
    void courseDataChange(){
        /*Updates student database*/
        modelDB.courseCodeList.clear();
        modelDB.courseObjects.clear();
        modelDB.studentList.clear();
        modelDB.studentObjects.clear();
        
        modelDB.extractStudentData();
        modelDB.extractCourseData();
        courseList = modelDB.courseCodeList.toArray(new String[0]);
        modelDB.matchCourseCode();
        modelDB.populateTable(0);

        courseDB.setChangeStatus(true);
        courseDB.setChangeData(modelDB.tableData);
    }
}
