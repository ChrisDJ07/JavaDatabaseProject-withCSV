
package view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Frame class for the student and course database interface.
 * @author Christian Dave Janiola
 */
public class DatabaseFrame extends JFrame{

    //int designation for frame dimensions
    int frameHeight = 600;
    int frameWidth = 1250;
    
    JPanel titlePanel;
    JLabel title;
    
    JPanel tablePanel;
    public JTable table;
    public DefaultTableModel tableModel;
    public TableRowSorter<DefaultTableModel> sorter;
    String[] columns;
    
    JPanel buttonPanel;
    
    JButton editKey;
    JButton addKey;
    JButton deleteKey;
    JButton clearKey;
    
    JTextField searchField;
    JLabel searchDescription;
    
    private static boolean changed; //toggles into true if there's a change in course data
    private static String[][] changedData; //stores the row information of the updated student data
    
    public DatabaseFrame(String name, int type){
        super(name);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(frameWidth+20,frameHeight-50);
        this.setLocationRelativeTo(this);
        
    /*Panel for the Title*/
        titlePanel = new JPanel();
        this.add(titlePanel);
        titlePanel.setBounds(4,2, frameWidth-4, frameHeight/10);
        titlePanel.setLayout(null);
        
        //Title Labels
        if(type == 1){
            title = new JLabel("COURSE DATABASE");
            this.setSize(1200, 550); //sets different frame dimensions for course database
            searchDescription = new JLabel("(Search Course ID or Name)");
            this.add(searchDescription);
            searchDescription.setBounds(560,(frameHeight/10)+(frameHeight/2)+107-65, 200, 10);
            searchDescription.setFont(new Font("Arial", Font.PLAIN, 12));
        }
        if(type == 0){
            title = new JLabel("STUDENT DATABASE");
            searchDescription = new JLabel("(Search Student Name, Gender, ID, Year, Course Name)");
            this.add(searchDescription);
            searchDescription.setBounds(468,(frameHeight/10)+(frameHeight/2)+107-65, 350, 10);
            searchDescription.setFont(new Font("Arial", Font.PLAIN, 12));
        }
        
        titlePanel.add(title);
        title.setFont(new java.awt.Font("Unispace", 1, 24));
        title.setBounds(500,5, frameWidth/3, (frameHeight/10)-5);
        
    /*The students/course data table*/
        tablePanel = new JPanel();
        this.add(tablePanel);
        tablePanel.setBounds(4,(frameHeight/10)+4, frameWidth-4, (frameHeight/2)-2);
        tablePanel.setLayout(null);
            tablePanel.setBorder(BorderFactory.createEtchedBorder());
        
    /*Button Panel*/
        buttonPanel = new JPanel();
        this.add(buttonPanel);
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(360,(frameHeight/2)+110, frameWidth/2, frameHeight/10);
        
    /*Adding buttons to the interface*/
        editKey = new JButton("EDIT Field");
        addKey = new JButton("ADD Field");
        deleteKey = new JButton("DELETE Field");
        clearKey = new JButton("CLEAR List");
        buttonPanel.add(editKey);
        buttonPanel.add(addKey);
        buttonPanel.add(deleteKey);
        buttonPanel.add(clearKey);
        addKey.setBounds(0, 10, 120,50);
        editKey.setBounds(125, 10, 120,50);
        deleteKey.setBounds(250, 10, 120,50);
        clearKey.setBounds(375, 10, 120,50);
        
    /*Searching*/
        searchField = new JTextField();
        JLabel searchLabel = new JLabel("Search:");
        this.add(searchField);
        this.add(searchLabel);
        searchField.setBounds(480,(frameHeight/10)+(frameHeight/2)+107-100, frameWidth/4-4, frameHeight/20);
        searchLabel.setBounds(420,(frameHeight/10)+(frameHeight/2)+109-100, 70, 20);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        /*
        Check every time the student (type = 0) frame is opened/(in focus) if course data is changed
        and prompts the user.
        */ 
        if(type == 0){
            this.addWindowFocusListener(new WindowFocusListener(){ //adding windowFocusListener
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    if(changed){ //check for "changed" boolean status
                        JOptionPane.showMessageDialog(null, "Change in Course Data detected"); //prompts user
                        tableModel.setRowCount(0); //deletes all active rows
                        for(String[] row: changedData){ //add rows to the table from the "changedData" 2d String
                            tableModel.addRow(row);
                        }
                    setChangeStatus(false); //toggles "changed" boolean status to false
                    }
                }
                @Override public void windowLostFocus(WindowEvent e) {}
            });
        }
        this.setVisible(true);
    }
    
    // adding component listeners
    public void addEditListener(ActionListener editListener){
        editKey.addActionListener(editListener);
    }
    public void addAddListener(ActionListener addListener){
        addKey.addActionListener(addListener);
    }
    public void addDeleteListener(ActionListener deleteListener){
        deleteKey.addActionListener(deleteListener);
    }
    public void addClearListener(ActionListener clearListener){
        clearKey.addActionListener(clearListener);
    }
    public void addSearchListener(KeyListener searchListener){
        searchField.addKeyListener(searchListener);
    }
    
    // Method to generate the table area in the frame.
    public void generateTable(String[][] tableData, int type){
        if(type == 0){
           columns = new String[]{"Name", "Gender", "ID", "Year Level","Course"}; // assigns column headers for student data (type = 0)
        }
        if(type == 1){
           columns = new String[]{"Course Code", "Course Name"}; // assigns column headers for course data (type = 1)
        }
        
        /*Adding Table to the tablePanel*/
        tableModel = new DefaultTableModel(tableData, columns);
        table = new JTable(tableModel);
        //add sorter
        table.setAutoCreateRowSorter(true);
        
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        tablePanel.add(new JScrollPane(table));
        if(type == 1){ //adjusts table position for course data table
            tablePanel.getComponent(0).setBounds(0,0, frameWidth-71, frameHeight/2);
        }else{
            tablePanel.getComponent(0).setBounds(0,0, frameWidth-3, frameHeight/2); //default value for student data table
        }
    }
    
    /*
    Method that is called when there is a changed in course code in the courses database.
    Prompts the user if they wanted to update student data or not.
    If they choose to update student data, all students enrolled in the course will also update their
    course code to the updated one.
    If they choose not to update student data, the student will retain the out of date course code (
    will display "N/A course does not exist"
    If they chose neither, the operation will be cancelled.
    ).
    */
    public String codeChanged(){
        int option = JOptionPane.showConfirmDialog(null, "Course Code change detected, update Student Data?"
                                      , "Course Code change alert", JOptionPane.YES_NO_CANCEL_OPTION);
        if(option == JOptionPane.YES_OPTION){
            return "yes";
        }
        else if(option == JOptionPane.NO_OPTION){
            return "no";
        }
        return "cancel";
    }
    //toggles the "changed" boolean variable to true/false
    public void setChangeStatus(boolean status){
        DatabaseFrame.changed = status;
    }
    //set the value of "changedData" 2d String
    public void setChangeData(String[][] data){
        DatabaseFrame.changedData = data;
    }
    //add filter to row sorter
    public void addFilter(){
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(searchField.getText())));
    }
    //clear searchField
    public void clearSearch(){
        searchField.setText("");
        addFilter();
    }
}
