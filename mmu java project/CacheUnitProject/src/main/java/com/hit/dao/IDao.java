package com.hit.dao;

import java.io.IOException;
import java.lang.IllegalArgumentException;

public interface IDao <ID extends java.io.Serializable ,T>
{
	public void save (T entity) throws IOException;
	public void delete (T entity) throws IllegalArgumentException;
	public T find (ID id) throws IllegalArgumentException;
}
