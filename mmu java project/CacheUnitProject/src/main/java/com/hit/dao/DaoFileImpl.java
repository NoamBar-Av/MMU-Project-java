package com.hit.dao;

import com.hit.dao.IDao;
import java.lang.String;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import com.hit.dm.DataModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

// This class is Responsible for updating the data in the 'datasorce.txt' file.
public class DaoFileImpl<T>extends java.lang.Object implements IDao<java.lang.Long,DataModel<T>> 
{
	File file;
	int maxValue;
	private LinkedHashMap<Long, DataModel<T>> hashMap = new LinkedHashMap <Long, DataModel<T>>();
	
	public DaoFileImpl(String filePath,int capacity)
	{
		this.maxValue=capacity;
		try {
			this.file = new File(new File(".").getCanonicalPath()+filePath);
		} catch (IOException e) {
			e.printStackTrace();
		};
		System.out.println(filePath);
	}

	public DaoFileImpl(String filePath)
	{
		this.maxValue=1000;
		System.out.println(filePath);
		try {
			this.file = new File(new File(".").getCanonicalPath()+filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(DataModel<T> t) throws IOException 
	{
		if (this.maxValue >= hashMap.size())
		{
			hashMap.put(t.getDataModelId(), t);  
			try {
				saveFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(DataModel<T> t) throws IllegalArgumentException
	{
		if(hashMap.containsKey( t.getDataModelId()))
		{
			
			hashMap.put(t.getDataModelId(), t);  // We are deleting only the content. This id is still in the file.
			
			try {
				saveFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public DataModel<T> find(Long id) throws IllegalArgumentException
	{
		return hashMap.get(id);	
	}
	
	public void saveFile() throws IOException 
	{
		FileWriter fstream;
	    BufferedWriter out;
	    fstream = new FileWriter(this.file);
	    out = new BufferedWriter(fstream);
	    Iterator<Entry<Long, DataModel<T>>> it = hashMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<Long, DataModel<T>> pairs = it.next();
	        //out.write(pairs.getValue() + "\n");
	        out.write(pairs + "\n");
	    }
	    out.close();
	}
}
