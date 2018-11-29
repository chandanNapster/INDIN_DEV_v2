/***
 * Author: Chandan_Sharma
 */
package INDIN_DEV_v2;

public class Node {
    private String name;
    private String api;
    private String channel;
    private String implementationType;
    private int relRef;

    public Node(){

    }

    public Node(String name, String api, String channel, String implementationType, int relRef){
        this.name = name;
        this.relRef = relRef;
        if(api == "null"){ this.api = "EMPTY";}
        else{this.api = api;}
        if(channel == "null"){this.channel = "EMPTY";}
        else {this.channel = channel;}
        if(implementationType == "null"){this.implementationType = "EMPTY";}
        else{this.implementationType = implementationType;}
    }


    public int getRelRef(){
        return relRef;
    }

    public void nodeTest(){
        System.out.println(this.name + " : " + this.relRef);
    }


    public String toString(){
        return name + "|" + api +"|"+ channel +"|" + implementationType + ":" + relRef ;
    }








}
