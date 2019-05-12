package com.hit.algorithem;

import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashMap;

public class NRUAlgoCacheImpl<K,V>extends AbstractAlgoCache<K,V> 
{ 
	private HashMap<K, Integer> referenceMap;
	private LinkedHashMap<K, V> HashMap;
	
    public NRUAlgoCacheImpl(int capacity) {
        super(capacity);
        this.HashMap = new LinkedHashMap<K,V>();
        this.referenceMap = new HashMap<K,Integer>();
    }

	@Override
	public V getElement(K key)
	{
    	if (key==null)
			return null;
    	else
    		return this.HashMap.get(key);
	}
	
	public Integer getInteger(K key)
	{
		 return this.referenceMap.get(key);
	}
	@Override
	public V putElement(K key, V value) 
	{
		if(this.HashMap.containsKey(key))
		{
			this.referenceMap.replace(key,getInteger(key)+1);
		}
		else if (this.HashMap.size()== this.get_capacity())	
		{
			Integer min= Collections.min(referenceMap.values());
			K keyToRemove=key;
	    	for(HashMap.Entry<K, Integer> entry : referenceMap.entrySet()) 
			{
			   if (entry.getValue()==min)
			   {
				   keyToRemove=entry.getKey();
			   	break;
			   }
			}
			removeElement(keyToRemove);
			putElement( key,  value);	
		}
		else
		{
			int bit=0;
			this.HashMap.put(key,value);
			this.referenceMap.put(key,bit);
			this.referenceMap.replace(key,bit+1);
		}
		return null;
	}

	@Override
    public void removeElement(K key) 
    {
        this.HashMap.remove(key);
        this.referenceMap.remove(key);
    }

    public void printHashMap ()
    {
    	System.out.println("Map:" + HashMap);
    	System.out.println("referenceMap:" + referenceMap);

    }
}
