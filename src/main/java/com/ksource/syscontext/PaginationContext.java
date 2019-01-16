package com.ksource.syscontext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;

/**
 * 此类为 分页相关类
 * 
 * @author zxl :)
 * @version 1.0 date Mar 14, 2011 time 10:59:08 AM
 */
public class PaginationContext<T> {
	// 日志
	private static final Logger logger = LogManager
			.getLogger(PaginationContext.class);
	private Map<String, Object> map = new HashMap<String, Object>();
	private PaginationHelper<T> helper = new PaginationHelper<T>();

	/**
	 * 返回保存查询条件的容器. <br>
	 * 返回Map<String,Object>,键为第一个参数的非空属性名,值为第一个参数的非空属性值,
	 * 除了属性名外，另外还有用于控制分页的start和end,它们两个的值也被保存到了Map中.
	 * @param model
	 * @param page
	 * @return
	 */
	public Map<String, Object> getConditionMap(Object model,String page) {
		getConditionMap(model);
		//设置页码
		setPageNumber(page);
		map.put("start", helper.getStartNum());
		map.put("end", helper.getEndNum());

		return map;
	}
	
	public Map<String, Object> getConditionMap(String page) {
		//设置页码
		setPageNumber(page);
		map.put("start", helper.getStartNum());
		map.put("end", helper.getEndNum());

		return map;
	}
	public  Map<String, Object> getConditionMap(Object model){
		if (model == null)
			return null;
		List<Field> fieldList= new ArrayList<Field>();
		Class class1 = (Class)model.getClass();
		Class upClasses =class1.getSuperclass();
		//添加父类属性
		fieldList.addAll(Arrays.asList(upClasses.getDeclaredFields()));
		//添加类属性
		fieldList.addAll(Arrays.asList(class1.getDeclaredFields()));
		
		for (Field field : fieldList) {
			String fieldName = field.getName();
			Object fieldValue;

			try {
				Method method = class1.getMethod(
						"get" + Character.toUpperCase(fieldName.charAt(0))+ fieldName.substring(1), 
						null);
				fieldValue =  method.invoke(model, null);
			} catch (Exception e) {
				try {
					if (!Modifier.isPublic(field.getModifiers())) {
						field.setAccessible(true);
					}

					fieldValue = field.get(model);
				} catch (Exception exception) {
					logger.error("分页调用失败：" + exception.getMessage());
					throw new BusinessException("分页调用失败");
				}
			}
		if(fieldValue!=null){
		if(fieldValue instanceof String){
			String fieldValueString = String.valueOf(fieldValue);
			if (StringUtils.isNotBlank(fieldValueString)) {
				map.put(fieldName, fieldValueString.trim()); // 处理查询参数
			}
		}else{
			map.put(fieldName, fieldValue); 
			//getConditionMap(fieldValue);// 处理查询参数TODO	
		}
			}
		}
		return map;
	}
	public void setPageNumber(String page) {
		int pageNum;

		// 处理当前页
		if(page==null||page.equals("")){
			pageNum=1;
		} else {
			pageNum = Integer.parseInt(page);
		}

		// 处理并存储当前页
		helper.setPageNumber(pageNum);
	}

	public PaginationHelper<T> getPaginationList(int rowCount, List<T> rowList) {
        // 存储总记录数
       setPageCount(rowCount);
       // 存储查询数据
       setList(rowList);
		return helper;
	}

	private void setList(List<T> list) {
		this.helper.setList(list);
	}

	private void setPageCount(int pageCount) {
		this.helper.setFullListSize(pageCount);
	}
}
