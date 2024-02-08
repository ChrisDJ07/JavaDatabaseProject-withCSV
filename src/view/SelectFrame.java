
package view;

import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Edit interface for the database software.
 * @author Christian Dave Janiola
 */
public class SelectFrame extends JFrame{
    
    int width = 400;
    int height = 200;
    
    JButton selectButton = new JButton("SUBMIT");
    JLabel label = new JLabel("SELECT STUDENT:");
    JComboBox studentList;
    
    
    public SelectFrame(String title){
        super(title);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        
        this.add(selectButton);
        selectButton.setBounds(150, 100, 100, 30);
        
        this.add(label);
        label.setBounds(20, 40, 150, 30);
        
        this.setVisible(true);
    }
    
    public void addSelectListener(ActionListener selectListener){
        selectButton.addActionListener(selectListener);
    }
    
    /*Set-up Drop-down option*/
    public void setStudentList(String[] students){
        studentList = new JComboBox(students);
        this.add(studentList);
        studentList.setBounds(150, 40, 150, 30);
    }
    
    /*Return current selected student*/
    public int getSelectedStudent(){
        return studentList.getSelectedIndex();
    }

}
