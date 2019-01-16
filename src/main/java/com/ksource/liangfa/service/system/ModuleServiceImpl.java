package com.ksource.liangfa.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.ModuleExample;
import com.ksource.liangfa.domain.ModuleExample.Criteria;
import com.ksource.liangfa.domain.PostRoleExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.ModuleMapper;
import com.ksource.liangfa.mapper.PostRoleMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.syscontext.Const;


/**
 * 此类为 菜单业务 实现类(服务层)
 *
 * @author zxl :)
 * @version 1.0 date 2011-5-10 time 下午02:45:24
 */
@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private UserMapper userMapper;
    
	@Autowired
	private PostRoleMapper postRoleMapper;
	@Autowired
	private SystemDAO systemDAO;
    // 日志
    private static final Logger log = LogManager.getLogger(ModuleServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<Module> getChildModule(String userId, Integer parentModuleId,boolean isAdmin) {
        List<Module> modules = null;

        try {
        
            if (isAdmin) { // 系统管理员systemAdmin

                ModuleExample example = new ModuleExample();
                example.createCriteria().andParentIdEqualTo(parentModuleId);
                example.setOrderByClause("ORDER_NO");
                modules = moduleMapper.selectByExample(example);
            } else { // 其他用户

                Map<String, String> paramMap = new HashMap<String, String>(2);
                paramMap.put("parentId", parentModuleId.toString());

                paramMap.put("userId", userId);
                
                modules = moduleMapper.getChildModule(paramMap);
            }
        } catch (Exception e) {
            log.error("菜单生成失败：" + e.getMessage());
            throw new BusinessException("菜单生成失败");
        }

        return modules;
    }
    
    @Override
    @Transactional(readOnly = true)
    public void getHomeModule(String userId,Integer parentModuleId,List<Module> moduleList,int moduleCategory,boolean isAdmin) {
    	try {
    		if(CollectionUtils.isEmpty(moduleList)){
        		if (isAdmin) { // 系统管理员systemAdmin
        			ModuleExample example = new ModuleExample();
        			example.createCriteria().andParentIdEqualTo(parentModuleId).andModuleCategoryEqualTo(moduleCategory);
        			example.setOrderByClause("ORDER_NO");
        			moduleList.addAll(moduleMapper.selectByExample(example));
        		} else { // 其他用户
        			Map<String, String> paramMap = new HashMap<String, String>(2);
        			paramMap.put("parentId", parentModuleId.toString());
        			paramMap.put("userId", userId);
        			paramMap.put("moduleCategory", String.valueOf(moduleCategory));
        			moduleList.addAll(moduleMapper.getChildModule(paramMap));
        		}
        	}
    		for(Module rootModule : moduleList){
    			if(rootModule.getIsLeaf()==Const.LEAF_YES){
    				continue;
    			}
    			List<Module> childModuleList = rootModule.getChildModelList();
    			if(childModuleList==null){
    				childModuleList =new ArrayList<Module>();
    				rootModule.setChildModelList(childModuleList);
    			}
    			getHomeModule(userId, rootModule.getModuleId(), childModuleList,moduleCategory,isAdmin);
    		}
    	} catch (Exception e) {
    		log.error("菜单生成失败：" + e.getMessage());
    		throw new BusinessException("菜单生成失败");
    	}
    }

    @Override
    @Transactional(readOnly = true)
    public List<Module> getChildModule(User user, Integer parentModuleId) {
    	try{
        String userId = user.getUserId();

        String account = user.getAccount();
        
        boolean isAdmin = false;
        if (!Const.SYSTEM_ADMIN_ID.equals(account) &&
                user.getUserType().equals(Const.USER_TYPE_ADMIN)) {
            ModuleExample example = new ModuleExample();
            example.createCriteria().andParentIdEqualTo(parentModuleId)
                   .andIsMaintainEqualTo(Const.MODULE_IS_MAINTAIN_NO);
            example.setOrderByClause("ORDER_NO");

            return moduleMapper.selectByExample(example);
        }
        
        if(Const.SYSTEM_ADMIN_ID.equals(account)){
        	isAdmin = true;
        }
        
    	return getChildModule(userId, parentModuleId, isAdmin);
    	} catch (Exception e) {
    		log.error("菜单生成失败：" + e.getMessage());
    		throw new BusinessException("菜单生成失败");
    	}
    }
    
    @Override
    @Transactional(readOnly = true)
    public Boolean isNoChildren(Integer id) {
        Boolean boo = false;
        ModuleExample example = new ModuleExample();
        example.createCriteria().andParentIdEqualTo(id);
        try {
            int cheirdrenCount = moduleMapper.countByExample(example);
            if (cheirdrenCount == 0) {
                boo = true;
            }
            return boo;
        } catch (Exception e) {
            log.error("查询菜单失败：" + e.getMessage());
            throw new BusinessException("查询菜单失败");
        }
    }

    @Override
    @Transactional
    public void del(Integer moduleid,Integer parentId) {
    	PostRoleExample example = new PostRoleExample();
    	example.createCriteria().andPostIdEqualTo(moduleid);
        try {
        	//1.判断菜单是否可以删除(是否有角色在用此菜单)
        	  int size =postRoleMapper.countByExample(example);
        	  if(size==0){
        		//2.删除菜单
                  moduleMapper.deleteByPrimaryKey(moduleid);
                //3.修改被删除菜单的父菜单的节点状态
                  if (isNoChildren(parentId)) {
                      Module parentModule = new Module();
                      parentModule.setModuleId(parentId);
                      parentModule.setIsLeaf(Const.LEAF_YES);
                      moduleMapper.updateByPrimaryKeySelective(parentModule);
                  } 
        	  }
        } catch (Exception e) {
            log.error("删除菜单失败：" + e.getMessage());
            throw new BusinessException("删除菜单失败");
        }
    }

    @Override
    @Transactional
    public void buildModuleTree(User user, Integer id, StringBuffer buffer,
        ArrayList<Integer> modules) {
        try {
            List<Module> list = getChildModule(user, id);
            Iterator<Module> it = list.iterator();
            buffer.append("[");

            for (int i = 1; it.hasNext(); i++) {
                boolean isParent = false;
                Module mod = it.next();
                buffer.append("{\"id\":\"").append(mod.getModuleId())
                      .append("\",\"name\":\"").append(mod.getModuleName());

                if (mod.getIsLeaf() == Const.LEAF_NO) {
                    isParent = true;
                }

                buffer.append("\",\"isParent\":").append(isParent);
                buffer.append(",\"open\":").append(isParent);

                if (modules != null) {
                    if (modules.contains(mod.getModuleId())) {
                        buffer.append(",\"checked\":true");
                    }
                }
                //TODO:添加子节点
                if (mod.getIsLeaf() == Const.LEAF_NO) {
                    buffer.append(",\"nodes\":");
                    buildModuleTree(user, mod.getModuleId(), buffer, modules);
                }
                buffer.append("}");

                if ((list.size() > 1) && (i < list.size())) {
                    buffer.append(",");
                }
            }

            buffer.append("]");
        } catch (Exception e) {
            log.error("查询权限失败：" + e.getMessage());
            throw new BusinessException("查询权限失败");
        }
    }

    @Override
    @Transactional
    public ServiceResponse insert(Module module) {
    	ServiceResponse res = new ServiceResponse();
    	try{
        //添加默认值
    	Integer parentId = (module.getParentId() == null) ? Const.TOP_MODULE_ID
                     : module.getParentId(); 
    	module.setParentId(parentId);
    	
        if (module.getIsLeaf() == null) {
            module.setIsLeaf(Const.LEAF_YES);
        }

        if (module.getIsMaintain() == null) {
            module.setIsMaintain(Const.MODULE_IS_MAINTAIN_NO);
        }
        module.setModuleId(systemDAO.getSeqNextVal(Const.TABLE_MODULE));
        moduleMapper.insertSelective(module);

        module = moduleMapper.selectByPrimaryKey(module.getParentId());

        //如果父节点 状态是 叶子节点 状态，修改之
        if (module.getIsLeaf() == Const.LEAF_YES) {
            module.setIsLeaf(Const.LEAF_NO);
            moduleMapper.updateByPrimaryKeySelective(module);
        }
    	} catch (Exception e) {
    		log.error("菜单添加失败：" + e.getMessage());
    		throw new BusinessException("菜单添加失败");
    	}
    	res.setingSucess("添加成功");
    	return res;
    }

	@Override
	public List<Module> getModules(String userId,boolean isAdmin) {
		List<Module> modules = new ArrayList<Module>();
		if (isAdmin) { // 系统管理员systemAdmin
            ModuleExample example = new ModuleExample();
            example.setOrderByClause("ORDER_NO");
            Criteria createCriteria = example.createCriteria();
            createCriteria.andModuleCategoryEqualTo(Const.MODULE_CATEGORY_WEB);
            modules = moduleMapper.selectByExample(example);
        } else { // 其他用户
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userId", userId);
            paramMap.put("moduleCategory", Const.MODULE_CATEGORY_WEB.toString());
			modules = moduleMapper.getChildModule(paramMap);
        }
		return modules;
	}
	@Override
	public List<Module> getAppModules(String userId,boolean isAdmin) {
		List<Module> modules = new ArrayList<Module>();
		if (isAdmin) { // 系统管理员systemAdmin
			ModuleExample example = new ModuleExample();
			example.setOrderByClause("ORDER_NO");
			Criteria createCriteria = example.createCriteria();
			createCriteria.andModuleCategoryEqualTo(Const.MODULE_CATEGORY_APP);
			modules = moduleMapper.selectByExample(example);
		} else { // 其他用户
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userId", userId);
			paramMap.put("moduleCategory", Const.MODULE_CATEGORY_APP.toString());
			modules = moduleMapper.getChildModule(paramMap);
		}
		return modules;
	}
}
