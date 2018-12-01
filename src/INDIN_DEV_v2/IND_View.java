/***
 * Author: Chandan_Sharma
 */
package INDIN_DEV_v2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IND_View extends JPanel {

    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 600;
    private final int SEARCH_FIELD_WIDTH = 200;
    private final int SEARCH_FIELD_HEIGHT = 20;
    private JPanel buttonPanel,
            queryPanel,
            listPanel;

    private JTextField queryField;
    private JButton searchPattern;
    private JList queryResults;
    private ScrollPane scrollPane;
    private JRadioButton filter;
    private ArrayList<QueryResult> queryResultsList;
    private boolean flag = false;

    public IND_View(){
        super(new BorderLayout());
        queryResultsList = new ArrayList<>();
        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        queryField = new JTextField();
        queryField.setPreferredSize(new Dimension(SEARCH_FIELD_WIDTH, SEARCH_FIELD_HEIGHT));
        queryField.setFont(new Font("Verdana", Font.BOLD, 12));
        buttonPanel = new JPanel();
        searchPattern = new JButton("Search Pattern");
        buttonPanel.add(searchPattern);
        queryPanel = new JPanel();
        queryPanel.add(queryField);
        add(buttonPanel, BorderLayout.SOUTH);
        add(queryPanel, BorderLayout.NORTH);
    }

    public IND_View(boolean drawJList, ArrayList<QueryResult> queryResultsList){
        super(new BorderLayout());

        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        listPanel = new JPanel();
        queryResults = new JList();
        this.queryResultsList = queryResultsList;
        if(drawJList == true) {
            queryResults.setModel(getQueryResults());
        }

        listPanel.add(queryResults);
        listPanel.setBackground(Color.PINK);
        listPanel.setVisible(true);
        add(listPanel, BorderLayout.CENTER);
    }

    private DefaultListModel getQueryResults() {
        DefaultListModel<QueryResult> resultsList = new DefaultListModel<>();
        for (QueryResult queryResult: queryResultsList) {
            resultsList.addElement(queryResult);
        }
        return resultsList;
    }

    public String JTextFieldString(){
        return queryField.getText();
    }

    public void addIND_ViewListener(ActionListener listener){
        searchPattern.addActionListener(listener);
    }

    public JButton getButtonInfo(){
        return searchPattern;
    }
}
