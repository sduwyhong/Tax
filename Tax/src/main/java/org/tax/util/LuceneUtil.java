package org.tax.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.tax.model.TaxQuestion;
import org.wltea.analyzer.lucene.IKAnalyzer;

/** 有一个基本假设是，这里的question都是合法的实体 id不为空s */
public class LuceneUtil {
	// 存放索引库的位置
	private static String INDEX_LIB_PATH = "E:\\temp\\tax\\lucene\\index";
	//private static String INDEX_LIB_PATH = "D:\\temp\\tax\\lucene\\index_db";
	// 索引库关联的源数据的位置
	private static String SEARCH_SRC_PATH = "E:\\temp\\tax\\lucene\\searchsource";

	public static void initIndexLib() {
		// 测试时候根据db插入好的数据初始化索引库
		String TEST_INDEX_LIB_PATH = "E:\\temp\\tax\\lucene\\index_db";
		String URL = "jdbc:mysql://localhost:3306/tax";
		String USERNAME = "root";
		String PASSWORD = "root";
		Directory idxDir;
		try {
			idxDir = FSDirectory.open(new File(TEST_INDEX_LIB_PATH));
			Analyzer analyzer = new IKAnalyzer();
			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LATEST,
					analyzer);
			IndexWriter idxWriter = new IndexWriter(idxDir, iwConfig);
			//查询数据库所有问题记录
			Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = con.createStatement();
            String query = "select * from tax_question";
            System.out.println("query==="+query);
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next()) {
                Document doc = new Document();
                String id = ""+rs.getInt("id");
                System.out.println("id--------------->"+id);
                String title = rs.getString("title");
                System.out.println("title--------------->"+title);
                String content = rs.getString("content");
                System.out.println("content--------------->"+content);
                String type = rs.getString("type");
                doc.add(new StringField("questionId", id, Field.Store.YES));// no analyze
                doc.add(new TextField("questionTitle", title, Field.Store.YES));
                doc.add(new TextField("questionContent", content, Field.Store.YES)); 
                doc.add(new TextField("questionType", type, Field.Store.YES));
                idxWriter.addDocument(doc);
            }
            idxWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void creatIndex(TaxQuestion question) {
		// title content type字段建立索引
		try {
			Directory idxDir = FSDirectory.open(new File(INDEX_LIB_PATH));
			Analyzer analyzer = new IKAnalyzer();
			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LATEST,
					analyzer);
			IndexWriter idxWriter = new IndexWriter(idxDir, iwConfig);
			Document document = new Document();
			// 问题id
			Integer questionId = question.getId();
			Field questionIdField = new StringField("questionId",
					questionId.toString(), Store.YES);//no analyze
			// 问题标题
			String questionTitle = question.getTitle();
			Field questionTitleField = new TextField("questionTitle",
					questionTitle, Store.YES);
			// 问题内容
			String questionContent = question.getContent();
			Field questionContentField = new TextField("questionContent",
					questionContent, Store.YES);
			// 问题分类
			String questionType = question.getType();
			Field questionTypeField = new TextField("questionType",
					questionType, Store.YES);
			// 构建document
			document.add(questionIdField);
			document.add(questionTitleField);
			document.add(questionContentField);
			document.add(questionTypeField);
			// document加入的索引库
			idxWriter.addDocument(document);
			if (idxWriter != null)
				idxWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void deleteIndex(TaxQuestion question) {
		// 删除该question的doc
		try {
			Directory idxDir = FSDirectory.open(new File(INDEX_LIB_PATH));
			Analyzer analyzer = new IKAnalyzer();
			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LATEST,
					analyzer);
			IndexWriter idxWriter = new IndexWriter(idxDir, iwConfig);
			// 由于questionId唯一，只要根据id删除对应的doc即可
			idxWriter.deleteDocuments(new Term("questionId", question.getId()
					.toString()));
			if (idxWriter != null)
				idxWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateIndex(TaxQuestion question) {
		// 更新该question的doc
		try {
			Directory idxDir = FSDirectory.open(new File(INDEX_LIB_PATH));
			Analyzer analyzer = new IKAnalyzer();
			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LATEST,
					analyzer);
			IndexWriter idxWriter = new IndexWriter(idxDir, iwConfig);
			Document document = new Document();
			document.add(new TextField("questionId", question.getId()
					.toString(), Store.YES));
			document.add(new TextField("questionTitle", question.getTitle(),
					Store.YES));
			document.add(new TextField("questionContent",
					question.getContent(), Store.YES));
			document.add(new TextField("questionType", question.getType(),
					Store.YES));
			Term deleteTerm = new Term("questionId", question.getId()
					.toString());
			// //先根据id删除对应的question的doc
			// Term deleteTerm=new Term("questionId", question.getId());
			// idxWriter.deleteDocuments(deleteTerm);
			// //加入新的doc
			// idxWriter.addDocument(document);
			// 其实这个updateDocument其实也是封装了先删后加而已
			idxWriter.updateDocument(deleteTerm, document);
			if (idxWriter != null)
				idxWriter.commit();
			if (idxWriter != null)
				idxWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * search(String keyword, String type,int offset, int limit) 的一个特例 合并
	 * */
	public static List<TaxQuestion> search(String keyword, String type,
			int returnNum) throws Exception {
		if (returnNum <= 0)
			return new ArrayList();
		return search(keyword, type, 1, returnNum);
	}

	/** 调用之前必须确保pageIdx合法 否则调用时候返回垃圾数据异常即可 */
	public static List<TaxQuestion> search(String keyword, String type,
			int pageIdx, int pageSize) {
		// 根据关键词与种类搜索
		// 注意调用时默认传入的type是形式为只有数字(数字应该保持唯一，且递增书写)与';'分割的形式 如: "1;2;4;5"
		// 或者type.equals("")或者type==null
		// 若这里keyword为null不应该使用这个方法查****************************************************
		if (keyword == null || keyword.equals("") || keyword.split("\\s+").length == 0) return new ArrayList<TaxQuestion>();
		// 为了使后面处理的type肯定不是null 
		if (type == null) type = "";
		try {
			Directory idxDir = FSDirectory.open(new File(INDEX_LIB_PATH));
			IndexReader idxReader = DirectoryReader.open(idxDir);
			IndexSearcher idxSearcher = new IndexSearcher(idxReader);
			// 对问题的title content中含有keyword先查询出来
			String[] queries = { keyword, keyword };
			String[] fields = { "questionTitle", "questionContent" };
			BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD };
			Query query = MultiFieldQueryParser.parse(queries, fields, clauses,new IKAnalyzer());
			TopDocs topDocs = null;
			if (type.equals("")) {
				ScoreDoc lastScoreDoc = getLastScoreDoc(pageIdx, pageSize,query, null, idxSearcher);
				topDocs = idxSearcher.searchAfter(lastScoreDoc, query, pageSize);
			} else {
				String negativeWord = type;// 比如筛选负面或者正面词的新闻就是这么用
				QueryParser qp = new QueryParser("questionType", new IKAnalyzer());
				try {
					Query kwQuery = qp.parse(negativeWord);
					QueryWrapperFilter qwFilter = new QueryWrapperFilter(kwQuery);
					// 通过最后一个元素去搜索下一页的元素
					ScoreDoc lastScoreDoc = getLastScoreDoc(pageIdx, pageSize, query, qwFilter, idxSearcher);
					topDocs = idxSearcher.searchAfter(lastScoreDoc, query, qwFilter, pageSize);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 根据搜索结果包装好返回的问题列表
			List<TaxQuestion> questionList = new ArrayList<TaxQuestion>();
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				int doc = scoreDoc.doc;
				Document document = idxSearcher.doc(doc);
				TaxQuestion question = new TaxQuestion();
				// 设置问题id
				String questionIdStr = document.get("questionId");
				Integer questionId = Integer.parseInt(questionIdStr);
				question.setId(questionId);
				// 设置问题标题
				String questionTitle = document.get("questionTitle");
				question.setTitle(questionTitle);
				// 设置问题内容
				String questionContent = document.get("questionContent");
				question.setContent(questionContent);
				// 设置问题分类
				String questionType = document.get("questionType");
				question.setType(questionType);
				// 添加到队列
				questionList.add(question);
			}
			if (idxReader != null)
				idxReader.close();
			return questionList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 不返回null是因为防止空指针异常
		return new ArrayList<TaxQuestion>();
	}

	/**
	 * 根据页码和分页大小获取上一次的最后一个scoredocs
	 * 
	 * @param pageIdx
	 * @param pageSize
	 * @param query
	 * @param filter
	 * @param idxSearcher
	 * @return
	 * @throws IOException
	 */
	private static ScoreDoc getLastScoreDoc(int pageIdx, int pageSize,
			Query query, Filter filter, IndexSearcher idxSearcher)
			throws IOException {
		if (pageIdx == 1)
			return null;// 如果是第一页就返回空
		int num = pageSize * (pageIdx - 1);// 获取上一页的最后数量
		TopDocs tds = null;
		if (filter == null) {
			tds = idxSearcher.search(query, num);
		} else {
			tds = idxSearcher.search(query, filter, num);
		}
		return tds.scoreDocs[num - 1];
	}

}
