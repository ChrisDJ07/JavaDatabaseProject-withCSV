
import controller.DatabaseController;
import model.DatabaseModel;
import view.DatabaseFrame;


/**
 * Main program for the database software
 * @author Christian Dave Janiola
 */
public class main {

    public static void main(String args[]) {
        DatabaseFrame studentDB =  new DatabaseFrame("Student Database");
        DatabaseModel modelDB = new DatabaseModel();
        DatabaseController controller = new DatabaseController(studentDB, modelDB);
    }
}
