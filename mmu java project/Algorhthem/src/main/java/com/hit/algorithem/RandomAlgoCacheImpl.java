package com.hit.algorithem;

import java.util.HashMap;

public class RandomAlgoCacheImpl<K,V>
extends AbstractAlgoCache<K,V>
{
	private HashMap<K, V> HashMap;
	
    public RandomAlgoCacheImpl(int capacity) {
        super(capacity);
        this.HashMap = new HashMap<>();
    }

    @Override
    public V getElement(K key) {
    	if (key==null)
			return null;
    	else
    		return this.HashMap.get(key);
    }
    
    public K getRandonKey()
    {
    	int randomNum = (int) (Math.random()*this.get_capacity());
    	int i=0 ;
    	for(HashMap.Entry<K, V> entry : this.HashMap.entrySet()) 
		{
		   if (i==randomNum)
			   return entry.getKey();
		   else i++;
		}
    	return null;
    }
    
    @Override
    public V putElement(K key, V value) {
    	if (this.HashMap.containsKey(key))
    		return null;
    	else if((this.HashMap.size() == this.get_capacity()))
		{ 
			removeElement(this.getRandonKey());
			HashMap.put(key, value);
			return value;  		
		}
		else {			
			HashMap.put(key, value); 
			return null;
		}
	}

	@Override
    public void removeElement(K key) 
    {
        this.HashMap.remove(key);
    }

    public void printHashMap()
    {
    	System.out.println("Map:" + HashMap);
    }

}
