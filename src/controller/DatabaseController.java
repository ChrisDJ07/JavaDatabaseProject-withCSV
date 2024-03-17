
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import model.DatabaseModel;
import view.DatabaseFrame;
import view.InputFrame;
import view.MainMenu;
import view.SelectFrame;

/**
 * Main controller for the database software.
 * @author Christian Dave Janiola
 */
public class DatabaseController {
    
    private DatabaseFrame studentDB; //student frame
    private DatabaseFrame courseDB; //course frame
    private static DatabaseModel modelDB; //general model object
    private InputFrame input; //general input frame
    private SelectFrame selectStudent; //student select frame
    private SelectFrame selectCourse; //course select frame
    
    //integer for GUI type, 0-students 1-courses
    int type;
    public static String[] courseList; //list of courses by code
    
    /*
    Class constructor taking DatabaseModel parameter to init model object.
    Also initializes extracting of course and student data for the first time.
    Also matches course codes if student and course data aren't empty.
    */
    public DatabaseController(DatabaseModel modelDB){
        this.modelDB = modelDB;
        this.modelDB.extractData(1);
        this.modelDB.extractData(0);
        if(!(this.modelDB.studentObjects.isEmpty() || this.modelDB.courseObjects.isEmpty())){
            this.modelDB.matchCourseCode();
            courseList = modelDB.courseCodeList.toArray(new String[0]); //converts 'courseCodeList' arraylist 
                                                                        // into a String array stored in 'courseList'
        }
    }
    
    /*
    Another class constructor for either student/course controller initialization.
    */
    public DatabaseController(DatabaseFrame frameDB, int type, MainMenu main){
        if(type == 0){ //executes this nested if code when type = 0 (Student integer type designation)
            this.type = 0;
            this.studentDB = frameDB;
            this.studentDB.addAddListener(new addListener());
            this.studentDB.addEditListener(new editListener());
            this.studentDB.addDeleteListener(new deleteListener());
            this.studentDB.addClearListener(new clearListener());
            this.studentDB.addSearchListener(new searchListener());
            
            //populate table data if student objects isn't empty, otherwise populate table with no rows
            if(!(modelDB.studentObjects.isEmpty())){
                this.modelDB.populateTable(0);
                this.studentDB.generateTable(modelDB.tableData, 0);
            }
            else{
                this.studentDB.generateTable(new String[0][0], 0);
            }
            //enables student button in "main" when current window is closed
            studentDB.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    main.students.setEnabled(true);
                }
            });
        }
        
        if(type == 1){ //executes this nested if code when type = 1 (Course integer type designation)
            this.type = 1;
            this.courseDB = frameDB;
            this.courseDB.addAddListener(new addListener());
            this.courseDB.addEditListener(new editListener());
            this.courseDB.addDeleteListener(new deleteListener());
            this.courseDB.addClearListener(new clearListener());
            this.courseDB.addSearchListener(new searchListener());
            
            //populate table data if course objects isn't empty, otherwise populate table with no rows
            if(modelDB.courseObjects.isEmpty() == false){
                this.modelDB.populateTable(1);
                this.courseDB.generateTable(modelDB.tableData, 1);
            }
            else{
                this.courseDB.generateTable(new String[0][0], 1);
            }
            //enables student button in "main" when current window is closed
            courseDB.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    main.courses.setEnabled(true);
                }
            });
        }
    }
    
    /*InputFrame ActionListener*/
    class submitListener implements ActionListener{
        private String actionType; //either 'add' or 'edit'
        private int selectedIndex; //int index used for retrieving student/course object
        
        //class constructor that init values for 'actionType' and 'selectedIndex'
        public submitListener(String actionType, int selectedIndex){
            this.actionType = actionType;
            this.selectedIndex = selectedIndex;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(type == 0){
                if(actionType.equals("Add" )){
                    //check if name, id, and course is filled
                    if(input.getNameText().replace(" ", "").isEmpty() || input.getIdText().replace(" ", "").isEmpty()
                             || input.getCourseCode(modelDB.courseCodeList.toArray(new String[0]))== "None"){
                        JOptionPane.showMessageDialog( null, "Missing necessary fields.");
                        return;
                    } //check for duplicates in name and id
                    else if(modelDB.checkDuplicate(selectedIndex, input.getNameText(),input.getIdText(), 0, "add")){
                        JOptionPane.showMessageDialog(null, "Student Name or ID Number already taken.");
                        return;
                    }
                    else{
                        modelDB.createNewStudent(input.getNameText(), input.getGenderType(),input.getIdText(),
                                                 input.getYearText(), input.getCourseCode(modelDB.courseCodeList.toArray(new String[0])));
                        modelDB.saveData(0); //save new student data to dedicated csv file
                        refresh(); //calls refresh function
                        //stores new student data to string variable to add to table
                        String[] newStudentData = {input.getNameText(), input.getGenderType(),input.getIdText(),
                                                 input.getYearText(), modelDB.getCourseName(modelDB.studentList.size()-1)};
                        studentDB.tableModel.addRow(newStudentData); //add new row for new student data
                        input.dispose();
                    }
                }
                if(actionType.equals("Edit")){
                    //check if name, id, and course is filled
                    if(input.getNameText().replace(" ", "").isEmpty() || input.getIdText().replace(" ", "").isEmpty()
                             || input.getCourseCode(modelDB.courseCodeList.toArray(new String[0])) == "None"){
                        JOptionPane.showMessageDialog( null, "Missing necessary fields.");
                        return;
                    } // check for duplicates for name and id
                    else if(modelDB.checkDuplicate(selectedIndex, input.getNameText(),input.getIdText(), 0, "edit")){
                        JOptionPane.showMessageDialog(null, "Student Name or ID Number already taken.");
                        return;
                    }
                    else{
                        String[] studentData = {input.getNameText(), input.getGenderType(),input.getIdText(),
                                                 input.getYearText(), input.getCourseCode(modelDB.courseCodeList.toArray(new String[0]))};
                        modelDB.setData(selectedIndex, studentData, 0);
                        modelDB.saveData(0);
                        refresh();
                        studentDB.tableModel.removeRow(selectedIndex);
                        studentData[4] = modelDB.getCourseName(selectedIndex);//replaces course code with code name to insert into the table
                        studentDB.tableModel.insertRow(selectedIndex,studentData);
                        input.dispose();
                    }
                }
            }
            if(type == 1){
                if(actionType.equals("Add")){
                    //check if the course code and name fields are filled
                    if(input.getCourseNameField().replace(" ", "").isEmpty() || input.getCourseField().replace(" ", "").isEmpty()){
                        JOptionPane.showMessageDialog( null, "Missing necessary fields.");
                        return;
                    } //check for duplicates in code and name
                    else if(modelDB.checkDuplicate(selectedIndex, input.getCourseNameField(),input.getCourseField(), 1, "add")){
                        JOptionPane.showMessageDialog(null, "Course Name or Code already taken.");
                        return;
                    }
                    else{
                        modelDB.createNewCourse(input.getCourseField(), input.getCourseNameField());
                        modelDB.saveData(1);//save new course data to dedicated csv file
                        //stores new student data to string variable to add to table
                        String[] newCourseData = {input.getCourseField(), input.getCourseNameField()};
                        courseDB.tableModel.addRow(newCourseData);//add new row for new course data
                        courseDataChange();
                        input.dispose();
                    }
                }
                if(actionType.equals("Edit")){
                    //check if the course code and name fields are filled
                    if(input.getCourseNameField().replace(" ", "").isEmpty() || input.getCourseField().replace(" ", "").isEmpty()){
                        JOptionPane.showMessageDialog( null, "Missing necessary fields.");
                        return;
                    } //check for duplicates in code and name
                    else if(modelDB.checkDuplicate(selectedIndex, input.getCourseNameField(),input.getCourseField(), 1, "edit")){
                        JOptionPane.showMessageDialog(null, "Course Name or Code already taken.");
                        return;
                    }
                    else{
                        String nameField;
                        if(input.getCourseNameField().isEmpty()){ //solves weird bug when course name field is empty
                            nameField = " ";
                        }else {
                            nameField = input.getCourseNameField();
                        }
                        String[] previousData = modelDB.getData(selectedIndex, 1);
                        String[] courseData = {input.getCourseField(), nameField}; //new course data
                        if(previousData[0] != courseData[0] || previousData[1] != courseData[1]){ //check if anything is changed
                            if(previousData[0].equals(courseData[0])==false){ //checked if course code is changed
                                String choice = courseDB.codeChanged();
                                if(choice == "yes"){//calls the codeChanged method confirming if student data should be changed or not, or cancel operation
                                    modelDB.courseUpdate(modelDB.courseObjects.get(selectedIndex), courseData);//updates course codes of affected students
                                    modelDB.saveData(0); //save data to csv file    
                                }
                                else if(choice == "cancel"){ //check if not "yes" or "no"
                                    return;
                                }

                            }
                            modelDB.setData(selectedIndex, courseData, 1); //updates course data
                            modelDB.saveData(1); //save to csv file
                            /*updates table*/
                            courseDB.tableModel.removeRow(selectedIndex);
                            courseDB.tableModel.insertRow(selectedIndex, courseData);
                            courseDataChange();//updates students' data
                        }
                        input.dispose();
                    }
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
                selectedIndex = selectStudent.getSelected(); //index of selected student
                if(actionType.equals("Delete") && JOptionPane.showConfirmDialog(null, //dialog to confirm student deletion (skips deletion if false)
                                "Are you sure you want to delete this student from the database? This operation is permanent.",
                                "Student Delete alert", 2 )== JOptionPane.YES_OPTION){
                    modelDB.delete(selectedIndex, 0); //delete student object from arraylist
                    modelDB.saveData(0); //save data to dedicated csv file
                    refresh(); //performs database refresh
                    studentDB.tableModel.removeRow(selectedIndex); //update table
                    selectStudent.dispose();
                }
                if(actionType.equals("Edit")){
                    input = new InputFrame("Edit Student Data", 0); //init new edit inputFrame
                    input.addSubmitListener(new submitListener("Edit", selectedIndex));
                    input.setCourseCodeList(courseList);
                    String[] currentData = modelDB.getData(selectedIndex, 0); //get data of selected student
                    //set fields
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
                selectedIndex = selectCourse.getSelected(); //index of selected course
                if(actionType.equals("Delete") && 
                        JOptionPane.showConfirmDialog(null, //dialog to confirm course deletion (skips deletion if false)
                                "Deleting This Course will affect all Students enrolled in this course, proceed?",
                                "Course Delete alert", 2 )== JOptionPane.YES_OPTION){
                    modelDB.delete(selectedIndex, 1); //delete course object from arraylist
                    modelDB.saveData(1); //save data to dedicated csv file
                    courseDB.tableModel.removeRow(selectedIndex); //update table
                    courseDataChange(); //update student data
                    selectCourse.dispose();
                }
                if(actionType.equals("Edit")){
                    input = new InputFrame("Edit Course Data", 1); //init new edit inputFrame
                    input.addSubmitListener(new submitListener("Edit", selectedIndex));
                    String[] currentData = modelDB.getData(selectedIndex, 1); //get data of selected course
                    //set fields
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
                studentDB.clearSearch();
                input = new InputFrame("Add Student Data", 0);
                input.addSubmitListener(new submitListener("Add", 0));
                input.setCourseCodeList(courseList);
            }
            if(type == 1){
                courseDB.clearSearch();
                input = new InputFrame("Add Course", 1);
                input.addSubmitListener(new submitListener("Add", 0));
            }
        }
    }
    class editListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(type == 0){
                studentDB.clearSearch();
                selectStudent = new SelectFrame("Select Student Data to Edit", 0);
                selectStudent.addSelectListener(new selectListener("Edit"));
                selectStudent.setList(modelDB.studentList.toArray(new String[0]));
            }
            if(type == 1){
                courseDB.clearSearch();
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
                studentDB.clearSearch();
                selectStudent = new SelectFrame("Select Student Data to Delete",1);
                selectStudent.addSelectListener(new selectListener("Delete"));
                selectStudent.setList(modelDB.studentList.toArray(new String[0]));
            }
            if(type == 1){
                courseDB.clearSearch();
                selectCourse = new SelectFrame("Select Course Data to Delete", 1);
                selectCourse.addSelectListener(new selectListener("Delete"));
                selectCourse.setList(modelDB.courseCodeList.toArray(new String[0]));
            }
        }
    }
    class clearListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(type == 0 && JOptionPane.showConfirmDialog(null, //dialog to confirm student data deletion (skips deletion if false)
                                "Are you sure you want to clear the whole sutdent database? This operation cannot be undone.",
                                "Student DB Delete alert", 2 )== JOptionPane.YES_OPTION){
                studentDB.clearSearch();
                modelDB.studentObjects.clear();
                modelDB.saveData(0);
                refresh();
                for(int i=studentDB.tableModel.getRowCount()-1; i>=0; i--){
                    studentDB.tableModel.removeRow(i);
                }
            }
            if(type == 1 && JOptionPane.showConfirmDialog(null, //dialog to confirm course data deletion (skips deletion if false)
                                "Are you sure you want to clear the whole course database? This operation cannot be undone.",
                                "Course DB Delete alert", 2 )== JOptionPane.YES_OPTION){
                courseDB.clearSearch();
                modelDB.courseObjects.clear();
                modelDB.saveData(1);
                for(int i=courseDB.tableModel.getRowCount()-1; i>=0; i--){
                    courseDB.tableModel.removeRow(i);
                }
                courseDataChange();
            }
        }
    }
    class searchListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
            //...
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if(type == 0){
                studentDB.addFilter();
            }else{
                courseDB.addFilter();
            }
        }
    }
    
    /*Updates student database when course data is changed*/
    void courseDataChange(){
        modelDB.courseCodeList.clear();
        refresh();
        courseList = modelDB.courseCodeList.toArray(new String[0]);
        modelDB.populateTable(0);

        courseDB.setChangeStatus(true);
        courseDB.setChangeData(modelDB.tableData);
    }
    
    //performs a database refresh, clears any static variables, and assign values to them
    static void refresh(){
        modelDB.courseObjects.clear();
        modelDB.studentList.clear();
        modelDB.studentObjects.clear();
        
        modelDB.extractData(0);
        modelDB.extractData(1);
        modelDB.matchCourseCode();
    }
}
