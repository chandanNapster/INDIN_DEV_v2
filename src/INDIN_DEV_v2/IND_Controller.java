/**
 * Author: Chandan_Sharma
 */
package INDIN_DEV_v2;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Value;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class IND_Controller {
    private static IND_Model model;
    private static IND_View_v2 view;
    private String patternString;
    private List<Record> resultList;
    private List<String> resultHeader;

    private IND_Controller(){
        /**
         * MODEL INITIALIZATION
         */
        model = new IND_Model("bolt://localhost:11004",
                                "neo4j",
                                "chandan");
        /**
         * VIEW INITIALIZATION
         */
        view = new IND_View_v2();
        JFrame frame = new JFrame("INDIN GUI");
        frame.getContentPane().add(view.getMainPanel());
        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(new Point((d.width/2) - (frame.getWidth()/2),(d.height/2) - frame.getHeight()/2));
        frame.setVisible(true);
    }

    private void addListeners(){
        view.addIND_ViewListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == view.getsearchButton()){
                    patternString = view.JTextFieldString();
                    resultList = new ArrayList<>();
                    resultHeader = new ArrayList<>();
                    resultList = model.searchPattern(patternString);
                    List<List<Value>> recordVal = new ArrayList<>();
                    ArrayList<String> toAddList = new ArrayList<>();
                    DefaultListModel<String> toAddArrayList = new DefaultListModel();
                    StringBuilder sb1 = new StringBuilder();

                    for(Record record: resultList){
                        resultHeader = record.keys();
                        break;
                    }
                    for(Record record: resultList){
                        recordVal.add(record.values());
                    }
                    for(int i =0 ; i < recordVal.size(); i++){
                        StringBuilder sb = new StringBuilder();
                        for(int j = 0; j < recordVal.get(i).size() - 1; j++){
                            String s1 = recordVal.get(i)
                                    .get(j)
                                    .toString()
                                    .replace('"', ' ') + " | ";
                            sb.append(s1);
                        }
                        toAddList.add(sb.toString());
                    }
                    for(String header1: resultHeader){
                        sb1.append(header1).append(" | ");
                    }
                    toAddArrayList.addElement(sb1.toString());
                    for(String s : toAddList){
                        toAddArrayList.addElement(s);
                    }
                    view.getResultList().setModel(toAddArrayList);
                }
                else if(e.getSource() == view.getCloseDBconnectionButton()){
                    try {
                        model.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });
    }
   // (a)-[r]->(b)<-[t]-(v)

    public static void main(String[] arg) throws Exception {
        IND_Controller controller = new IND_Controller();
        controller.addListeners();
    }
}