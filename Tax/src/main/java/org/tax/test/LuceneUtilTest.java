package org.tax.test;

import java.util.List;

import org.junit.Test;
import org.tax.model.TaxQuestion;
import org.tax.util.LuceneUtil;

public class LuceneUtilTest {
	@Test
	public void testCreateIndex() {
		// 构造测试的question对象
		TaxQuestion q1 = new TaxQuestion();
		q1.setId(1);
		q1.setTitle("aaa_lucene");
		q1.setContent("i love lucene");
		q1.setType("1;2;3;512;1212");
		LuceneUtil.creatIndex(q1);
		TaxQuestion q2 = new TaxQuestion();
		q2.setId(2);
		q2.setTitle("bbb_lucene");
		q2.setContent("i hate lucene");
		q2.setType("1;2;3;4;5");
		LuceneUtil.creatIndex(q2);
		TaxQuestion q3 = new TaxQuestion();
		q3.setId(3);
		q3.setTitle("ccc_lucene");
		q3.setContent("");
		q3.setType("1;2;3;12;22;26");
		LuceneUtil.creatIndex(q3);
		TaxQuestion q4 = new TaxQuestion();
		q4.setId(4);
		q4.setTitle("ddd_lucene");
		q4.setContent("");
		q4.setType("121212;222222");
		LuceneUtil.creatIndex(q4);
		TaxQuestion q5 = new TaxQuestion();
		q5.setId(5);
		q5.setTitle("eee_lucenee");
		q5.setContent("");
		q5.setType("11;12;26");
		LuceneUtil.creatIndex(q5);
		TaxQuestion q6 = new TaxQuestion();
		q6.setId(6);
		q6.setTitle("");
		q6.setContent("fff_lucenee");
		q6.setType("31;12221");
		LuceneUtil.creatIndex(q6);
	}

	@Test
	public void testSearch() {
		List<TaxQuestion> questionList=null;
		try {
			/**基本调用*/
			questionList = LuceneUtil.search("lucene", "3;26", 100);
			//questionList = LuceneUtil.search("lucenee", "26", 100);
			//questionList = LuceneUtil.search("lucenee", null, 100);
			/**keyword为null 或者为 全空格 或者是 空串 返回null什么都没有查到返回一个空的列表*/
			//questionList = LuceneUtil.search("", "3;26", 100);
			//questionList = LuceneUtil.search("  ", "3;26", 100);
			//questionList = LuceneUtil.search(null, "3;26", 100);
			/**keyword中全是stopword也是啥都没查到*/
			//questionList = LuceneUtil.search(";,##", "3;26", 100);
			for (TaxQuestion question : questionList) {
				System.out.println("**********");
				System.out.println("id: "+question.getId());
				System.out.println("title: "+question.getTitle());
				System.out.println("content: "+question.getContent());
				System.out.println("type: "+question.getType());
				System.out.println("**********");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearch2() {
		List<TaxQuestion> questionList=null;
		try {
			/**基本调用*/
			questionList = LuceneUtil.search("lucene", "3;26", 1, 2);
			//questionList = LuceneUtil.search("lucene", "3;26", 2, 2);
			//questionList = LuceneUtil.search("lucene", "3;26", 1, 100);//pageIdx合法 pageSize过大时没有问题的
			//questionList = LuceneUtil.search("lucene", "3;26", 2, 2);//pageIdx不合法就有问题了
			//questionList = LuceneUtil.search("lucenee", "26", 1, 2);
			//questionList = LuceneUtil.search("lucenee", null, 1, 2);
			/**keyword为null 或者为 全空格 或者是 空串 返回null什么都没有查到返回一个空的列表*/
			//questionList = LuceneUtil.search("", "3;26", 1, 2);
			//questionList = LuceneUtil.search("  ", "3;26", 1, 2);
			//questionList = LuceneUtil.search(null, "3;26", 1, 2);
			/**keyword中全是stopword也是啥都没查到*/
			//questionList = LuceneUtil.search(";,##", "3;26", 1, 2);
			for (TaxQuestion question : questionList) {
				System.out.println("**********");
				System.out.println("id: "+question.getId());
				System.out.println("title: "+question.getTitle());
				System.out.println("content: "+question.getContent());
				System.out.println("type: "+question.getType());
				System.out.println("**********");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteIndex(){
		TaxQuestion q1 = new TaxQuestion();
		q1.setId(1);
		q1.setTitle("aaa_lucene");
		q1.setContent("i love lucene");
		q1.setType("1;2;3;512;1212");
		LuceneUtil.deleteIndex(q1);
		TaxQuestion q6 = new TaxQuestion();
		q6.setId(6);
		q6.setTitle("");
		q6.setContent("fff_lucenee");
		q6.setType("31;12221");
		LuceneUtil.deleteIndex(q6);
		/**用search来测试一下*/
		List<TaxQuestion> questionList=null;
		try {
			//questionList = LuceneUtil.search("lucene", "3;26", 100);
			questionList = LuceneUtil.search("lucene", "", 100);
			for (TaxQuestion question : questionList) {
				System.out.println("**********");
				System.out.println("id: "+question.getId());
				System.out.println("title: "+question.getTitle());
				System.out.println("content: "+question.getContent());
				System.out.println("type: "+question.getType());
				System.out.println("**********");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateIndex(){
		TaxQuestion q1 = new TaxQuestion();
		q1.setId(1);
		q1.setTitle("");
		q1.setContent("我是一个在中国的大学生，来自于山东大学");
		q1.setType("1;2;3;512;1212");
		LuceneUtil.updateIndex(q1);
//		TaxQuestion q6 = new TaxQuestion();
//		q6.setId("6");
//		q6.setTitle("new_update_title");
//		q6.setContent("fff_lucenee");
//		q6.setType("31;12221");
//		LuceneUtil.updateIndex(q6);
		/**用search来测试一下*/
		List<TaxQuestion> questionList=null;
		try {
			//questionList = LuceneUtil.search("lucene", "3;26", 100);
			questionList = LuceneUtil.search("中国", "3", 100);
			for (TaxQuestion question : questionList) {
				System.out.println("**********");
				System.out.println("id: "+question.getId());
				System.out.println("title: "+question.getTitle());
				System.out.println("content: "+question.getContent());
				System.out.println("type: "+question.getType());
				System.out.println("**********");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
