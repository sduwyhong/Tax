package org.tax.service;


/**
 * @author wyhong
 * @date 2018-7-7
 */
public interface SearchEngine<T> {

	void createIndex(T t);
	
	void delIndex(String field,String value);
	
	void updateIndex(String field,String value,T t);
	
	int[] search(String field,String keyword,int num);
	
	int[] search(String[] fields,String[] keywords,int num);
	
}
