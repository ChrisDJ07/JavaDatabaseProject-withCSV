
package view;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Main menu for the Database Software
 * @author Christian Dave Janiola
 */
public class MainMenu extends JFrame{
    
    JButton students;
    JButton courses;
    
    public MainMenu(){
        super("Database Menu");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
        
    }
    
    public static void main(String args[]) {
        MainMenu menu = new MainMenu();
    }
}
