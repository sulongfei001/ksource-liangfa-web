package com.ksource.common.lucene;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.util.ArrayUtil;
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
public class LuceneFactoryForCase {
    public static final Version LUCENE_VERSION = Version.LUCENE_33;
    public static String SESSION_QUERY_FLAG = LuceneFactoryForCase.class.getName() +
        "query_flag";
    public static final String HIGHLIGH_FLAG_START = "<em>";
    public static final String HIGHLIGH_FLAG_END = "</em>";
    public static final String INDEX_FILE_PATH="/caseBasic";
    public static final Analyzer analyzer;
    public static final String INDEX_POSTION_ALL = "all";
    
    public static final String ID_FIELD = "caseId";
    public static final String[] HIGHLIGH_ARRAY = new String[]{"caseNo","caseName","caseDetailName"};
    //需要进行分词的字段
    public static final String[] ANALYSE_ARRAY = new String[]{"caseNo","caseName","caseDetailName","anfaAddress","xianyirenName","companyName","goodsInvolved","identifyOrg","identifyPerson","identifyResult"};
    
    public static String[] FILTER_CRITERIA = new String[]{"address","shijian","wupin","jianding","danwei","dangshiren"};
    //需要进行时间范围索引的字段
    public static final String[] DATE_TERM_FIELD = new String[]{"anfaTime"};
    //需要进行数值范围索引的字段
    public static final String[] NUMBER_TERM_FIELD = new String[]{"amountInvolved"};
    
    static String indexFilePath = "";
    static{
		Properties properties = new Properties();
		LuceneFactoryForCase factoryForCase = new LuceneFactoryForCase();
		InputStream inputStream = factoryForCase.getClass().getResourceAsStream("/system_config.properties");
		try {
			properties.load(inputStream);
			indexFilePath = properties.getProperty("indexFilePath");
		} catch (IOException e) {
			indexFilePath = "";
		}
    }
    
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
        HttpSession session,String[] highlighArray,String flag,String queryType,boolean isAndCondition) throws ParseException {
        BooleanQuery bq = new BooleanQuery();
        BooleanQuery highlighbq = new BooleanQuery();
        List<String> list = Arrays.asList(highlighArray);
        Query q = null;
        if(map.isEmpty()){
        	removeSessionQuery(session, queryType);
        	return new WildcardQuery(new Term(flag,"*"));
        }
        for (Entry<String, Object> entry : map.entrySet()) {
        	//时间范围
        	if(entry.getValue() instanceof Date && Arrays.asList(DATE_TERM_FIELD).contains(entry.getKey())){
        		if(map.get("startAnfaTime") != null && map.get("endAnfaTime") != null){
            		Term beginData = new Term("anfaTime",DateTools.dateToString((Date) map.get("startAnfaTime"),DateTools.Resolution.DAY));
            		Term endDate = new Term("anfaTime", DateTools.dateToString((Date) map.get("endAnfaTime"),DateTools.Resolution.DAY));
            		TermRangeQuery termRangeQuery = new TermRangeQuery("anfaTime",beginData.text(), endDate.text(), false, true);
            		bq.add(termRangeQuery, BooleanClause.Occur.MUST);
        		}
        		//数值范围
        	}else if(entry.getValue() instanceof Double && Arrays.asList(NUMBER_TERM_FIELD).contains(entry.getKey())){
        		if(map.get("minAmountInvolved") != null && map.get("maxAmountInvolved") != null){
            		Double min = Double.valueOf(map.get("minAmountInvolved").toString());
            		Double max = Double.valueOf(map.get("maxAmountInvolved").toString());
                    NumericRangeQuery<Double> numericRangeQuery = NumericRangeQuery.newDoubleRange("price", min, max, true, true);  
                    bq.add(numericRangeQuery, BooleanClause.Occur.MUST);
        		}
        	}else{
        		q = createParseQuery(entry.getKey(),entry);
                if(isAndCondition){
               	 bq.add(q, BooleanClause.Occur.MUST);
                }else{
               	 bq.add(q, BooleanClause.Occur.SHOULD);
                }
           	if (list.contains(entry.getKey())) {
           		 if(isAndCondition){
           			 highlighbq.add(q, BooleanClause.Occur.MUST);
           		 }else{
           			 highlighbq.add(q, BooleanClause.Occur.SHOULD);
           		 }
               }
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
    private static Query createParseQuery(String field,Entry<String, Object> entry){
         try {
        	//多字段检索
			return IKQueryParser.parse(field, entry.getValue().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    public static IndexWriter getIndexWriter(String indexPosition) {
		IndexWriter writer=null;
		try{
		Directory dir = getLuceneDirectory(indexPosition);
	      IndexWriterConfig iwc = new IndexWriterConfig(LUCENE_VERSION,getAnalyzerInstance());
	      writer = new IndexWriter(dir, iwc);
		}catch (IOException e) {
		      System.out.println(" caught a " + e.getClass() +
		    	       "\n with message: " + e.getMessage());
	}
		return writer;
	}
	public static IndexSearcher getIndexSearcher(String indexPosition) {
		IndexSearcher searcher=null;
		try{
		Directory dir = getLuceneDirectory(indexPosition);
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
	public static Directory getLuceneDirectory(String indexPosition){
		String realpath = getRealPath(indexPosition);
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

	private static String getRealPath(String indexPosition) {
		String separator = "/";
    	String separatorTwo ="\\\\";
    	String savePath = INDEX_FILE_PATH;
    	String realpath = SystemContext.getRealPath();
    	if(StringUtils.isNotBlank(indexFilePath)){
    		realpath = indexFilePath;
    	}
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
    	if(StringUtils.isNotBlank(indexPosition)){
    		realpath += indexPosition+"/";
    	}
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
	public static void deleteIndexDir(String indexPosition){
		FileUtil.deleteFile(new File(getRealPath(indexPosition)));
	}
}
