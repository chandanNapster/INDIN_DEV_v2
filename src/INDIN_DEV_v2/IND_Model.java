/***
 * Author: Chandan_Sharma
 */
package INDIN_DEV_v2;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;
import java.util.*;

public class IND_Model implements AutoCloseable{

    private Driver driver;
    private String patternString;
    private ArrayList<Object> subGraphData;
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
    //todo look at how can we incorporate the filter functions
    //todo the filter function will be a bit different than the current search pattern function
    public ArrayList<Object> searchPattern(String patternString){
        this.patternString = patternString;
        subGraphData = new ArrayList<>();
        try(Session session = driver.session(AccessMode.READ)) {
            StatementResult result = session.run("MATCH " + patternString +
                                                    " WHERE 1 = 1 "  +
                                                    " RETURN * LIMIT 1");
            List<String> Keys = result.keys();

//            List<Record> r = result.list();

//            for(Record record: r){
//                System.out.println(record.asMap());
//            }

            int relationshipRef = 0;
            for(Record record :result.list()){
                relationshipRef++;
                for(String key: Keys) {
                    if(record.asMap()
                            .get(key)
                            .toString()
                            .substring(0,1)
                            .equals("n")){
                        Node node = record.get(key).asNode();
                        INDIN_DEV_v2.Node n = new INDIN_DEV_v2.Node(
                                node.get("name").asString(),
                                node.get("apiClient").asString(),
                                node.get("channel").asString(),
                                node.get("implementationType").asString(),
                                relationshipRef,
                                key);
                        subGraphData.add(n);
                    }
                    else if (record.asMap()
                            .get(key)
                            .toString()
                            .substring(0,1)
                            .equals("r")){
                        Relationship rel = record.get(key).asRelationship();
                        INDIN_DEV_v2.Edge e = new INDIN_DEV_v2.Edge(
                                rel.get("value").asInt(),
                                relationshipRef,
                                key);
                        subGraphData.add(e);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }



        return subGraphData;
    }

    private void searchKeys(){
        try(Session session = driver.session(AccessMode.READ)){
            StatementResult result = session.run("MATCH" + patternString +
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

    @Override
    public void close() throws Exception {
        driver.close();
    }
}
