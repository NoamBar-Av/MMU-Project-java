package com.hit.services;

import com.hit.dm.DataModel;

import java.io.IOException;

import com.hit.dao.DaoFileImpl;

public class CacheUnitController<T>
extends java.lang.Object {
	private CacheUnitService cacheService;
	
	public CacheUnitController()
	{
		cacheService = new CacheUnitService ();
	}
	public boolean update(DataModel<T>[] dataModels) throws IOException
	{
		 return cacheService.update (dataModels);
		
	}
	public boolean delete(DataModel<T>[] dataModels)
	{
		 return cacheService.delete (dataModels);
			
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels)
	{
		 return cacheService.get (dataModels);
	
	}
	public String getStatistics() {
		return cacheService.getStatistics();
	}
}
