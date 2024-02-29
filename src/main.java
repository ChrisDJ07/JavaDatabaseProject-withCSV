
import controller.DatabaseController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.DatabaseModel;
import view.DatabaseFrame;
import view.MainMenu;


/**
 * Main program for the database software
 * @author Christian Dave Janiola
 */
public class main {
    
    static MainMenu main; //main object

    public static void main(String args[]) {
        main = new MainMenu();
        main.addCoursesListener(new coursesListener()); //listener for course button
        main.addStudentsListener(new studentsListener()); //listener for student button
        new DatabaseController(new DatabaseModel()); //initializing static model instannce using a constructor in DatabaseController
    }

    private static class studentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DatabaseFrame studentDB =  new DatabaseFrame("Student Database", 0);//frame object for student database
            new DatabaseController(studentDB, 0, main); // instance of DatabaseController, arguements: studentDB,
                                                                  // 0 - int assignment for students, main - main object to enable back student button
            main.students.setEnabled(false); // disables button to allow only one instance of student frame and object
        }
    }
    
    private static class coursesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DatabaseFrame coursesDB =  new DatabaseFrame("Courses Database", 1); //frame object for course database
            new DatabaseController(coursesDB, 1, main); // instance of DatabaseController, arguements: coursesDB,
                                                                  // 1 - int assignment for courses, main - main object to enable back course button
            
            main.courses.setEnabled(false); // disables button to allow only one instance of course frame and object
        }
    }
}
