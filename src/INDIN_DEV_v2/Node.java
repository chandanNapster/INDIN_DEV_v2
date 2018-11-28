package INDIN_DEV_v2;

import java.awt.*;

public class Node {
    private String name;
    private String api;
    private String channel;
    private String implementationType;

    public Node(String name, String api, String channel, String implementationType){
        this.name = name;
        if(api == "null"){ this.api = "EMPTY";}
        else{this.api = api;}
        if(channel == "null"){this.channel = "EMPTY";}
        else {this.channel = channel;}
        if(implementationType == "null"){this.implementationType = "EMPTY";}
        else{this.implementationType = implementationType;}
    }





    public String toString(){
        return name + "|" + api +"|"+ channel +"|" + implementationType ;
    }








}
