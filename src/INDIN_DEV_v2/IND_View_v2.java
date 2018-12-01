package INDIN_DEV_v2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IND_View_v2 {
    private JPanel MainPanel;
    private JPanel searchBarPanel;
    private JTextField queryField;
    private JPanel buttonPanel;
    private JButton searchButton;
    private JPanel resultsPanel;
    private JButton closeDBconnection;
    private JList resultList;
    private ArrayList<QueryResult> queryResultsList;

    public JPanel getMainPanel(){
        return this.MainPanel;
    }

    public JList getResultList(){
        return this.resultList;
    }

    public void addIND_ViewListener(ActionListener listener){
        searchButton.addActionListener(listener);
        closeDBconnection.addActionListener(listener);
    }
    public JButton getsearchButton(){
        return searchButton;
    }
    public JButton getCloseDBconnectionButton(){return closeDBconnection;}

    public String JTextFieldString(){
        return queryField.getText();
    }

}
