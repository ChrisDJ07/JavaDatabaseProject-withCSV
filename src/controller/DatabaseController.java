
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
    private DatabaseModel modelDB;
    private InputFrame inputStudent;
    private SelectFrame selectStudent;
    
    public DatabaseController(DatabaseFrame studentDB, DatabaseModel modelDB){
        this.studentDB = studentDB;
        this.studentDB.addAddListener(new addListener());
        this.studentDB.addEditListener(new editListener());
        this.studentDB.addDeleteListener(new deleteListener());
        this.studentDB.addClearListener(new clearListener());
        
        this.modelDB = modelDB;
        this.modelDB.extractCourseData();
        
        if(this.modelDB.studentFile.exists() == false){
            this.modelDB.createStudentFile();
        }
        else{
            this.modelDB.extractStudentData();
            if(modelDB.studentObjects.isEmpty() == false){
                this.modelDB.matchCourseCode();
                this.modelDB.populateTable();
                this.studentDB.generateTable(modelDB.tableData);
            }
            else{
                this.studentDB.generateTable(new String[0][0]);
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
            if(actionType.equals("Add")){
                modelDB.createNewStudent(inputStudent.getNameText(), inputStudent.getGenderType(),inputStudent.getIdText(),
                                         inputStudent.getYearText(), inputStudent.getCourseCode(modelDB.courseCodeList.toArray(new String[0])));
                modelDB.saveStudentData();
                modelDB.matchCourseCode();
                String[] newStudentData = {inputStudent.getNameText(), inputStudent.getGenderType(),inputStudent.getIdText(),
                                         inputStudent.getYearText(), modelDB.getCourseName(modelDB.studentList.size()-1)};
                studentDB.tableModel.addRow(newStudentData);
                inputStudent.dispose();
            }
            if(actionType.equals("Edit")){
                String[] studentData = {inputStudent.getNameText(), inputStudent.getGenderType(),inputStudent.getIdText(),
                                         inputStudent.getYearText(), inputStudent.getCourseCode(modelDB.courseCodeList.toArray(new String[0]))};
                modelDB.setStudentData(selectedIndex, studentData);
                modelDB.saveStudentData();
                modelDB.matchCourseCode();
                studentDB.tableModel.removeRow(selectedIndex);
                String[] newStudentData = {inputStudent.getNameText(), inputStudent.getGenderType(),inputStudent.getIdText(),
                                         inputStudent.getYearText(), modelDB.getCourseName(selectedIndex)};
                studentDB.tableModel.addRow(newStudentData);
                inputStudent.dispose();
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
            if(actionType.equals("Delete")){
                modelDB.deleteStudent(selectedIndex);
                modelDB.saveStudentData();
                studentDB.tableModel.removeRow(selectedIndex);
                selectStudent.dispose();
            }
            if(actionType.equals("Edit")){
                inputStudent = new InputFrame("Edit Student Data");
                inputStudent.addSubmitListener(new submitListener("Edit", selectedIndex));
                inputStudent.setCourseCodeList(modelDB.courseCodeList.toArray(new String[0]));
                
                String[] currentStudentData = modelDB.getStudentData(selectedIndex);
                inputStudent.setNameText(currentStudentData[0]);
                inputStudent.setIdText(currentStudentData[1]);
                inputStudent.setYearText(currentStudentData[2]);
                inputStudent.setGenderType(currentStudentData[3]);
                inputStudent.setCourseText(currentStudentData[4]);
                
                selectStudent.dispose();
            }
        }
    }
    
    /*DatabaseFrame ActionListeners*/
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
            modelDB.saveStudentData();
            for(int i=studentDB.tableModel.getRowCount()-1; i>=0; i--){
                studentDB.tableModel.removeRow(i);
            }
        }
    }
    class addListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            inputStudent = new InputFrame("Add Student Data");
            inputStudent.addSubmitListener(new submitListener("Add", 0));
            inputStudent.setCourseCodeList(modelDB.courseCodeList.toArray(new String[0]));
        }
    }
}
