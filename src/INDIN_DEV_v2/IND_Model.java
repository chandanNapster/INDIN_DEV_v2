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


   public String testLogic(String patternString) {
       try (Session session = driver.session(AccessMode.READ)) {
           StatementResult result = session.run("MATCH " + patternString +
                   " WHERE 1 = 1 " +
                   " RETURN * LIMIT 1");
           List<String> Keys = result.keys();

          // ArrayList<Iterator> properties = new ArrayList<>();

           HashMap<String,Iterator> propMap = new HashMap<>();
           for (Record record : result.list()) {
               for (int i = 0; i < Keys.size(); i++) {
                   //System.out.println(Keys.get(i) + "::" +record.asMap().get(Keys.get(i)));
                   // System.out.println(record.get(i).asLocalDateTime());

                   //System.out.println(Keys.get(i) + ":" + record.get(Keys.get(i)).keys());
//                   keyString.add(Keys.get(i) + record.get(Keys.get(i)).keys());
//                   keyString1.add(record.get(Keys.get(i)).keys().iterator());

                   propMap.put(Keys.get(i), record.get(Keys.get(i)).keys().iterator());

                  // keyString.add(record.get(Keys.get(i)).keys().iterator());


                   //System.out.println(keyString);
                   //System.out.println(record.get(Keys.get(i)).asEntity().id());
                   //System.out.println(record.fields());
               }
           }

           //propMap.forEach((key, value) -> System.out.println(key + "=" + value));
           //ArrayList<String> test = new ArrayList<>();
           StringBuilder sb = new StringBuilder();
           String str = "";
            for(String key: propMap.keySet()){
                //System.out.println(key);

                while(propMap.get(key).hasNext()){
                    //System.out.println(key +"."+propMap.get(key).next());
                    String a = "";
                    a = propMap.get(key).next().toString();
                    sb.append(key +"."+a).append(" AS ").append(key+a.toUpperCase()).append(" , ");
                }

            }

           str = sb.toString();

           //System.out.println(str);
           return str;

           //(a)-[r]->(b)<-[t]-(c), (b)<-[d]-(h)

//           public Iterator getValue(Iterator value) {
//
//               for (Iterator iterator : value) {
//                   while (iterator.hasNext()) {
//                       System.out.println(iterator.next());
//                   }
//               }
//           }


//               for(String string: propMap.keySet()){
//                   Iterator i = propMap.get(string);
//                   while(i.hasNext()){
//                       System.out.println(i.next());
//                   }
//               }

//            Set set = propMap.entrySet();
//            Iterator iterator = set.iterator();
//            while(iterator.hasNext()){
//                Map.Entry entry = (Map.Entry) iterator.next();
//                System.out.println("Key is: " + entry.getKey() + "Value is: " + entry.getValue());
//                //properties = entry.getValue();
//            }

           //     for (int i = 0; i < Keys.size(); i++) {

          // ArrayList<String> properties = new ArrayList<>();
//           for (Iterator iterator : keyString) {
//               while (iterator.hasNext()) {
//                   properties.add(iterator.next().toString());
//               }
//               // System.out.println("-----");
//           }
////(a)-[r]->(b)<-[t]-(c)
//           //}
//
//           for (String keyS : Keys) {
//               for (String str : properties) {
//                   System.out.println(str);
//               }
//           }
       }
   }


//    }




    //todo look at how can we incorporate the filter functions
    //todo the filter function will be a bit different than the current search pattern function
    public ArrayList<Object> searchPattern(String patternString){

        String testString = testLogic(patternString);

        this.patternString = patternString;
        subGraphData = new ArrayList<>();
        try(Session session = driver.session(AccessMode.READ)) {
            StatementResult result = session.run("MATCH " + patternString +
                    " WHERE 1 = 1 " +
                    " RETURN " + testString + " 1 " + " LIMIT 2"); // passing a value 1 because
                                                                    // i was getting an extra comma in the end.

           // Record record = result.list();
            //List<> results = result.toString();

            //System.out.println(result.list());
            Map<String, Object> RecordMap = null;
            for(Record record: result.list()) {
               System.out.println(record.asMap());
                RecordMap = record.asMap();

            }



                //for(String k: RecordMap.keySet()){
                    //System.out.println(RecordMap.keySet());
                    //System.out.println(RecordMap);
                //}


                //System.out.println(record.asMap());
                //System.out.println("---------");
            //}

//            List<String> Keys = result.keys();
//
//            for(Record record: result.list()){
//
//            }

        }


           // List<Record> r = result.list();


//
//
//            for(Record rec:r){
//                System.out.println(rec.asMap());
//
//            }


            //todo figure out how to find nodes and relationship connection.


//            int relationshipRef = 1;
//            for(Record record :result.list()){
//                //relationshipRef++;
//                for(int i =0; i < Keys.size(); i++) {
//                    if(record.asMap()
//                            .get(Keys.get(i))
//                            .toString()
//                            .substring(0,1)
//                            .equals("n")){
//                        Node node = record.get(Keys.get(i)).asNode();
//                        INDIN_DEV_v2.Node n = new INDIN_DEV_v2.Node(
//                                node.get("name").asString(),
//                                node.get("apiClient").asString(),
//                                node.get("channel").asString(),
//                                node.get("implementationType").asString(),
//                                relationshipRef,
//                                Keys.get(i));
//                        //subGraphData.add(n);
//                    }
//                    else if (record.asMap()
//                            .get(Keys.get(i))
//                            .toString()
//                            .substring(0,1)
//                            .equals("r")){
//                        Relationship rel = record.get(Keys.get(i)).asRelationship();
//                        INDIN_DEV_v2.Edge e = new INDIN_DEV_v2.Edge(
//                                rel.get("value").asInt(),
//                                relationshipRef,
//                                Keys.get(i));
//                        //subGraphData.add(e);
//
//                    }
//                }
//            }
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }

//        for(Object obj: subGraphData){
//            System.out.println(obj.toString());
//        }

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
