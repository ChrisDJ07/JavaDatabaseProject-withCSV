
package view;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Frame class for the main interface and also
 * the student table interface.
 * @author Christian Dave Janiola
 */
public class DatabaseFrame extends JFrame{

    int frameHeight = 600;
    int frameWidth = 1250;
    
    JPanel titlePanel;
    JLabel title;
    
    JPanel tablePanel;
    public JTable table;
    public DefaultTableModel tableModel;
    String[] columns;
    
    JPanel buttonPanel;
    
    JButton editKey;
    JButton addKey;
    JButton deleteKey;
    JButton clearKey;
    
    private static boolean changed;
    private static String[][] changedData;
    
    public DatabaseFrame(String name, int type){
        super(name);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(frameWidth+20,frameHeight+4);
        this.setLocationRelativeTo(this);
        
    /*Panel for the Title*/
        titlePanel = new JPanel();
        this.add(titlePanel);
        titlePanel.setBounds(4,2, frameWidth-4, frameHeight/10);
        titlePanel.setLayout(null);
        
        //Title Label
        if(type == 1){
            title = new JLabel("COURSE DATABASE");
        }
        if(type == 0){
            title = new JLabel("STUDENT DATABASE");
        }
        
        titlePanel.add(title);
        title.setFont(new java.awt.Font("Unispace", 1, 24));
        title.setBounds(500,5, frameWidth/3, (frameHeight/10)-5);
        
    /*The students data table*/
        tablePanel = new JPanel();
        this.add(tablePanel);
        tablePanel.setBounds(4,(frameHeight/10)+4, frameWidth-4, (frameHeight/2)-2);
        tablePanel.setLayout(null);
            tablePanel.setBorder(BorderFactory.createEtchedBorder());
        
    /*Button Panel*/
        buttonPanel = new JPanel();
        this.add(buttonPanel);
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(4,(frameHeight/2)+100, frameWidth-4, frameHeight/10);
        
    /*Adding buttons to the interface*/
        editKey = new JButton("EDIT Field");
        addKey = new JButton("ADD Field");
        deleteKey = new JButton("DELETE Field");
        clearKey = new JButton("CLEAR List");
        buttonPanel.add(editKey);
        buttonPanel.add(addKey);
        buttonPanel.add(deleteKey);
        buttonPanel.add(clearKey);
        addKey.setBounds(340, 5, 120,50);
        editKey.setBounds(465, 5, 120,50);
        deleteKey.setBounds(590, 5, 120,50);
        clearKey.setBounds(715, 5, 120,50);
        
        if(type == 0){
            this.addWindowFocusListener(new WindowFocusListener(){
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    if(changed){
                        JOptionPane.showMessageDialog(null, "Change in Course Data detected");
                        tableModel.setRowCount(0);
                        for(String[] row: changedData){
                            tableModel.addRow(row);
                        }
                    setChangeStatus(false);
                    }
                }
                @Override public void windowLostFocus(WindowEvent e) {}
            });
        }
        this.setVisible(true);
    }
    
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
    
    public void generateTable(String[][] tableData, int type){
        if(type == 0){
           columns = new String[]{"Name", "Gender", "ID", "Year Level","Course"};
        }
        if(type == 1){
           columns = new String[]{"Course Code", "Course Name"};
        }
        
        /*Adding Table to the tablePanel*/
        tableModel = new DefaultTableModel(tableData, columns);
        table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table));
        tablePanel.getComponent(0).setBounds(0,0, frameWidth-4, frameHeight/2);
    }
    
    public boolean codeChanged(){
        int option = JOptionPane.showConfirmDialog(null, "Course Code change detected, update Student Data?"
                                      , "Course Code change alert", 2);
        if(option == JOptionPane.YES_OPTION){
            return true;
        }return false;
    }
    public void setChangeStatus(boolean status){
        DatabaseFrame.changed = status;
    }
    public void setChangeData(String[][] data){
        DatabaseFrame.changedData = data;
    }
}