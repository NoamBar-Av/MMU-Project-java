package com.hit.dm;	

public class DataModel<T> extends java.lang.Object implements java.io.Serializable
{
	
	private long id;
	private T content;
	
	
	public DataModel (java.lang.Long id, T content)
	{
		setContent( content);
		this.id=id;
	}
	
	 @Override
	public int hashCode()
	{
		return this.hashCode();
	}
	 @Override
	public boolean equals(java.lang.Object obj)
	{
		DataModel<T> a= (DataModel)obj;
		return ((this.content==a.content)&&(this.id==a.id));
			
	}
	 @Override
	 public java.lang.String toString()
	 {
		 return "DataModel [id=" + id + ", content=" + content + "]";
	 }
	 
	 public java.lang.Long getDataModelId ()
	 {
		 return this.id;
	 }
	 
	 public T getContent () 
	 {
		 return content;
	 }
	 
	 public void setContent (T content)
	 {
		 this.content=content;
	 }
	 
	 
}
