package com.hit.memory;

import java.lang.Object;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.hit.algorithem.IAlgoCache;
import com.hit.algorithem.LRUAlgoCacheImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

// This class is Responsible for updating the data in the Cache .

public class CacheUnit<T> extends java.lang.Object
{
	private IAlgoCache<Long,DataModel<T>> algo;
	
	public CacheUnit(com.hit.algorithem.IAlgoCache<java.lang.Long ,DataModel<T>> algo)
	{
		this.algo=algo;
	}
	
	public DataModel<T>[] getDataModels(Long[] ids)
	{
		DataModel<T>[] dataModels=new DataModel[ids.length];
		for (int i = 0; i < ids.length; i++)
		{
			dataModels[i]=this.algo.getElement(ids[i]);		
		}
		return dataModels;  // Returns the DM that are saved in the Cache.
	}
	
	@SuppressWarnings("unchecked")
	public DataModel<T>[] putDataModels(DataModel<T>[] datamodels)
	{
		DataModel<T> dm;
		List<DataModel<T>> dmsl = new ArrayList<DataModel<T>>();
		for(DataModel<T> dm1:datamodels) {
			dm = algo.putElement(dm1.getDataModelId(),dm1); // 'dm' is the page that got out from the Cache  
			if(dm==null)
				dmsl.add(dm1);
			else {
				
				algo.removeElement(dm.getDataModelId());
				
				dm = algo.putElement(dm1.getDataModelId(),dm1); // 'dm' is the page that got out from the Cache  
				if(dm==null)
					dmsl.add(dm1);
			}
		}
		if(dmsl.size()>0) {
			DataModel<T>[] dms = (DataModel<T>[]) Array.newInstance(dmsl.get(0).getClass(), dmsl.size());
			dms = dmsl.toArray(dms);
			return dms; // Returns an array of the pages that got out from Cache
		}
		return null;
	}
	
	public void removeDataModels(Long[] ids)
	{
		for (int i=0; i < ids.length;i++)
			algo.removeElement(ids[i]);  // this DM is no longer in the Cache
	}
}
