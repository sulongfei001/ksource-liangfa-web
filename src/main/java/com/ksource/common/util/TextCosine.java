package com.ksource.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;


public class TextCosine {
    private static Map<String, String> map= null; 
    
    static {
        map = new HashMap<String, String>();
        try {
            InputStreamReader isReader = new InputStreamReader(new FileInputStream(TextCosine.class.getResource("/synonyms.dict").getPath()), "UTF-8");
            BufferedReader br = new BufferedReader(isReader);
            String s = null;
            while ((s = br.readLine()) !=null) {
                String []synonymsEnum = s.split("→");
                map.put(synonymsEnum[0], synonymsEnum[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static List<ElementDict> tokenizer(String str) {
        List<ElementDict> list = new ArrayList<ElementDict>();
        IKAnalyzer analyzer = new IKAnalyzer(true);
        try {
            TokenStream stream = analyzer.tokenStream("", new StringReader(str));
            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
            stream.reset();
            int index = -1;
            while (stream.incrementToken()) {
                if ((index = isContain(cta.toString(), list)) >= 0) {
                    list.get(index).setFreq(list.get(index).getFreq() + 1);
                }
                else {
                    list.add(new ElementDict(cta.toString(), 1));
                }
            }
            analyzer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return list;
    }
    
    
    public static int isContain(String str, List<ElementDict> list) {
        for (ElementDict ed : list) {
            if (ed.getTerm().equals(str)) {
                return list.indexOf(ed);
            } else if (map.get(ed.getTerm())!= null && map.get(ed.getTerm()).equals(str)) {
                return list.indexOf(ed);
            }
        }
        return -1;
    }
    
    
    public static List<String> mergeTerms(List<ElementDict> list1, List<ElementDict> list2) {
        List<String> list = new ArrayList<String>();
        for (ElementDict ed : list1) {
            if (!list.contains(ed.getTerm())) {
                list.add(ed.getTerm());
            } else if (!list.contains(map.get(ed.getTerm()))) {
                list.add(ed.getTerm());
            }
        }
        
        for (ElementDict ed : list2) {
            if (!list.contains(ed.getTerm())) {
                list.add(ed.getTerm());
            } else if (!list.contains(map.get(ed.getTerm()))) {
                list.add(ed.getTerm());
            }
        }
        return list;
    }
    
    
    public static int anslysisTerms(List<ElementDict> list1, List<ElementDict> list2) {
        int len1 = list1.size();
        int len2 = list2.size();
        //则针对长句选定短句长度的文本内容逐个与短句进行相似度判定，直至长句结束，若中间达到预设的阈值，则跳出该循环，否则判定文本不相似。
        if (len2 >= len1 * 1.5) {
            List<ElementDict> newList = new ArrayList<ElementDict>();
            for (int i = 0; i + len1 <= len2; i++) {
                for (int j = 0; j < len1; j++) 
                    newList.add(list2.get(i+j));
                
                newList = adjustList(newList, list2, len2, len1, i);
                if (getResult(analysis(list1, newList))) 
                    return 1;
                else 
                    newList.clear();
            }
        } else if (len1 >= len2 * 1.5) {
            List<ElementDict> newList = new ArrayList<ElementDict>();
            for (int i = 0; i + len2 <= len1; i++) {
                for (int j = 0; j < len2; j++)
                    newList.add(list1.get(i+j));
                
                newList = adjustList(newList, list1, len1, len2, i);
                if (getResult(analysis(newList, list2))) 
                    return 1;
                else 
                    newList.clear();
            }
        } else {
            if (getEasyResult(analysis(list1, list2))) 
                return 1;
        }
        return 0;
    }
    
    
    public static List<ElementDict> adjustList(List<ElementDict> newList, List<ElementDict> list, int lenBig, int lenSmall, int index) {
        int gap = lenBig -lenSmall;
        int size = (gap/2 > 2) ? 2: gap/2;
        if (index < gap/2) {
            for (int i = 0; i < size; i++) {
                newList.add(list.get(lenSmall+index+i));
            }
        } else {
            for (int i = 0; i > size; i++) {
                newList.add(list.get(lenBig-index-i));
            }
        }
        return newList;
    }
    
    
    public static double analysis(List<ElementDict> list1, List<ElementDict> list2) {
        List<String> list = mergeTerms(list1, list2);
        List<Integer> weightList1 = assignWeight(list, list1);
        List<Integer> weightList2 = assignWeight(list, list2);
        return countCosSimilariry(weightList1, weightList2);
    }
    
    
    public static List<Integer> assignWeight(List<String> list, List<ElementDict> list1) {
        List<Integer> vecList = new ArrayList<Integer>(list.size());
        boolean isEqual = false;
        for (String str : list) {
            for (ElementDict ed : list1) {
                if (ed.getTerm().equals(str)) {
                    isEqual = true;
                    vecList.add(new Integer(ed.getFreq()));
                } else if (map.get(ed.getTerm())!= null && map.get(ed.getTerm()).equals(str)) {
                    isEqual = true;
                    vecList.add(new Integer(ed.getFreq()));
                }
            }
            
            if (!isEqual) {
                vecList.add(new Integer(0));
            }
            isEqual = false;
        }
        return vecList;
    }
    
    
    public static double countCosSimilariry(List<Integer> list1, List<Integer> list2) {
        double countScores = 0;
        int element = 0;
        int denominator1 = 0;
        int denominator2 = 0;
        int index = -1;
        for (Integer it : list1) {
            index ++;
            int left = it.intValue();
            int right = list2.get(index).intValue();
            element += left * right;
            denominator1 += left * left;
            denominator2 += right * right;
        }
        try {
            countScores = (double)element / Math.sqrt(denominator1 * denominator2);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        return countScores;
    }
    
    
    public static boolean getResult(double scores) {
        if (scores >= 0.85)
            return true;
        else 
            return false;
    }
    
    
    public static boolean getEasyResult(double scores) {
        if (scores >= 0.75)
            return true;
        else 
            return false;
    }

	public static boolean CompareText(String str1,String str2){
		if(StringUtils.isBlank(str1)||StringUtils.isBlank(str2)){
			return false;
		}
		List<ElementDict> dicts1 = tokenizer(str1);
		List<ElementDict> dicts2 = tokenizer(str2);
		try {
			int similarity = anslysisTerms(dicts1,dicts2);
			return similarity > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		String str1 = "" +  
				"4.非法行医被卫生行政部门行政处罚两次以后，再次非法行医的；" + 
				"";
		String str2 = "江岸区卫生计生于2014年7月24日办理的吴么舫未取得《医疗机构执业许可证》擅自开展诊疗活动案件，因为吴么舫非法行医被卫生行政部门行政处罚两次以后再次非法行医，根据《最高人民检察院、公安部关于公安机关管辖的刑事案件立案追诉标准的规定（一）》第五十七条第一款第（四）项的规定应当移送公安机关处理。";
		System.out.println(CompareText(str1,str2));
		//不加/
		//System.out.println(TextCosine.class.getResource(""));
        //System.out.println(TextCosine.class.getResource("/"));
	}
    
}