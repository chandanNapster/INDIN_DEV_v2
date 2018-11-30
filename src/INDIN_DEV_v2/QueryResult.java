package INDIN_DEV_v2;

public class QueryResult {

    public INDIN_DEV_v2.Node firstNode;
    public INDIN_DEV_v2.Node secondNode;
    public INDIN_DEV_v2.Edge edge;

    public QueryResult(INDIN_DEV_v2.Node firstNode,
                       INDIN_DEV_v2.Node secondNode,
                       INDIN_DEV_v2.Edge edge ){

        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.edge = edge;
    }

    public String toString(){
        return firstNode.toString() + "::" + edge.toString() + "::"  + secondNode.toString();
    }


}
