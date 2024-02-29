
package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Main menu for the Database Software
 * @author Christian Dave Janiola
 */
public class MainMenu extends JFrame{
    
    public JButton students = new JButton("STUDENTS");
    public JButton courses = new JButton("COURSES");
    
    public MainMenu(){
        super("Database Menu");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(250,200);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1, 20, 10));
        this.add(panel);
        panel.setBounds(65, 55, 100, 200);
        
        JLabel label = new JLabel("Database Main Menu");
        this.add(label);
        label.setFont(new java.awt.Font("Unispace", 1, 18));
        label.setBounds(16, 10, 225, 50);
        
        panel.add(students);
        panel.add(courses);
        students.setSize(75, 30);
        courses.setSize(75, 30);
        
        this.setVisible(true);
    }
    
    public void addStudentsListener (ActionListener studentsListener){
        students.addActionListener(studentsListener);
    }
    
    public void addCoursesListener (ActionListener coursesListener){
        courses.addActionListener(coursesListener);
    }
}
