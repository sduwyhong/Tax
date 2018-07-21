package org.tax.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tax.factory.MapperFactory;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxQuestionExample;
import org.tax.model.TaxQuestionPro;

/**
 * @author wyhong
 * @date 2018-7-21
 */
public class SearchTest {

	MapperFactory mapperFactory;
	
	@Before
	public void setUp(){
		ApplicationContext context 
			= new ClassPathXmlApplicationContext("classpath:spring/applicationContext-db.xml","classpath:spring/applicationContext-service.xml");
		mapperFactory = context.getBean(MapperFactory.class);
	}
	
	@Test
	public void migrate(){
		TaxQuestionExample example = new TaxQuestionExample();
		List<TaxQuestion> questions = mapperFactory.getTaxQuestionMapper().selectByExample(example );
		for(TaxQuestion question : questions){
			if(question.getType().equals("-")) continue;
			splitProIdAndInsert(question);
		}
	}

	private void splitProIdAndInsert(TaxQuestion question) {
		String[] proArr = question.getType().split(";");
		int questionId = question.getId();
		for(String proId : proArr){
			TaxQuestionPro record = new TaxQuestionPro();
			record.setProId(Integer.parseInt(proId));
			record.setQuestionId(questionId);
			mapperFactory.getTaxQuestionProMapper().insert(record );
		}
		
	}
}
