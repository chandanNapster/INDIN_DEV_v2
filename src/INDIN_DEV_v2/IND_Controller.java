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
    private static IND_View view;
    private String patternString;
    private ArrayList<INDIN_DEV_v2.Node> nodeList;
    private ArrayList<INDIN_DEV_v2.Edge> edgeList;
    private ArrayList<Object> object = new ArrayList<>();


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
        view = new IND_View();
        JFrame frame = new JFrame("INDIN GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
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
                if(e.getSource() == view.getButtonInfo()){
                    patternString = view.JTextFieldString();
                    object = model.searchPattern(patternString);
                    ArrayList<QueryResult> queryResultsList = new ArrayList();
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
                    for (QueryResult result: queryResultsList) {
                        System.out.println(result.toString());
                    }
                }
            }
        });
    }

    public static void main(String[] arg) throws Exception {
        IND_Controller controller = new IND_Controller();
        // System.out.println(model.getDriver());
        //model.close();
        controller.addListeners();
        //model.close();
    }
}
