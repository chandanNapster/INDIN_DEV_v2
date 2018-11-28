package INDIN_DEV_v2;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;

import java.util.*;

public class IND_Model implements AutoCloseable{

    private Driver driver;
    //private ArrayList<Character> nodeEdgeVar;
    private String pString;
    private ArrayList<INDIN_DEV_v2.Node> nodeData;
    private ArrayList<Edge> edgeData;
    private StatementResult key;
    private Set<String> nodeKeys;
    private Set<String> edgeKeys;


    public IND_Model(String uri, String user, String password){
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
        }
        catch (DatabaseException e){
            System.out.println("Database Exception1" + e.getMessage());
        }
    }


    private void searchKeys(){
        try(Session session = driver.session(AccessMode.READ)){
            StatementResult result = session.run("MATCH" + pString +
                                                " RETURN * LIMIT 5");
            List<String> keys = result.keys();
            nodeKeys = new HashSet();
            edgeKeys = new HashSet();
            for(Record record :result.list()){
                for(int i =0; i < keys.size(); i++) {
                    if(record.asMap()
                            .get(keys.get(i))
                            .toString()
                            .substring(0,1)
                            .equals("n")){
                        nodeKeys.add(keys.get(i));
                    }
                    else if (record.asMap()
                            .get(keys.get(i))
                            .toString()
                            .substring(0,1)
                            .equals("r")){
                        edgeKeys.add(keys.get(i));
                    }
                }
            }
        }
    }


    //todo Idea try returning nodedata and edgedata from this function
    //todo look at how can we incorporate the filter functions
    private void searchPattern(String pString){
        searchKeys();
            String r = "";
        for(String a: nodeKeys){
            System.out.println("NodeKeys " + a);
        }

        for(String r1: edgeKeys){
            r = r1;
            System.out.println("EdgeKeys " + r1);
        }
        nodeData = new ArrayList();
        edgeData = new ArrayList();
//        List<String> relationshipKeys = new ArrayList();
//        List<String> nodeKeys = new ArrayList();
        //List<> values = new ArrayList();
        //Record record = null;
        //getNodeEdgeVar();
        try(Session session = driver.session(AccessMode.READ)) {
            StatementResult result = session.run("MATCH " + pString + " WHERE " + r + ".value > 4" +
                    " RETURN *");
//                                                    + nodeEdgeVar.get(0)+ ".name AS name"
//                                                    + nodeEdgeVar.get(1)+ ".");

            List<String> keys = result.keys();
//            for(String k:keys){
//                System.out.println(k);
//
//            }

            for(Record record :result.list()){
                for(int i =0; i < keys.size(); i++) {


                    System.out.println(i + "::::" +record.asMap().get(keys.get(i)).toString().substring(0,2));

                    if(record.asMap()
                            .get(keys.get(i))
                            .toString()
                            .substring(0,1)
                            .equals("n")){
                        //System.out.println(true);
                        //relationshipKeys.add(keys.get(i));
                        Node hyb = record.get(keys.get(i)).asNode();
                        INDIN_DEV_v2.Node n = new INDIN_DEV_v2.Node(
                                hyb.get("name").asString(),
                                hyb.get("APIclient").asString(),
                                hyb.get("Channel").asString(),
                                hyb.get("implementationType").asString());
                        nodeData.add(n);
                        //System.out.println( record.asMap() + "::" + keys.get(i) + "::" + i + "::" + n.toString());
                    }

                    else if (record.asMap()
                            .get(keys.get(i))
                            .toString()
                            .substring(0,1)
                            .equals("r")){
                        Relationship rel = record.get(keys.get(i)).asRelationship();
                        INDIN_DEV_v2.Edge e = new INDIN_DEV_v2.Edge(
                                rel.get("value").asInt());
                        edgeData.add(e);
                       // System.out.println( record.asMap()+ "::" + keys.get(i) + "::" + i + "::"+ e.toString());
                    }
//
//                    System.out.println(record.get(keys.get(i)).asNode());
//                    System.out.println(record.get(keys.get(i)).asRelationship());
//
//                    if(keys.contains("Node")){
//                        System.out.println("Node");
//                    }
//
//
//                    System.out.println(hyb.labels());
////                    //System.out.println(keys.get(i));
////
//                    Neo_Java_MVC.Node n = new Neo_Java_MVC.Node(
//                            hyb.get("name").asString(),
//                            hyb.get("APIclient").asString(),
//                            hyb.get("Channel").asString(),
//                            hyb.get("implementationType").asString());
//
//
//                    Relationship edg = record.get(keys.get(i)).asRelationship();
//
//                    Neo_Java_MVC.Edge e = new Neo_Java_MVC.Edge(edg.get("value").asInt());
//                    System.out.println(keys.get(i) +"::"+ e.toString());

                }
            }

            //result.list();
//            while(result.hasNext()){
//                Record record = result.next();
//
//
//                // record = result.next();
//
//
//
//
//                nodeData.add(record);

            //values.add(record.values());

//                      Node node = null;
//            for(int i =0; i < record.size(); i++){
//                node = record.get(i).asNode();
//
//            }
//            List l = new ArrayList();
//            l = (List) node.labels();
//
//            for(Object s: l){
//                System.out.println(s);
//            }

        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }

//        for(Record noded : nodeData){
//            Node tech = nodeData.get("Hybrid").asNode();
//        }

        //for(Node node: )

        for(INDIN_DEV_v2.Node node: nodeData){
            System.out.println(node);
        }

        for(Edge edge: edgeData){
            System.out.println(edge);
        }


    }

    public void searchPat(String pString){
        this.pString = pString;
        searchPattern(pString);
    }

//    public String getString(String patternString){
//        pString = patternString;
//        return patternString;
//    }

//    private ArrayList<Character> getNodeEdgeVar(){
//
//        //char[] ch =
//        char[] ch = pString.toCharArray();
//        nodeEdgeVar = new ArrayList();
//
//        for(int i =0; i < ch.length; i++){
//            if(ch[i] == '(' || ch[i] == ')' ||
//                    ch[i] == '-' ||
//                    ch[i] == '>' ||
//                    ch[i] == '<' ||
//                    ch[i] == '[' ||
//                    ch[i] == ']'){
//                continue;
//            }
//            else{
//                nodeEdgeVar.add(ch[i]);
//            }
//        }
//
//        for(Character ch1 : nodeEdgeVar){
//            System.out.println(ch1);
//        }
//
//        return nodeEdgeVar;
//    }


    @Override
    public void close() throws Exception {
        //driver.close();
    }
}
