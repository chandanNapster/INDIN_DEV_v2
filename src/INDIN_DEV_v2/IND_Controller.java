package INDIN_DEV_v2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class IND_Controller {
    private static IND_Model model;
    private static IND_View view;
    private String searchString;
    private ArrayList<Node> nodeList;
    private ArrayList<Edge> edgeList;
    private ArrayList<Object> obj = new ArrayList<>();


    public IND_Controller(){
        /**
         * MODEL INITIALIZATION
         */
        model = new IND_Model("bolt://localhost:11004",
                "neo4j",
                "chandan");
        /**
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

        //IND_Controller i = new IND_Controller();
    }

    public void addListeners(){

        nodeList = new ArrayList();
        edgeList = new ArrayList();
        view.addIND_ViewListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == view.getButtonInfo()){
                    System.out.println("search");
                    System.out.println(view.JTextFieldString());
                    searchString = view.JTextFieldString();
                    obj = model.searchPat(searchString);
                    for(Object o : obj) {
                        if (o instanceof INDIN_DEV_v2.Node) {
                            nodeList.add((Node) o);
                        }
                        else if(o instanceof INDIN_DEV_v2.Edge){
                            edgeList.add((Edge) o);
                        }
                    }
                    for(Node node: nodeList){
                        for(Edge edge : edgeList){
                            if(node.getRelRef() == edge.getRelRef()){
                                System.out.println("The id is: " + edge.getRelRef() + "::" + node.getRelRef());
                                node.nodeTest();
                                edge.edgeTest();
                                System.out.println("------------------------------------");
                            }
                        }
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
        model.close();

    }
}