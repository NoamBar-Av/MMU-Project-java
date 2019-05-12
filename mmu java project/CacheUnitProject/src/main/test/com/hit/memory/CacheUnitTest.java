package com.hit.memory;

import java.io.IOException;
import com.hit.memory.CacheUnit;
import org.junit.jupiter.api.Test;
import com.hit.algorithem.IAlgoCache;
import com.hit.algorithem.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
public class CacheUnitTest<T> {

	 IDao<Long, DataModel<String>> dao = new DaoFileImpl<>("/src/main/resources/datasource.txt",100);
	 IAlgoCache<Long, DataModel<String>> algo= new LRUAlgoCacheImpl<>(3);
	 CacheUnit<String> cacheUnit = new CacheUnit<String>(algo);
	
	 @Test
	 public void DaoTest() throws IOException
	 {
		 Long [] ids = {(long)2,(long)3,(long)4,(long)9,(long)1,(long)8,(long)7,(long)5,(long)0,(long)6};
		 DataModel<String>[] dataModels;
		 dataModels = cacheUnit.putDataModels(cacheUnit.getDataModels(ids));
		 for (int i=0; i<ids.length; i++)
		 {
			 dao.save(dataModels[i]);
			 DataModel dataModelFind= dao.find(ids[i]);
			 System.out.println(dataModelFind.getContent());
			 System.out.println(dataModelFind.getDataModelId());
			// dao.delete(dataModels[i]);
		 }
		 ((DaoFileImpl<String>) dao).saveFile();
	 }
}
