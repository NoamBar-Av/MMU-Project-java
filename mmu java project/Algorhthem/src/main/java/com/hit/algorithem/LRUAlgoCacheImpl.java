package com.hit.algorithem;

import java.util.LinkedHashMap;

public class LRUAlgoCacheImpl<K,V> extends AbstractAlgoCache<K,V>
{
	private LinkedHashMap<K, V> HashMap;

    public LRUAlgoCacheImpl(int capacity) {
    	super(capacity);
        this.HashMap = new LinkedHashMap<K,V>();
    }

    @Override
    public V getElement(K key) {
    	if (key==null)
    			return null;
    	else {
    		if(this.HashMap.containsKey(key)){
                V value = this.HashMap.get(key);
                this.removeElement(key);
                this.putElement(key, value);
            }
    		return this.HashMap.get(key);
    	}
    }

    @SuppressWarnings("unchecked")
	@Override
    public V putElement(K key, V value) 
    {
        V last = null;
        if ((!this.HashMap.containsKey(key))){
        	if(this.HashMap.size()== this.get_capacity()) {
        		last = (V) this.HashMap.values().toArray()[this.HashMap.size()-1];
        	}
        	else {
        		//this.HashMap.put(key,value);
        		
        		LinkedHashMap<K, V> newmap=(LinkedHashMap<K, V>) HashMap.clone();
        		HashMap.clear();
        		HashMap.put(key, value);
        		HashMap.putAll(newmap);
        		
        		
        	}
        }
        else {
        	LinkedHashMap<K, V> newmap=(LinkedHashMap<K, V>) HashMap.clone();
        	HashMap.clear();
    		HashMap.put(key, value);
    		HashMap.putAll(newmap);
        }
        return last;	
    }
    
    @Override
    public void removeElement(K key) 
    {
        HashMap.remove(key);
    }
    public void printHashMap ()
    {
    	System.out.println("Map:" + HashMap);
    }
    
    public LinkedHashMap<K,V> getHashMap ()
    {
    	return this.HashMap;
    }

}