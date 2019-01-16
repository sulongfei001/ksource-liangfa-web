package com.ksource.liangfa.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.FieldItem;
import com.ksource.liangfa.domain.FormDef;
import com.ksource.liangfa.domain.FormField;
import com.ksource.liangfa.mapper.FieldItemMapper;
import com.ksource.liangfa.mapper.FormDefMapper;
import com.ksource.liangfa.mapper.FormFieldMapper;
import com.ksource.syscontext.Const;

@Service("formManageService")
public class FormManageService {
	private static JsonMapper binder = JsonMapper.buildNonNullMapper();

	@Autowired
	SystemDAO systemDAO;
	@Autowired
	FormDefMapper formDefMapper;
	@Autowired
	FormFieldMapper formFieldMapper;
	@Autowired
	FieldItemMapper fieldItemMapper;
	
	/**
	 * 创建表单模板
	 * @param formDef	form表单各项数据：动作、字段、选项值
	 */
	@Transactional
	public void createForm(FormDef formDef){
		//表单模板ID
		int formDefId=systemDAO.getSeqNextVal(Const.TABLE_FORM_DEF);
		formDef.setFormDefId(formDefId);
		//保存表单字段
		Assert.notEmpty(formDef.getFormFieldList());
		for(FormField formField : formDef.getFormFieldList()){
			int fieldId=systemDAO.getSeqNextVal(Const.TABLE_FORM_FIELD);
			formField.setFieldId(fieldId);
			formField.setFormDefId(formDefId);
			formFieldMapper.insert(formField);//保存表单字段
			List<FieldItem> fieldItemList= formField.getFieldItemList();
			if(!CollectionUtils.isEmpty(fieldItemList)){
				for(FieldItem fieldItem : fieldItemList){
					int itemId = systemDAO.getSeqNextVal(Const.TABLE_FIELD_ITEM);
					fieldItem.setItemId(itemId);
					fieldItem.setFieldId(fieldId);
					fieldItemMapper.insert(fieldItem);//保存字段选项值
				}
			}
		}
		String formJsonoView = binder.toJson(formDef);
		formDef.setJsonView(formJsonoView);
		formDefMapper.insert(formDef);//保存表单
	}

	public void deleteByPrimaryKey(Integer formDefId) {
		fieldItemMapper.deleteByFormDefId(formDefId);
		formFieldMapper.deleteByFormDefId(formDefId);
		formDefMapper.deleteByPrimaryKey(formDefId);
	}

	@Transactional
	public void updateForm(FormDef formDef) {
		Integer formDefId = formDef.getFormDefId();
		fieldItemMapper.deleteByFormDefId(formDefId);
		formFieldMapper.deleteByFormDefId(formDefId);
		//表单模板ID
		Assert.notEmpty(formDef.getFormFieldList());
		for(FormField formField : formDef.getFormFieldList()){
			Integer fieldId = formField.getFieldId();
			if(fieldId == null){
				fieldId=systemDAO.getSeqNextVal(Const.TABLE_FORM_FIELD);
				formField.setFieldId(fieldId);
			}else{
				formField.setFieldId(fieldId);
			}
			formField.setFormDefId(formDefId);
			formFieldMapper.insert(formField);//保存表单字段
			
			List<FieldItem> fieldItemList= formField.getFieldItemList();
			if(!CollectionUtils.isEmpty(fieldItemList)){
				for(FieldItem fieldItem : fieldItemList){
					Integer itemId = fieldItem.getItemId();
					if(fieldItem.getItemId() == null){
						itemId = systemDAO.getSeqNextVal(Const.TABLE_FIELD_ITEM);
						fieldItem.setItemId(itemId);
					}else{
						fieldItem.setItemId(itemId);
					}
					fieldItem.setFieldId(fieldId);
					fieldItemMapper.insert(fieldItem);//保存字段选项值
				}
			}
		}
		String formJsonoView = binder.toJson(formDef);
		formDef.setJsonView(formJsonoView);
		formDefMapper.updateByPrimaryKeySelective(formDef);
	}
}
