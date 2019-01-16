package com.ksource.common.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class CosineSimilarityUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CosineSimilarityUtil.class);
	//阈值
	public static double YUZHI = 0.985 ;

	/**
	 * 
	 * 利用余弦定理 判断2段文本的相似度
	 *
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean CompareText(String str1,String str2){
		if(StringUtils.isBlank(str1)||StringUtils.isBlank(str2)){
			return false;
		}
		Vector<String> vector1 = participle(str1);
		Vector<String> vector2 = participle(str2);
		try {
			double similarity = getSimilarity(vector1,vector2);
			logger.debug("相似度值："+similarity+"-阈值:"+YUZHI);
			return similarity > YUZHI;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Vector<String> participle( String str ) {
		
		Vector<String> str1 = new Vector<String>() ;//对输入进行分词
		
		try {
			
		    StringReader reader = new StringReader( str ); 
		    IKSegmentation ik = new IKSegmentation(reader,true);//当为true时，分词器进行最大词长切分 
		    Lexeme lexeme = null ;			
			
		    while( ( lexeme = ik.next() ) != null ) {
			str1.add( lexeme.getLexemeText() ); 
		    }			
			
		    if( str1.size() == 0 ) {
		    	return null ;
		    }
		    
	 	    //分词后
		    System.out.println( "str分词后：" + str1 );
		    
		} catch ( IOException e1 ) {
			System.out.println();
		}
		return str1;
	}	
	
	public static double getSimilarity(Vector<String> T1, Vector<String> T2) throws Exception {
		int size = 0 , size2 = 0 ;
	    if ( T1 != null && ( size = T1.size() ) > 0 && T2 != null && ( size2 = T2.size() ) > 0 ) {
	        
	    	Map<String, double[]> T = new HashMap<String, double[]>();
	        
	        //T1和T2的并集T
	    	String index = null ;
	        for ( int i = 0 ; i < size ; i++ ) {
	        	index = T1.get(i) ;
	            if( index != null){
	            	double[] c = T.get(index);
	                c = new double[2];
	                c[0] = 1;	//T1的语义分数Ci
	                c[1] = YUZHI;//T2的语义分数Ci
	                T.put( index, c );
	            }
	        }
	 
	        for ( int i = 0; i < size2 ; i++ ) {
	        	index = T2.get(i) ;
	        	if( index != null ){
	        		double[] c = T.get( index );
	        		if( c != null && c.length == 2 ){
	        			c[1] = 1; //T2中也存在，T2的语义分数=1
	                }else {
	                    c = new double[2];
	                    c[0] = YUZHI; //T1的语义分数Ci
	                    c[1] = 1; //T2的语义分数Ci
	                    T.put( index , c );
	                }
	            }
	        }
	            
	        //开始计算，百分比
	        Iterator<String> it = T.keySet().iterator();
	        double s1 = 0 , s2 = 0, Ssum = 0;  //S1、S2
	        while( it.hasNext() ){
	        	double[] c = T.get( it.next() );
	        	Ssum += c[0]*c[1];
	        	s1 += c[0]*c[0];
	        	s2 += c[1]*c[1];
	        }
	        //百分比
	        return Ssum / Math.sqrt( s1*s2 );
	    } else {
	        throw new Exception("传入参数有问题！");
	    }
	}
	
	public static void main(String[] args) throws Exception {
		Vector<String> str1 = participle("" + 
				"1.造成就诊人轻度残疾、器官组织损伤导致一般功能障碍，或者中度以上残疾、器官组织损伤导致严重功能障碍，或者死亡的；" + 
				"" + 
				"" + 
				"2.造成甲类传染病传播、流行或者有传播、流行危险的；" + 
				"" + 
				"" + 
				"3.使用假药、劣药或不符合国家规定标准的卫生材料、医疗器械，足以严重危害人体健康的；" + 
				"" + 
				"" + 
				"4.非法行医被卫生行政部门行政处罚两次以后，再次非法行医的；" + 
				"" + 
				"" + 
				"5.其他情节严重的情形。" + 
				"" + 
				"");
		Vector<String> str2 = participle("江岸区卫生计生于2014年7月24日办理的吴么舫未取得《医疗机构执业许可证》擅自开展诊疗活动案件，因为吴么舫非法行医被卫生行政部门行政处罚两次以后再次非法行医，根据《最高人民检察院、公安部关于公安机关管辖的刑事案件立案追诉标准的规定（一）》第五十七条第一款第（四）项的规定应当移送公安机关处理。");
		System.out.println(getSimilarity(str1,str2));
	}
}
