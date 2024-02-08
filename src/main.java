
import controller.DatabaseController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.DatabaseModel;
import view.DatabaseFrame;
import view.MainMenu;


/**
 * Main program for the database software
 * @author Christian Dave Janiola
 */
public class main {
    
    static MainMenu main;

    public static void main(String args[]) {
        main = new MainMenu();
        main.addCoursesListener(new coursesListener());
        main.addStudentsListener(new studentsListener());
    }

    private static class studentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DatabaseFrame studentDB =  new DatabaseFrame("Student Database", 0);
            DatabaseModel modelDB = new DatabaseModel();
            DatabaseController controller = new DatabaseController(studentDB, modelDB, 0);
        }
    }
    
    private static class coursesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DatabaseFrame coursesDB =  new DatabaseFrame("Courses Database", 1);
            DatabaseModel modelDB = new DatabaseModel();
            DatabaseController controller = new DatabaseController(coursesDB, modelDB, 1);
        }
    }
}
