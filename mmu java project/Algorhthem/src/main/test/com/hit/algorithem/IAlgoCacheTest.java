package com.hit.algorithem;


import org.junit.jupiter.api.Test;

public class IAlgoCacheTest extends java.lang.Object
{
	public IAlgoCacheTest() {};
	
	@Test
	public void LRUTest() 
	{
		int[] Array = {2,3,4,2,1,3,7,5,4,3};
		LRUAlgoCacheImpl<Integer, String> LRUTest = new LRUAlgoCacheImpl<>(3);
		System.out.println("LRU Test Here:");
		for (int i=0; i< Array.length;i++ )
		{
			LRUTest.putElement( Array[i], Integer.toString(Array[i]+1) );
			LRUTest.printHashMap();
		}
		System.out.println("\n");
		
	}
	
	@Test
	public void NRUTest() 
	{
		int[] Array = {2,3,4,2,1,3,7,5,4,3};
		NRUAlgoCacheImpl<Integer, String> NRUTest = new NRUAlgoCacheImpl<>(3);
		System.out.println("NRU Test Here:");
		for (int i=0; i< Array.length;i++ )
		{
			NRUTest.putElement( Array[i], Integer.toString(Array[i]+1) );
			NRUTest.printHashMap();
		} 
		System.out.println("\n");
	}
	
	@Test
	public void RandomTest() 
	{
		int[] Array = {2,1,3,4,5,6,7,8,5,2};
		RandomAlgoCacheImpl<Integer, String> RandomTest = new RandomAlgoCacheImpl<>(3);
		System.out.println("Random Test Here:");
		for (int i=0; i< Array.length;i++ )
		{
			RandomTest.putElement( Array[i], Integer.toString(Array[i]+1) );
			RandomTest.printHashMap();
		} 
		System.out.println("\n");
	}

}
