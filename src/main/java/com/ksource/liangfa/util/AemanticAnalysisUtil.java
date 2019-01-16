package com.ksource.liangfa.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.wltea.analyzer.Lexeme;


/**
 * 语义分析工具类
 * 用语义向量计算语义相似度
 * 用ikanlyzer 先分词然后通过余弦定理计算相似度得出百分百比
 * 经测试 匹配时候两句话长度越接近匹配的值越准确，如果关键字就两个字案件名称非常长匹配率低
 * @author wsq
 * 
 */
public class AemanticAnalysisUtil {
    
 public static final Logger logger=LogManager.getLogger(AemanticAnalysisUtil.class);
    
 
 
 /**
  * 相似度的判读阈值
  * 设置可以提高准确率
  * 阈值设置越大捕获率越大，但是错误率也会上来主要还是关键字
  */
 public static double NAME_JIZHI = 1 ;
 
 public static double DETAIL_JIZHI = 1;
 
    /**
     * 对外提供语义分析方法
     * @param str1
     * @param str2
     * @return
     */
    public  static double aemanticAnalysis(String str1,String str2){
        double result = 0;
        try{
            if(StringUtil.isNotEmpty(str1) && StringUtil.isNotEmpty(str2)){
/*              Vector<String>  t1 =   participle(str1,true);
                Vector<String>  t2 =   participle(str2,true);*/
                result = getSimilarity(str1,str2);
                return result;
            }
        }finally{
        	logger.debug("内容：【"+str1 +"】---->关键词：【"+str2 + "】语义分析结果：【" + result+"】");
        }
        
        return 0;
    }
    

    /**
     * 分词
     * @param str
     * @param isMaxWordLength
     * @return
     */
    public static  Vector<String> participle( String str ,boolean isMaxWordLength) {
            
            Vector<String> str1 = new Vector<String>() ;//对输入进行分词
            
            try {
                
                StringReader reader = new StringReader( str ); 
                org.wltea.analyzer.IKSegmentation ik = new org.wltea.analyzer.IKSegmentation(reader,isMaxWordLength);//当为true时，分词器进行最大词长切分 
                Lexeme lexeme = null ;          
                
                while( ( lexeme = ik.next() ) != null ) {
                str1.add( lexeme.getLexemeText() ); 
                }           
                
                if( str1.size() == 0 ) {
                    return null ;
                }
                
                //分词后
               // System.out.println( "str分词后：" + str1 );
                
            } catch ( IOException e1 ) {
                //忽略
            }
            return str1;
        }
    
   
    public static double getSimilarity(String doc1, String doc2) {  
        if (doc1 != null && doc1.trim().length() > 0 && doc2 != null&& doc2.trim().length() > 0) {  
           Map<Integer, double[]> AlgorithmMap = new HashMap<Integer, double[]>();  
           //将两个字符串中的中文字符以及出现的总数封装到，AlgorithmMap中  
           for (int i = 0; i < doc1.length(); i++) {  

                char d1 = doc1.charAt(i);
                if(isHanZi(d1)){
                   int charIndex = getGB2312Id(d1);  //ch在GB2312中的位置
                    if(charIndex != -1){  
                        double[] fq = AlgorithmMap.get(charIndex);  
                       if(fq != null && fq.length == 2){  
                           fq[0]++;  
                        }else {  
                            fq = new double[2];  
                           fq[0] = 1;  
                            fq[1] = 0;  
                           AlgorithmMap.put(charIndex, fq);  
                        }  
                   }  
                }
           }  

   

            for (int i = 0; i < doc2.length(); i++) {  

                char d2 = doc2.charAt(i);  

               if(isHanZi(d2)){  
                   int charIndex = getGB2312Id(d2);  
                   if(charIndex != -1){  
                       double[] fq = AlgorithmMap.get(charIndex);
                        if(fq != null && fq.length == 2){ 
                            fq[1]++; 
                       }else {  
                           fq = new double[2];  
                           fq[0] = 0;  
                           fq[1] = 1;  
                           AlgorithmMap.put(charIndex, fq);  
                        }  
                    }  
                }  
            }  
            Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();  
            double sqdoc1 = 0;  
           double sqdoc2 = 0;  
            double denominator = 0; 
            while(iterator.hasNext()){  
                double[] c = AlgorithmMap.get(iterator.next());  
                denominator += c[0]*c[1];  
                sqdoc1 += c[0]*c[0];  
                sqdoc2 += c[1]*c[1];  
           }  
            return denominator / Math.sqrt(sqdoc1*sqdoc2);  
       } else {  
           return 0; 
        }  
    }  
    
    public static boolean isHanZi(char ch) {  
        // 判断是否汉字  
       return (ch >= 0x4E00 && ch <= 0x9FA5);  
    }  

    /**  
    * 根据输入的Unicode字符，获取它的GB2312编码或者ascii编码，  
    * @param ch  
    *  输入的GB2312中文字符或者ASCII字符(128个) 
    * @return ch在GB2312中的位置，-1表示该字符不认识  
    */ 

    public static short getGB2312Id(char ch) {  
       try {  
           byte[] buffer = Character.toString(ch).getBytes("GB2312");  
            if (buffer.length != 2) {  
                // 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'?'，此时说明不认识该字符  
                return -1;  
            }  
            int b0 = (int) (buffer[0] & 0x0FF) - 161; // 编码从A1开始，因此减去0xA1=161  
            int b1 = (int) (buffer[1] & 0x0FF) - 161; // 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字  
           return (short) (b0 * 94 + b1);  

        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  

        }  
        return -1;  
    }  
    
    
}
