/***
 * Author: Chandan_Sharma
 */
package INDIN_DEV_v2;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import java.util.*;

public class IND_Model implements AutoCloseable{

    private Driver driver;

    public IND_Model(String uri, String user, String password){
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
        }
        catch (DatabaseException e){
            System.out.println("Database Exception1" + e.getMessage());
        }
    }

   public String getDBProperties(String patternString) {
       StringBuilder sb = new StringBuilder();
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

           String filterString = "";
            for(String key: propMap.keySet()){
                while(propMap.get(key).hasNext()){
                    String recordString = "";
                    recordString = propMap.get(key).next().toString();
                    sb.append(key +"."+recordString)
                            .append(" AS ")
                            .append(key.toUpperCase()+recordString)
                            .append(" , ");
                }
            }
           filterString = sb.toString();
           return filterString;
       }
   }

    public List<Record> searchPattern(String patternString){
        String testString = getDBProperties(patternString);
        StatementResult result = null;
        try(Session session = driver.session(AccessMode.READ)) {
            result = session.run("MATCH " + patternString +
                    " WHERE 1 = 1 " +
                    " RETURN DISTINCT " +
                    testString + " 1  " +
                    "ORDER BY 1" ); // passing a value 1 because
                                    // i was getting an extra comma in the end.
        }
        return result.list();
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}