package com.hit.algorithem;

public abstract class AbstractAlgoCache<K,V> extends java.lang.Object
implements IAlgoCache<K,V> 
{
	private int _capacity;
	public AbstractAlgoCache(int capacity) 
	{
		this.set_capacity(capacity);
	}
	public int get_capacity() {
		return _capacity;
	}
	public void set_capacity(int _capacity) {
		this._capacity = _capacity;
	}
}
