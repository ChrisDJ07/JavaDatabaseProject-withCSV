
package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Edit interface for the database software.
 * @author Christian Dave Janiola
 */
public class SelectFrame extends JFrame{
    
    //init frame dimensions
    int width = 400;
    int height = 200;
    
    JButton selectButton = new JButton("SELECT");
    JLabel label;
    JComboBox list;
    
    public SelectFrame(String title, int type){
        super(title);
        
        //init label based on type (students = 0, courses = 1)
        if(type == 0){
           label = new JLabel("SELECT STUDENT:");
        }
        if(type == 1){
           label = new JLabel("SELECT COURSE:");
        }
        
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
    public void setList(String[] data){
        list = new JComboBox(data);
        this.add(list);
        list.setBounds(150, 40, 150, 30);
    }
    
    /*Return current selected student/course*/
    public int getSelected(){
        return list.getSelectedIndex();
    }

}
