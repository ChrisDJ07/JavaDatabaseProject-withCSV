
package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Main menu for the Database Software
 * @author Christian Dave Janiola
 */
public class MainMenu extends JFrame{
    
    JButton students = new JButton("STUDENTS");
    JButton courses = new JButton("COURSES");
    
    public MainMenu(){
        super("Database Menu");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1, 20, 10));
        this.add(panel);
        panel.setBounds(95, 150, 100, 200);
        
        JLabel label = new JLabel("Database Main Menu");
        this.add(label);
        label.setFont(new java.awt.Font("Unispace", 1, 18));
        label.setBounds(40, 25, 225, 100);
        
        
        panel.add(students);
        panel.add(courses);
        students.setSize(75, 30);
        students.setSize(75, 30);
        
        this.setVisible(true);
        
    }
    
    public static void main(String args[]) {
        MainMenu menu = new MainMenu();
    }
}
