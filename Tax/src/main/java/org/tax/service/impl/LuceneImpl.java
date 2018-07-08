package org.tax.service.impl;

import org.tax.service.SearchEngine;

/**
 * @author wyhong
 * @date 2018-7-7
 */
public class LuceneImpl<TaxQuestion> implements SearchEngine<TaxQuestion> {

	@Override
	public void createIndex(TaxQuestion question) {
	}

	@Override
	public void delIndex(String field, String value) {
	}

	@Override
	public void updateIndex(String field, String value, TaxQuestion question) {
	}

	@Override
	public int[] search(String field, String keyword, int num) {
		
		return null;
	}

	@Override
	public int[] search(String[] fields, String[] keywords, int num) {
		
		return null;
	}

}
