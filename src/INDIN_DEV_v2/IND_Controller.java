/**
 * Author: Chandan_Sharma
 */
package INDIN_DEV_v2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IND_Controller {
    private static IND_Model model;
    private static IND_View_v2 view;
    private String patternString;
    private ArrayList<INDIN_DEV_v2.Node> nodeList;
    private ArrayList<INDIN_DEV_v2.Edge> edgeList;
    private ArrayList<Object> object;
    private ArrayList<QueryResult> queryResultsList;
    private  boolean drawJList = false;


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
                    object = new ArrayList<>();
                    object = model.searchPattern(patternString);
                    queryResultsList = new ArrayList();
                    for(Object obj: object) {
                        if (obj instanceof INDIN_DEV_v2.Node) {
                            nodeList.add((Node) obj);
                        }
                        else if(obj instanceof INDIN_DEV_v2.Edge){
                            edgeList.add((Edge) obj);
                        }
                    }
                    if(!edgeList.isEmpty()) {
                        Node firstNode = null;
                        Node secondNode = null;
                        for (Edge edge : edgeList) {
                            for (Node node : nodeList) {
                                if(edge.getRelRef() == node.getRelRef()){
                                    if(firstNode == null){
                                        firstNode = node;
                                    }
                                    else if(secondNode == null){
                                        secondNode = node;
                                    }
                                }
                            }
                            queryResultsList.add(new QueryResult(firstNode,secondNode,edge));
                            firstNode = null;
                            secondNode = null;
                        }
                    }
                    else{
                        for(Node node: nodeList){
                            System.out.println(node.toString());
                        }
                    }
                    drawJList = true;
                    DefaultListModel<QueryResult> resultsList = new DefaultListModel<>();
                    for (QueryResult queryResult: queryResultsList) {
                        resultsList.addElement(queryResult);
                    }
                    view.getResultList().setModel(resultsList);
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
        IND_Controller controller = new IND_Controller();
        // System.out.println(model.getDriver());
        //model.close();
        controller.addListeners();

//todo i need to re call the view here.
        //model.close();
    }
}
