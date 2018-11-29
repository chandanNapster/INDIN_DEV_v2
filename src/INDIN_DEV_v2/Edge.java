package INDIN_DEV_v2;

public class Edge {

    private int value;
    private int relRef;

    public Edge(int value, int relRef){
        this.relRef = relRef;
        this.value = value;
    }

    public int getRelRef(){
        return relRef;
    }

    public void edgeTest(){
        System.out.println("Weight " + value);
    }
    public String toString(){
        return "Weight" + "->" + value + ":" + relRef;
    }

}
