/***
 * Author: Chandan_Sharma
 */
package INDIN_DEV_v2;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;
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


   public String testLogic(String patternString) {
       try (Session session = driver.session(AccessMode.READ)) {
           StatementResult result = session.run("MATCH " + patternString +
                   " WHERE 1 = 1 " +
                   " RETURN * LIMIT 1");
           List<String> Keys = result.keys();
           HashMap<String,Iterator> propMap = new HashMap<>();
           for (Record record : result.list()) {
               for (int i = 0; i < Keys.size(); i++) {
                   propMap.put(Keys.get(i), record.get(Keys.get(i)).keys().iterator());
               }
           }

           StringBuilder sb = new StringBuilder();
           String filterString = "";
            for(String key: propMap.keySet()){
                while(propMap.get(key).hasNext()){
                    String recordString = "";
                    recordString = propMap.get(key).next().toString();
                    sb.append(key +"."+recordString)
                            .append(" AS ")
                            .append(key+recordString.toUpperCase())
                            .append(" , ");
                }

            }
           filterString = sb.toString();
           return filterString;
       }
   }

    public List<Record> searchPattern(String patternString){
        String testString = testLogic(patternString);
        StatementResult result = null;
        this.patternString = patternString;
        subGraphData = new ArrayList<>();
        try(Session session = driver.session(AccessMode.READ)) {
            result = session.run("MATCH " + patternString +
                    " WHERE 1 = 1 " +
                    " RETURN " + testString + " 1 " + " LIMIT 5"); // passing a value 1 because
                                                                    // i was getting an extra comma in the end.
        }
        return result.list();
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
