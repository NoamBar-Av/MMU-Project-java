package com.hit.services;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.hit.algorithem.LRUAlgoCacheImpl;
import com.hit.algorithem.NRUAlgoCacheImpl;
import com.hit.algorithem.RandomAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T>
extends java.lang.Object {
	
	private CacheUnit<T> cacheUnit;
	private DaoFileImpl<T> dao;
	private int numAlgo;
	private int swapingCount;
	private int requestCount;
	private int DMCount;
	private String Algo;
	public static int CAPACITY = 5;
	public CacheUnitService()
	{
		dao = new DaoFileImpl<>("/src/main/resources/datasource.txt");		
		cacheUnit = new CacheUnit<> (new LRUAlgoCacheImpl<>(CAPACITY));
		swapingCount = 0;
		requestCount = 0;
		DMCount = 0;
	}
	@SuppressWarnings("unchecked")
	public boolean update(DataModel<T>[] dataModels) throws IOException
	{
		requestCount ++;
		DMCount+=dataModels.length;
		try {
			DataModel<T>[] returnFromCache = cacheUnit.putDataModels(dataModels);
			swapingCount += dataModels.length-CAPACITY;
			for(DataModel<T> dt:dataModels) {
				dao.save(dt);
			}
			return true;
		}
		catch(IOException e){
			return false;
		}
		
	}
	@SuppressWarnings("unchecked")
	public boolean delete(DataModel<T>[] dataModels) {
		requestCount++;
		Long[] MDids = new Long[dataModels.length];
		for (int i = 0; i < MDids.length; i++) {
			MDids[i] = dataModels[i].getDataModelId();
			dao.delete(dataModels[i]); // Here we are delete the DM's content from file
		}
		cacheUnit.removeDataModels(MDids); // Here we are delete the DM's from cache
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public DataModel<T>[] get(DataModel<T>[] dataModels)
	{
		requestCount ++;
		DMCount+=dataModels.length;
		Long[] ids = new Long[dataModels.length];
		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId ();
			System.out.print(ids[i]);
		}
        DataModel<T>[] DMFromCacheUnitOrDao = cacheUnit.getDataModels(ids); // This array is field with DM's from Cache - that the 'GET' json ask for
        		int countWithoutNulls= 0;
        for (int i = 0; i < DMFromCacheUnitOrDao.length; i++) {
        	System.out.print(DMFromCacheUnitOrDao[i]);
        	if (DMFromCacheUnitOrDao[i] == null) {  // If we didn't find the DM's in the Cache --> we will search in the dao file.
        		if(dao.find(ids[i]) != null) {
        			countWithoutNulls++; // for out new array without 'null's
        			DMFromCacheUnitOrDao[i] = (DataModel<T>) dao.find(ids[i]);	
        		}
        	} else {
        		countWithoutNulls++; // for out new array without 'null's
        	}
        }
        DataModel<T>[] arrayWithoutNulls = new DataModel[countWithoutNulls];
        int j = 0;
        for (int i=0; i< DMFromCacheUnitOrDao.length; i++) {
        	if(DMFromCacheUnitOrDao[i] != null) {
        		arrayWithoutNulls[j] = DMFromCacheUnitOrDao[i];  // Creating new array without 'null's
        		j++;
        	}
        }
        cacheUnit.putDataModels(arrayWithoutNulls);
        swapingCount += arrayWithoutNulls.length;
        return arrayWithoutNulls;
	}
	
	public String getStatistics() {
		return "Capacitiy = " + CAPACITY + "\n" + "Algorithem:" + "LRU" + "\n" + "Total number of requests:"
				+ requestCount + "\n" + "Total number of DataModels(GET,DELETE,UPDATE requsts):"
				+ DMCount + "\n" + "Total number of dataModels swaps (from Cache to Disk):" + swapingCount
				+ "\n";
	}
}



