/**
 * Author: Chandan_Sharma
 */
package INDIN_DEV_v2;

public class Edge {

    private int value;
    private int relRef;
    private String key;

    public Edge(int value,
                int relRef,
                String key){
        this.relRef = relRef;
        this.value = value;
        this.key = key;
    }

    public int getRelRef(){
        return relRef;
    }

    public void edgeTest(){
        System.out.println("Weight " + value + " : " + this.relRef);
    }
    public String toString(){
        return key + " --> " + "Weight" + "->" + value + ":" + relRef;
    }

}
