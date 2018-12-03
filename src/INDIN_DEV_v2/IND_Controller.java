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
    private ArrayList<INDIN_DEV_v2.Node> nodeList;
    private ArrayList<INDIN_DEV_v2.Edge> edgeList;
    private List<Record> resultList;
    private List<String> resultHeader;
    private List<List<Value>> resultValue;
    private ArrayList<QueryResult> queryResultsList;
    private boolean setHeader = true;



    private IND_Controller(){
        /*
         * MODEL INITIALIZATION
         */
        model = new IND_Model("bolt://localhost:11004",
                                "neo4j",
                                "chandan");
        /*
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
        nodeList = new ArrayList<>();
        edgeList = new ArrayList<>();
        view.addIND_ViewListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == view.getsearchButton()){

                    patternString = view.JTextFieldString();
                    resultList = new ArrayList<>();
                    resultHeader = new ArrayList<>();
                    resultList = model.searchPattern(patternString);
                    resultValue = new ArrayList<List<Value>>();



                    for(Record record: resultList){
                        resultHeader = record.keys();
                        break;
                    }
                    if(setHeader) {
                        for (String head : resultHeader) {
                            //System.out.println(head);
                            view.setHeaderArea(head);
                        }
                    }
                    setHeader = false;

                    List<List<Value>> recordVal = new ArrayList<>();

                    ArrayList<String> toAddList = new ArrayList<>();

                    for(Record record: resultList){
                        //System.out.println(record.values());
                        recordVal.add(record.values());
                    }


                    for(int i =0 ; i < recordVal.size(); i++){
                        //System.out.println(recordVal.elementAt(i).get(i).asString());
                        StringBuilder sb = new StringBuilder();
                        for(int j = 0; j < recordVal.get(i).size(); j++){
                            //System.out.println(recordVal.elementAt(i).get(j).toString().replace('"', ' '));
                            String s1 = recordVal.get(i).get(j).toString().replace('"', ' ') + " | ";
                            sb.append(s1);
                        }
                       // System.out.println(sb.toString());
                        toAddList.add(sb.toString());
                    }



                    DefaultListModel<String> toAddArrayList = new DefaultListModel();
                    for(String s : toAddList){
                        toAddArrayList.addElement(s);
                    }

                    view.getResultList().setModel(toAddArrayList);
                   // System.out.println(resultValue);


//(a)-[r]->(b)<-[f]-(d)
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

    public static void main(String[] arg) throws Exception {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(Exception ignored){

        }
        IND_Controller controller = new IND_Controller();
        // System.out.println(model.getDriver());
        //model.close();
        controller.addListeners();

//todo i need to re call the view here.
        //model.close();
    }
}


////                    //Record record = (Record) resultList;
////                    //System.out.println(record.keys());
////
////                    Iterator<String> iterator = null;
////
////                    for(Record record1: resultList){
////                        iterator = record1.keys().iterator();
////                        break;
////                    }
////
////                    while(iterator.hasNext()){
////                        System.out.println(iterator.next());
////                    }
////
//////                    System.out.println("----------------------");
////
////                    DefaultListModel<Map<String, Object>>
////                            recordDefaultListModel = new DefaultListModel();
////                    for(Record record1: resultList){
////                        recordDefaultListModel.addElement(record1.asMap());
////                    }
////                    view.getResultList().setModel(recordDefaultListModel);
