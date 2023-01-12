public class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public K getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }

    public void updateKey(K key){
        this.key = key;
    }

    public String toString(){
        return value.toString();
    }
    
}
