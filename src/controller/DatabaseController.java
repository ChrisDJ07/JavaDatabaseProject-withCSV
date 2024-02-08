
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.DatabaseModel;
import view.DatabaseFrame;
import view.InputFrame;
import view.SelectFrame;

/**
 * Main controller for the database software.
 * @author Christian Dave Janiola
 */
public class DatabaseController {
    
    private DatabaseFrame studentDB;
    private DatabaseFrame courseDB;
    private DatabaseModel modelDB;
    private InputFrame input;
    private SelectFrame selectStudent;
    
    //integer for GUI type, 0-students 1-courses
    int type;
    
    public DatabaseController(DatabaseFrame frameDB, DatabaseModel modelDB, int type){
        this.modelDB = modelDB;
        this.modelDB.extractCourseData();
        
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
                    modelDB.setStudentData(selectedIndex, studentData);
                    modelDB.saveData(0);
                    modelDB.matchCourseCode();
                    studentDB.tableModel.removeRow(selectedIndex);
                    String[] newStudentData = {input.getNameText(), input.getGenderType(),input.getIdText(),
                                             input.getYearText(), modelDB.getCourseName(selectedIndex)};
                    studentDB.tableModel.addRow(newStudentData);
                    input.dispose();
                }
            }
            if(type == 1){
                if(actionType.equals("Add")){
                    modelDB.createNewCourse(input.getCourseField(), input.getCourseNameField());
                    modelDB.saveData(1);
                    String[] newCourseData = {input.getCourseField(), input.getCourseNameField()};
                    courseDB.tableModel.addRow(newCourseData);
                    input.dispose();
                }
                if(actionType.equals("Edit")){
                    //to do code here
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
            int selectedIndex = selectStudent.getSelectedStudent();
            if(type == 0){
                if(actionType.equals("Delete")){
                    modelDB.deleteStudent(selectedIndex);
                    modelDB.saveData(0);
                    studentDB.tableModel.removeRow(selectedIndex);
                    selectStudent.dispose();
                }
                if(actionType.equals("Edit")){
                    input = new InputFrame("Edit Student Data", 0);
                    input.addSubmitListener(new submitListener("Edit", selectedIndex));
                    input.setCourseCodeList(modelDB.courseCodeList.toArray(new String[0]));

                    String[] currentStudentData = modelDB.getStudentData(selectedIndex);
                    input.setNameText(currentStudentData[0]);
                    input.setIdText(currentStudentData[1]);
                    input.setYearText(currentStudentData[2]);
                    input.setGenderType(currentStudentData[3]);
                    input.setCourseText(currentStudentData[4]);

                    selectStudent.dispose();
                }
            }
            if(type == 1){
                //to do code here
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
                input.setCourseCodeList(modelDB.courseCodeList.toArray(new String[0]));
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
            selectStudent = new SelectFrame("Select Student Data to Edit");
            selectStudent.addSelectListener(new selectListener("Edit"));
            selectStudent.setStudentList(modelDB.studentList.toArray(new String[0]));
        }
    }
    class deleteListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            selectStudent = new SelectFrame("Select Student Data to Delete");
            selectStudent.addSelectListener(new selectListener("Delete"));
            selectStudent.setStudentList(modelDB.studentList.toArray(new String[0]));
        }
    }
    class clearListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            modelDB.studentObjects.clear();
            modelDB.saveData(0);
            for(int i=studentDB.tableModel.getRowCount()-1; i>=0; i--){
                studentDB.tableModel.removeRow(i);
            }
        }
    }
}
