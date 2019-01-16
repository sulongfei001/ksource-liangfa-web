 
package com.ksource.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.DictionaryExample;
import com.ksource.liangfa.domain.DtGroup;
import com.ksource.liangfa.domain.DtGroupExample;
import com.ksource.liangfa.mapper.DictionaryMapper;
import com.ksource.liangfa.mapper.DtGroupMapper;

public class DictionaryManager {
	
	
	private static Map<String, DtGroup> dtGroupMap=new HashMap<String, DtGroup>();//字典分组map：key="group_code"
	private static Map<String, Dictionary> dicMap=new HashMap<String, Dictionary>();//key="groupCode_dtCcde"
	//key="group_code"
	private static Map<String, List<Dictionary>> groupDicMap=new HashMap<String, List<Dictionary>>();
	
	
	/**
	 * 得到所有字典表分类
	 * @return	字典分类列表List
	 */
	
	public static Collection<DtGroup> getDtGroups() {
		return dtGroupMap.values();
	}
	
	/**
	 * 字典列表List
	 * @param groupCode 分类代码
	 * @return	字典列表List
	 */
	
	public static List<Dictionary> getDictList(String groupCode) {
		return groupDicMap.get(groupCode);
	}
	
	public static DtGroup getDtGroup(String groupCode) {
		return dtGroupMap.get(groupCode);
	}
	
	public static Dictionary getDictionary(String groupCode,String dicCode) {
		return dicMap.get(groupCode+"_"+dicCode);
	}
	
	public static void init(DictionaryMapper dictionaryMapper, DtGroupMapper dtGroupMapper) throws Exception{
		//清理
		dtGroupMap.clear();
		dicMap.clear();
		groupDicMap.clear();
		Integer active = 1;
		DictionaryExample dictionaryExam = new  DictionaryExample();
		dictionaryExam.createCriteria().andActiveEqualTo(active);
		dictionaryExam.setOrderByClause("GROUP_CODE,SERIAL");
		DtGroupExample dtGroupExam = new DtGroupExample();
		dtGroupExam.createCriteria().andActiveEqualTo(active);
		dtGroupExam.setOrderByClause("SERIAL");
		List<Dictionary> dicList= dictionaryMapper.selectByExample(dictionaryExam);
		List<DtGroup> dtGroupList= dtGroupMapper.selectByExample(dtGroupExam);
		
		List<Dictionary> isEachDics=new ArrayList<Dictionary>();//已经处理过的Dictionary
		for(int i=0;i<dtGroupList.size();i++){
			isEachDics.clear();
			DtGroup dtGroup = dtGroupList.get(i);
			//加入到dtGroupMap
			dtGroupMap.put(dtGroup.getGroupCode(),dtGroup);
			for(int j=0;j<dicList.size();j++){
				Dictionary dic = dicList.get(j);
				//加入到dicMap
				dicMap.put(dic.getGroupCode()+"_"+dic.getDtCode(), dic);
				if(dic.getGroupCode().equals(dtGroup.getGroupCode())){
					//加入到groupDicMap
					List<Dictionary> dics = groupDicMap.get(dic.getGroupCode());
					if(dics==null){
						dics = new ArrayList<Dictionary>();
						groupDicMap.put(dic.getGroupCode(),dics);
					}
					dics.add(dic);
					isEachDics.add(dic);
				}
			}
			dicList.removeAll(isEachDics);
			isEachDics.clear();
		}
	}
	
}
