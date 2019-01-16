package com.ksource.common.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.syscontext.SystemContext;


/**
 * 此类为生成Lucene中Analyzer,Query,IndexWriter,IndexSearcher,Directory对象的类。
 * 保存着索引文件路径及高亮标识。
 * @author zxl :)
 * @version 1.0
 * date   2011-9-13
 * time   下午02:24:09
 */
public class LuceneFactoryForCms{
    public static final Version LUCENE_VERSION = Version.LUCENE_33;
    public static String SESSION_QUERY_FLAG = LuceneFactory.class.getName() +
        "query_flag";
    public static final String HIGHLIGH_FLAG_START = "<em>";
    public static final String HIGHLIGH_FLAG_END = "</em>";
    public static final String INDEX_FILE_PATH="resources/index/webCms";
    public static final Analyzer analyzer;
    static {
        analyzer = new IKAnalyzer();
    }

    public static Analyzer getAnalyzerInstance() {
        return analyzer;
    }

    public static Query getSessionQuery(HttpSession session,String queryType) {
    	String sessionFlag =SESSION_QUERY_FLAG;
    	if(queryType!=null){
    		sessionFlag+=queryType;
    	}
        return (Query) session.getAttribute(sessionFlag);
    }

    public static void setSessionQuery(HttpSession session, Query sessionQuery,String queryType) {
    	String sessionFlag =SESSION_QUERY_FLAG;
    	if(queryType!=null){
    		sessionFlag+=queryType;
    	}
        session.setAttribute(sessionFlag, sessionQuery);
    }

    /**
     * 把map中的键,值解析成查询索引的条件.
     * <pre>method test</pre>
     * @param map
     * @param session
     * @param highlighArray
     * @param flag 
     * @return
     * @throws ParseException
     */
    public static Query createQuery(Map<String, Object> map,
        HttpSession session,String[] highlighArray,String flag,String queryType) throws ParseException {
        BooleanQuery bq = new BooleanQuery();
        BooleanQuery highlighbq = new BooleanQuery();
        List<String> list = Arrays.asList(highlighArray);
        Query q = null;
        if(map.isEmpty()){
        	removeSessionQuery(session, queryType);
        	return new WildcardQuery(new Term(flag,"*"));
        }
        for (Entry<String, Object> entry : map.entrySet()) {
          	 q = createParseQuery(entry);
             bq.add(q, BooleanClause.Occur.MUST);
        	if (list.contains(entry.getKey())) {
              highlighbq.add(q, BooleanClause.Occur.MUST);
            }
            setSessionQuery(session, highlighbq,queryType);
        }
       
        return bq;
    }

    /**
     * 用IKAnalyzer解析查询关键字
     * @param entry
     * @return
     */
    private static Query createParseQuery(Entry<String, Object> entry){
         try {
			return IKQueryParser.parse(entry.getKey(),entry.getValue().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    public static IndexWriter getIndexWriter() {
		IndexWriter writer=null;
		try{
		Directory dir = getLuceneDirectory();
	      IndexWriterConfig iwc = new IndexWriterConfig(LUCENE_VERSION,getAnalyzerInstance());
	      writer = new IndexWriter(dir, iwc);
		}catch (IOException e) {
		      System.out.println(" caught a " + e.getClass() +
		    	       "\n with message: " + e.getMessage());
	}
		return writer;
	}
	public static IndexSearcher getIndexSearcher() {
		IndexSearcher searcher=null;
		try{
		Directory dir = getLuceneDirectory();
	      searcher = new IndexSearcher(dir);
	      searcher.setSimilarity(new IKSimilarity());
		}catch (IOException e) {
		      System.out.println(" caught a " + e.getClass() +
		    	       "\n with message: " + e.getMessage());
		}
		if(searcher==null){
			throw new BusinessException("没有创建索引");
		}
		return searcher;
	}
	public static Directory getLuceneDirectory(){
		String realpath = getRealPath();
    	 File uploadDir = new File(realpath);
       //初始化文件夹属性(1.如果文件夹不存在,则创建2.如果文件夹存在但是只能读,则修改为可写)
        //检查目录
        if (!uploadDir.isDirectory()) {
            if (!uploadDir.exists()) {
            	uploadDir.mkdirs();
            }
        }
        //检查目录写权限
        if (!uploadDir.canWrite()) {
           uploadDir.setWritable(true);
        }
        try {
			return FSDirectory.open(uploadDir);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getRealPath() {
		String separator = "/";
    	String separatorTwo ="\\\\";
    	String savePath = INDEX_FILE_PATH;
    	String realpath = SystemContext.getRealPath();
    	String[]str = savePath.split(separator);
    	
    	if(str.length==1){
    		str=savePath.split(separatorTwo);
    	}
    	if(str.length==1){
    		throw new BusinessException("不支持的路径分隔符,请用\"//\"或\"\\\"分隔你的路径");
    	}
    	//拼接文件保存目录路径
    	String path="/";
    	for(int i=0;i<str.length;i++){
    		path+=str[i]+"/";
    	}
    	realpath += path;
		return realpath;
	}

	public static void removeSessionQuery(HttpSession session, String queryType) {
		String sessionFlag = SESSION_QUERY_FLAG;
		if(queryType!=null){
			sessionFlag+=queryType;
		}
		if (session.getAttribute(sessionFlag) != null) {
            session.removeAttribute(sessionFlag);
        }
	}
	public static void deleteIndexDir(){
		FileUtil.deleteFile(new File(getRealPath()));
	}
}
