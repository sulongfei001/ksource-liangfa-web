package com.ksource.common.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.springframework.stereotype.Service;
import org.springframework.util.MethodInvoker;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsonMapper;
import com.ksource.syscontext.PaginationContext;


/**
 * 此类为 lucene 索引工具类
 * 此类只是对索引进行增，删，改，查操作，不包括Lucene架构的配置及索引文件配置。有关配置的信息在LuceneFactory类。
 * 一开始就想把这个类改写成与数据源类型无关的通用类。
 * 但时间有限，修改搁浅，悲叹啊。
 * @author zxl :)
 * @version 1.0
 * date   2011-9-7
 * time   下午05:40:36
 */
@Service
public class LuceneUtil {
	private static JsonMapper binder = JsonMapper.buildNonNullMapper();
    //日志
    private static final Logger log = LogManager.getLogger(LuceneUtil.class);

    /**
     * 创建索引。<br/>
     * queryLayInfos是一个JavaBean对象集合。它包含了将被索引的数据。<br/>
     * 而analyzedField参数是一个字符串数组。里面包含着JavaBean对象的个别字段名，这些字段的值将会被Lucene分析器分析、切词。
     * @param <T>
     * @param queryLayInfos　索引源(将要被索引的数据)集合
     * @param analyzedField　
     * @return
     */
    public static <T> ServiceResponse createIndex(List<T> queryLayInfos,
        String[] analyzedField) {
        ServiceResponse res = new ServiceResponse(true, "创建索引成功");

        if (log.isDebugEnabled()) {
            log.debug("开始创建索引...");
        }
        //创建之前先删除索引
        LuceneFactory.deleteIndexDir();
        if (log.isDebugEnabled()) {
            log.debug("删除旧索引成功...");
        }
        IndexWriter writer = LuceneFactory.getIndexWriter();
        try {
            int count = 0;

            if ((queryLayInfos != null) && !queryLayInfos.isEmpty()) {
                for (T layInfo : queryLayInfos) {
                    writer.addDocument(beanToDoc(layInfo, analyzedField));
                    count++;
                }
            }
            res.setMsg(String.valueOf(count));

            if (log.isDebugEnabled()) {
                log.debug("索引创建成功,共有条" + count + "记录被创建索引");
            }
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                "\n with message: " + e.getMessage());
            res.setingError("索引创建失败\n"+e.getMessage());
        }finally{
        	closeWriter(writer);
        }
        return res;
    }

    /**
     * 把JavaBean转换成Lucene能识别的Document对象。
     * analyzedField参数是一个字符串数组。里面包含着JavaBean对象的个别字段名，这些字段的值将会被Lucene分析器分析、切词。
     * @param <T>
     * @param bean　　JavaBean对象
     * @param analyzedField   JavaBean的部分字段。
     * @return
     */
    private static <T> Document beanToDoc(T bean, String[] analyzedField) {
        PaginationContext<T> con = new PaginationContext<T>();
        Map<String, Object> map = con.getConditionMap(bean);
        Document doc = new Document();
        List<String> list = Arrays.asList(analyzedField);

        for (Entry<String, Object> entry : map.entrySet()) {
            Index a = Field.Index.NOT_ANALYZED;

            if (list.contains(entry.getKey())) {
                a = Field.Index.ANALYZED;
            }

            String str = null;

            if (entry.getValue() == null) {
                str = "null";
            }

            if (entry.getValue() instanceof Date) {
                str = String.valueOf(((Date) entry.getValue()).getTime());
            } else {
                str = entry.getValue().toString();
            }

            doc.add(new Field(entry.getKey(), str, Field.Store.YES, a));
        }

        return doc;
    }

    /**
     * 把Lucene的Document对象转换成JavaBean对象。
     * 参数session和queryType的作用就是为了查找保存在session中的Query对象,以显示要高亮的本文。
     * 这个Query对象是在查询索引时保存于session中的，如果没有Query对象，则没有文本高亮。
     * 代码类似于：
     * <pre>(Query)session.getAttribute(queryType)</pre>
     * {@link LuceneUtil#queryIndex(Object, String, String[], String, String, HttpSession)}
     * @param <T> 
     * @param cl JavaBean对象类型
     * @param doc
     * @param highlightArray JavaBean中需要高亮的字段。
     * @param session 
     * @param queryType
     * @return
     */
    private static <T> T docToBean(Class<T> cl, Document doc,
        String[] highlightArray, HttpSession session, String queryType) {
        T domain = null;

        try {
            domain = cl.newInstance();

            List<String> list = null;

            if (highlightArray != null) {
                list = Arrays.asList(highlightArray);
            }

            java.lang.reflect.Field[] fields = domain.getClass()
                                                     .getDeclaredFields();

            for (java.lang.reflect.Field field : fields) {
                String fieldName = field.getName();
                String setMethodName = "set" +
                    Character.toUpperCase(fieldName.charAt(0)) +
                    fieldName.substring(1);
                String obj = doc.get(fieldName);

                if ((obj != null) && !"null".equals(doc.get(fieldName))) {
                    Object[] args;
                    Object obj1 = null;

                    if (field.getType() == Date.class) {
                        obj1 = new Date(Long.parseLong(doc.get(fieldName)));
                    } else if (field.getType() == Integer.class) {
                        obj1 = Integer.parseInt(doc.get(fieldName));
                    } else {
                        obj1 = doc.get(fieldName);
                    }

                    if ((obj1 != null) && (list != null) &&
                            list.contains(fieldName)) {
                        obj1 = addHighligh(fieldName, obj1.toString(),
                                LuceneFactory.getSessionQuery(session, queryType));
                    }

                    args = new Object[] { obj1 };

                    MethodInvoker method = new MethodInvoker();
                    method.setTargetObject(domain);
                    method.setTargetMethod(setMethodName);
                    method.setArguments(args);
                    method.prepare();
                    method.invoke();
                }
            }
        } catch (Exception e) {
        	 System.out.println(" caught a " + e.getClass() +
                     "\n with message: " + e.getMessage());
                 log.error("查询索引失败：" + e.getMessage());
        }

        return domain;
    }
    
    private static <T> T docHotlineToBean(Class<T> cl, Document doc,
    		String[] highlightArray, HttpSession session, String queryType) {
    	T domain = null;
    	
    	try {
    		domain = cl.newInstance();
    		
    		List<String> list = null;
    		
    		if (highlightArray != null) {
    			list = Arrays.asList(highlightArray);
    		}
    		
    		java.lang.reflect.Field[] fields = domain.getClass()
    				.getDeclaredFields();
    		
    		for (java.lang.reflect.Field field : fields) {
    			String fieldName = field.getName();
    			String setMethodName = "set" +
    					Character.toUpperCase(fieldName.charAt(0)) +
    					fieldName.substring(1);
    			String obj = doc.get(fieldName);
    			
    			if ((obj != null) && !"null".equals(doc.get(fieldName))) {
    				Object[] args;
    				Object obj1 = null;
    				
    				if (field.getType() == Date.class) {
    					obj1 = new Date(Long.parseLong(doc.get(fieldName)));
    				} else if (field.getType() == Integer.class) {
    					obj1 = Integer.parseInt(doc.get(fieldName));
    				} else {
    					obj1 = doc.get(fieldName);
    				}
    				
    				if ((obj1 != null) && (list != null) &&
    						list.contains(fieldName)) {
    					obj1 = addHighligh(fieldName, obj1.toString(),
    							LuceneFactoryForHotline.getSessionQuery(session, queryType));
    				}
    				
    				args = new Object[] { obj1 };
    				
    				MethodInvoker method = new MethodInvoker();
    				method.setTargetObject(domain);
    				method.setTargetMethod(setMethodName);
    				method.setArguments(args);
    				method.prepare();
    				method.invoke();
    			}
    		}
    	} catch (Exception e) {
    		System.out.println(" caught a " + e.getClass() +
    				"\n with message: " + e.getMessage());
    		log.error("查询索引失败：" + e.getMessage());
    	}
    	
    	return domain;
    }

    /**
     * 添加单记录索引。
     *
     * @param domain  数据源(它应该是一个JavaBean)
     * @param analyzedField 参数layInfo中需要被分析切词的字段。
     */
    public static <T> void addIndex(T domain, String[] analyzedField) {
        if (log.isDebugEnabled()) {
            log.debug("开始添加索引...");
        }

        IndexWriter writer = LuceneFactory.getIndexWriter();

        try {
            writer.addDocument(beanToDoc(domain, analyzedField));
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                "\n with message: " + e.getMessage());
            	log.error("添加索引失败：" + e.getMessage());
        }finally{
        	closeWriter(writer);
        }
        if (log.isDebugEnabled()) {
            log.debug("添加索引成功");
        }
    }
    
    /**
     * 添加单记录索引。
     *
     * @param domain  数据源(它应该是一个JavaBean)
     * @param analyzedField 参数layInfo中需要被分析切词的字段。
     */
    public static <T> void addHotlineIndex(T domain, String[] analyzedField) {
    	if (log.isDebugEnabled()) {
    		log.debug("开始添加索引...");
    	}
    	
    	IndexWriter writer = LuceneFactoryForHotline.getHotlineIndexWriter();
    	
    	try {
    		writer.addDocument(beanToDoc(domain, analyzedField));
    	} catch (IOException e) {
    		System.out.println(" caught a " + e.getClass() +
    				"\n with message: " + e.getMessage());
    		log.error("添加索引失败：" + e.getMessage());
    	}finally{
    		closeWriter(writer);
    	}
    	if (log.isDebugEnabled()) {
    		log.debug("添加索引成功");
    	}
    }

    /**
     * 通过标识符flag,更新索引。
     * <pre>method test</pre>
     * @param layInfo  需要更新记录。
     * @param flag    记录标识。
     * @param analyzedField  参数layInfo中需要被分析切词的字段。
     */
    public static<T> void updateIndex(T layInfo, String flag,
        String[] analyzedField) {
    	if (log.isDebugEnabled()) {
            log.debug("开始修改索引...");
        }
        IndexWriter writer = LuceneFactory.getIndexWriter();
        try {
            writer.updateDocument(new Term(flag,(String)binder.beanToMap(layInfo).get(flag)),
                beanToDoc(layInfo, analyzedField));
        } catch (Exception e) {
            System.out.println(" caught a " + e.getClass() +
                "\n with message: " + e.getMessage());
            log.error("修改索引失败：" + e.getMessage());
        }finally{
        	closeWriter(writer);
        }
        if (log.isDebugEnabled()) {
            log.debug("修改索引成功");
        }
    }

    /**
     * 通过标识删除索引。
     * @param id   标识值
     * @param flag  标识符
     */
    public static void deleteIndexById(String id, String flag) {
    	 if (log.isDebugEnabled()) {
             log.debug("开始删除索引...");
         }

        IndexWriter writer = LuceneFactory.getIndexWriter();

        try {
            writer.deleteDocuments(new Term(flag, id));
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                "\n with message: " + e.getMessage());
            log.error("删除索引失败：" + e.getMessage());
        }finally{
        	closeWriter(writer);
        }
        if (log.isDebugEnabled()) {
            log.debug("删除索引成功");
        }
    }

    /**
     * 通过条件查询索引。
     * @param domain 条件
     * @param page   页码
     * @param highlighArray 需要高亮的字段。
     * @param sortFlag   排序字段。它必须 1.被索引过。2.是整数类型。<br/>
     *                   一般来说是唯一标示。
     * @param queryType
     * @param session  HttpSession实例。
     * @return
     */
	public static<T> PaginationHelper<T> queryIndex(T domain,
        String page, String[] highlighArray, String sortFlag, String queryType,
        HttpSession session) {
		IndexSearcher searcher=null;
        try {
            PaginationContext<T> helper = new PaginationContext<T>();
            List<T> list = new ArrayList<T>();
            Map<String, Object> map = helper.getConditionMap(domain, page);
            
            
            if(map.containsKey("changeSearch")){
            	searcher = LuceneFactory.getIndexSearcher(map.get("changeSearch").toString());
            }else{
            	searcher = LuceneFactory.getIndexSearcher();
            }
            
            
            Integer start = (Integer) map.get("start");
            Integer end = (Integer) map.get("end");
            map.remove("start");
            map.remove("end");

            Query query = LuceneFactory.createQuery(map, session,
                    highlighArray, sortFlag, queryType);
            //1.如果无查询条件，根据唯一标示排序(只适用于部分数据源)。
            //2.如果有查询条件，则根据默认的得分排序
            Sort sort = new Sort();
            TopDocs docs ;
            if(map.isEmpty()){
            	 sort.setSort(new SortField(sortFlag, SortField.INT,true));
            	 docs =searcher.search(query, end, sort);
            }else{
            	 docs =searcher.search(query, end);
            }
            end = Math.min(docs.totalHits, end);

            ScoreDoc[] hits = docs.scoreDocs;

            for (int i = start; i < end; i++) {
                Document d = searcher.doc(hits[i].doc);
                list.add((T) docToBean(domain.getClass(), d, highlighArray, session,
                        queryType));
            }
            return helper.getPaginationList(docs.totalHits, list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询索引失败：" + e.getMessage());
            return null;
        }finally{
        	closeSearcher(searcher);
        }
    }
	
	public static<T> PaginationHelper<T> queryHotlineIndex(T domain,
			String page, String[] highlighArray, String sortFlag, String queryType,
			HttpSession session) {
		IndexSearcher searcher=null;
		try {
			PaginationContext<T> helper = new PaginationContext<T>();
			List<T> list = new ArrayList<T>();
			Map<String, Object> map = helper.getConditionMap(domain, page);
			searcher = LuceneFactoryForHotline.getIndexSearcher();
			Integer start = (Integer) map.get("start");
			Integer end = (Integer) map.get("end");
			map.remove("start");
			map.remove("end");
			
			Query query = LuceneFactoryForHotline.createQuery(map, session,
					highlighArray, sortFlag, queryType);
			//1.如果无查询条件，根据唯一标示排序(只适用于部分数据源)。
			//2.如果有查询条件，则根据默认的得分排序
			Sort sort = new Sort();
			TopDocs docs ;
			if(map.isEmpty()){
				sort.setSort(new SortField(sortFlag, SortField.INT,true));
				docs =searcher.search(query, end, sort);
			}else{
				docs =searcher.search(query, end);
			}
			end = Math.min(docs.totalHits, end);
			
			ScoreDoc[] hits = docs.scoreDocs;
			
			for (int i = start; i < end; i++) {
				Document d = searcher.doc(hits[i].doc);
				list.add((T) docHotlineToBean(domain.getClass(), d, highlighArray, session,
						queryType));
			}
			return helper.getPaginationList(docs.totalHits, list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询索引失败：" + e.getMessage());
			return null;
		}finally{
			closeSearcher(searcher);
		}
	}

    /**
     * method simple des.
     * method detail des(how).
     * <pre>method test</pre>
     * @param <T>
     * @param infoId
     * @param flag
     * @param cla
     * @param highlightArray
     * @param queryType
     * @param session
     * @return
     */
    public static <T> T detail(String infoId, String flag, Class<T> cla,
        String[] highlightArray, String queryType, HttpSession session) {
        IndexSearcher searcher = LuceneFactory.getIndexSearcher();
        TopScoreDocCollector topCollector = TopScoreDocCollector.create(100,
                false);

        try {
            searcher.search(new TermQuery(new Term(flag, infoId)), topCollector);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("命中：" + topCollector.getTotalHits());

        // 查询当页的记录     
        ScoreDoc[] hits = topCollector.topDocs().scoreDocs;
        Document d = null;

        try {
            d = searcher.doc(hits[0].doc);
        } catch (Exception e) {
        	System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
            log.error("查询索引失败：" + e.getMessage());
        }finally{
        	closeSearcher(searcher);
        }

        return docToBean(cla, d, highlightArray, session, queryType);
    }
    
    public static <T> T searchDetail(String infoId, String flag, Class<T> cla,
    		String[] highlightArray, String queryType, HttpSession session) {
    	IndexSearcher searcher = LuceneFactoryForHotline.getIndexSearcher();
    	TopScoreDocCollector topCollector = TopScoreDocCollector.create(100,
    			false);
    	
    	try {
    		searcher.search(new TermQuery(new Term(flag, infoId)), topCollector);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	System.out.println("命中：" + topCollector.getTotalHits());
    	
    	// 查询当页的记录     
    	ScoreDoc[] hits = topCollector.topDocs().scoreDocs;
    	Document d = null;
    	
    	try {
    		d = searcher.doc(hits[0].doc);
    	} catch (Exception e) {
    		System.out.println(" caught a " + e.getClass() +
    				"\n with message: " + e.getMessage());
    		log.error("查询索引失败：" + e.getMessage());
    	}finally{
    		closeSearcher(searcher);
    	}
    	
    	return docHotlineToBean(cla, d, highlightArray, session, queryType);
    }
    
   private static void closeSearcher(IndexSearcher searcher){
	   try {
		   if(searcher!=null)
			searcher.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   private static void closeWriter(IndexWriter writer){
	   try {
		   if(writer!=null)
		   writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
   }

	/** 对所命中文本添加标记,这个标记的样式位于common.css中.*/
    private static String addHighligh(String fieldName, String text, Query query) {
        if (text == null) {
            return null;
        }

        String highLightText = null;

        if ((query != null) && query.toString().contains(fieldName)) {
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(LuceneFactory.HIGHLIGH_FLAG_START,
                    LuceneFactory.HIGHLIGH_FLAG_END);
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
                    new QueryScorer(query));
            if(fieldName.equals("content")){
            	highlighter.setTextFragmenter(new SimpleFragmenter(text.length()));    
            }
            TokenStream tokenStream = LuceneFactory.getAnalyzerInstance()
                                                   .tokenStream(fieldName,
                    new StringReader(text));

            try {
                highLightText = highlighter.getBestFragment(tokenStream, text);
            }catch (Exception e) {
            	System.out.println(" caught a " + e.getClass() +
                        "\n with message: " + e.getMessage());
                log.error("添加命中文本高亮失败：" + e.getMessage());
            }
        }

        return (highLightText == null) ? text : highLightText;
    }

    public static void removeSessionQuery(HttpSession session, String queryType) {
        LuceneFactory.removeSessionQuery(session, queryType);
    }
}
