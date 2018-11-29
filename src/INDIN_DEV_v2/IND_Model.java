package INDIN_DEV_v2;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
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
    private ArrayList<Object> graphData;
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
    private ArrayList<Object> searchPattern(String pString){
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
        graphData = new ArrayList<>();
        try(Session session = driver.session(AccessMode.READ)) {
            StatementResult result = session.run("MATCH " + pString +
                                                    " WHERE 1 = 1 "  +
                                                    " RETURN * LIMIT 10");

            List<String> keys = result.keys();
            int relationshipRef = 0;

            for(Record record :result.list()){
                relationshipRef++;
                for(int i =0; i < keys.size(); i++) {
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
                                hyb.get("implementationType").asString(),
                                relationshipRef);
                        nodeData.add(n);
                        graphData.add(n);
                        //System.out.println( record.asMap() + "::" + keys.get(i) + "::" + i + "::" + n.toString());
                    }

                    else if (record.asMap()
                            .get(keys.get(i))
                            .toString()
                            .substring(0,1)
                            .equals("r")){
                        Relationship rel = record.get(keys.get(i)).asRelationship();
                        INDIN_DEV_v2.Edge e = new INDIN_DEV_v2.Edge(
                                rel.get("value").asInt(), relationshipRef);
                        edgeData.add(e);
                        graphData.add(e);
                        //System.out.println( record.asMap()+ "::" + keys.get(i) + "::" + i + "::"+ e.toString());
                    }
                }
            }
        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return graphData;
    }

    public ArrayList<Object> searchPat(String pString){
        this.pString = pString;
        return searchPattern(pString);
    }
    @Override
    public void close() throws Exception {
        //driver.close();
    }
}
