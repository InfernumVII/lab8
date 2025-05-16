package shared.network.models;

import java.io.Serializable;

public class Pair<V1, V2> implements Serializable{
    V1 value1;
    V2 value2;
    public Pair(V1 value1, V2 value2){
        this.value1 = value1;
        this.value2 = value2;
    }
    public V1 getValue1() {
        return value1;
    }
    public V2 getValue2() {
        return value2;
    }
    
}