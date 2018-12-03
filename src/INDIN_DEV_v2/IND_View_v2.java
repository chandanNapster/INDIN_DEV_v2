package INDIN_DEV_v2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IND_View_v2 {
    private JPanel MainPanel;
    private JPanel MenuPanel;
    private JTextField queryField;
    private JButton searchButton;
    private JPanel resultsPanel;
    private JButton closeDBconnection;
    private JList resultList;
    private JPanel serachPanel;
    private JPanel SubmenuPanel;
    private JPanel ResultSheet;
    private JTextArea HeaderArea;
    private JScrollPane scrollPane;
    private JList resultListHeader;



    public JPanel getMainPanel(){
        return this.MainPanel;
    }

    public JList getResultList(){
        return this.resultList;
    }

    public JList getResultListHeader(){
        return this.resultListHeader;
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

    public void setHeaderArea(String header){
            HeaderArea.append(header + "|");
    }

    public JScrollPane getJScrollpane(){
        return this.scrollPane;
    }

//    public void deleteHeaerArea(String header){
//        header.replaceAll(header, "");
//    }

}
