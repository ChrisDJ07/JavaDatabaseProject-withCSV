
package view;


import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Input field interface for the database software.
 * @author Christian Dave Janiola
 */
public class InputFrame extends JFrame{
    
    //init frame dimensions
    int height = 500;
    int width = 400;
    
    //init anchor coordinates
    int X=75;
    int Y=60;
    
    JPanel inputField;
    
    JButton submitButton = new JButton("SUBMIT");
    
    JLabel name = new JLabel("NAME:");
    JLabel id = new JLabel("STUDENT ID:");
    JLabel yearLevel = new JLabel("YEAR LEVEL:");
    JLabel gender = new JLabel("GENDER:");
    JLabel course = new JLabel("COURSE CODE:");
    JLabel courseName = new JLabel ("COURSE NAME");
    
    
    JTextField nameField = new JTextField();
    JTextField idField = new JTextField();
    JTextField yearLevelField = new JTextField();
    JTextField courseCodeField = new JTextField();
    JTextField courseNameField = new JTextField();
    
    
    String[] genderTypes = {"Male","Female", "Others"};
    JComboBox genderField = new JComboBox(genderTypes);
    
    JComboBox courseCodeBox;
    
    public InputFrame(String title, int type){
        super(title);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        
        inputField = new JPanel();
        
        //input frame for student type (0)
        if(type == 0){
            this.add(inputField);
            inputField.setLayout(null);
            inputField.setBounds(5,5,width-20, height-50);
            inputField.setBorder(BorderFactory.createEtchedBorder());

            inputField.add(name);
            inputField.add(id);
            inputField.add(yearLevel);
            inputField.add(gender);
            inputField.add(course);
            name.setBounds(X,Y, 100,30);
            id.setBounds(X,Y*2, 100,30);
            yearLevel.setBounds(X,Y*3, 100,30);
            gender.setBounds(X,Y*4, 100,30);
            course.setBounds(X,Y*5, 100,30);

            inputField.add(nameField);
            inputField.add(idField);
            inputField.add(genderField);
            inputField.add(yearLevelField);
            nameField.setBounds(X+90,Y, 150,30);
            idField.setBounds(X+90,Y*2, 150,30);
            yearLevelField.setBounds(X+90,Y*3, 150,30);
            genderField.setBounds(X+90,Y*4, 150,30);

            inputField.add(submitButton);
            submitButton.setBounds(X+60,Y*6, 100,40);
            
            JLabel necessary1 = new JLabel("(Necessary)");
            JLabel necessary2 = new JLabel("(Necessary)");
            JLabel necessary3 = new JLabel("(Necessary)");
            inputField.add(necessary1);necessary1.setFont(new Font("Arial", Font.PLAIN, 12));
            inputField.add(necessary2);necessary2.setFont(new Font("Arial", Font.PLAIN, 12));
            inputField.add(necessary3);necessary3.setFont(new Font("Arial", Font.PLAIN, 12));
            necessary1.setBounds(205,95,75,15);
            necessary2.setBounds(205,155,75,15);
            necessary3.setBounds(205-15,335,75,15);
            
        }
        
        //input frame for course type (1)
        if(type == 1){            
            this.setSize(400, 250);
            course.setText("COURSE CODE");
            courseName.setText("COURSE NAME");
            this.add(course);
            this.add(courseName);
            course.setBounds(X-40,Y-20, 100,30);
            courseName.setBounds(X-40,Y*2-20, 100,30);
            this.add(courseCodeField);
            this.add(courseNameField);
            courseCodeField.setBounds(X+60,Y-20, 200,30);
            courseNameField.setBounds(X+60,Y*2-20, 200,30);
            this.add(submitButton);
            submitButton.setBounds(X+70,Y*3-30, 100,40);
            
            JLabel necessary1 = new JLabel("(Necessary)");
            JLabel necessary2 = new JLabel("(Necessary)");
            this.add(necessary1);necessary1.setFont(new Font("Arial", Font.PLAIN, 12));
            this.add(necessary2);necessary2.setFont(new Font("Arial", Font.PLAIN, 12));
            necessary1.setBounds(200,72,75,15);
            necessary2.setBounds(200,131,75,15);
        }
        this.setVisible(true);
    }
    
    /*Setters*/
    public void setNameText(String name){
        nameField.setText(name);
    }
    public void setIdText(String id){
        idField.setText(id);
    }
    public void setGenderType(String gender){
        genderField.setSelectedItem(gender);
    }
    public void setYearText(String year){
        yearLevelField.setText(year);
    }
    public void setCourseText(String course){
        if(course.equals("None")){
            courseCodeBox.setSelectedIndex(-1);
        }
        else{
            courseCodeBox.setSelectedItem(course);
        }
    }
    public void setCourseCodeList(String[] courseCodeList){
        courseCodeBox = new JComboBox(courseCodeList);
        inputField.add(courseCodeBox);
        courseCodeBox.setSelectedIndex(-1);
        courseCodeBox.setBounds(X+100,Y*5, 100,30);
    }
    public void setCourseField(String courseCode){
        courseCodeField.setText(courseCode);
    }
    public void setCourseNameField(String courseName){
        courseNameField.setText(courseName);
    }
    
    
    /*Getters*/
    public String getNameText(){
        return nameField.getText();
    }
    public String getIdText(){
        return idField.getText();
    }
    public String getGenderType(){
        return genderTypes[genderField.getSelectedIndex()];
    }
    public String getYearText(){
        return yearLevelField.getText();
    }
    public String getCourseCode(String[] courseCodeList){
        if(courseCodeBox.getSelectedIndex() == -1){
            return "None";
        }
        return courseCodeList[courseCodeBox.getSelectedIndex()];
    }
    public String getCourseField(){
        return courseCodeField.getText();
    }
    public String getCourseNameField(){
        return courseNameField.getText();
    }
    
    public void addSubmitListener(ActionListener editListener){
        submitButton.addActionListener(editListener);
    }
}