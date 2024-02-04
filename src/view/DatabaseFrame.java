
package view;

import java.awt.event.ActionListener;
import javax.swing.*;
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
    public JTable studentTable;
    public DefaultTableModel tableModel;
    String[] columns = {"Name", "Gender", "ID", "Year Level","Course"};
    
    JPanel buttonPanel;
    
    JButton editKey;
    JButton addKey;
    JButton deleteKey;
    JButton clearKey;
    
    public DatabaseFrame(String name){
        super(name);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(frameWidth+20,frameHeight+4);
        
        
    /*Panel for the Title*/
        titlePanel = new JPanel();
        this.add(titlePanel);
        titlePanel.setBounds(4,2, frameWidth-4, frameHeight/10);
        titlePanel.setLayout(null);
        
        //Title Label
        title = new JLabel("STUDENT DATABASE");
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
    
    public void generateTable(String[][] tableData){
        /*Adding Table to the tablePanel*/
        tableModel = new DefaultTableModel(tableData, columns);
        studentTable = new JTable(tableModel);
        tablePanel.add(new JScrollPane(studentTable));
        tablePanel.getComponent(0).setBounds(0,0, frameWidth-4, frameHeight/2);
    }
}