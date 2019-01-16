package com.ksource.liangfa.web.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.ModuleExample;
import com.ksource.liangfa.domain.ModuleExample.Criteria;
import com.ksource.liangfa.domain.RolesModulesKey;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.ModuleMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.ModuleService;
import com.ksource.liangfa.service.system.RolesModulesService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;


/**
 * @author zxl
 *
 */
@Controller
@RequestMapping("/system/module")
public class ModuleController {
    public static final String MODULE_MAIN_PAGE = "system/module/moduleMain";
    public static final String MODULE_INFO_PAGE = "system/module/moduleInfo";
    public static final String REDIRECT_CHOOSE_MODULE_PAGE = "redirect:/system/module/toChooseModule/";
    public static final String REDI_MODULE_INFO_VIEW ="redirect:/system/module/getModuleTreeSelected/";
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private RolesModulesService roleModuleService;
    @Autowired
    private MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    private RolesModulesService rolesModulesService;

    /**菜单管理界面*/
    @RequestMapping(value = "toMain")
    public String toMain(HttpSession session, ModelMap model) {
        User loginUser = SystemContext.getCurrentUser(session);
        List<Module> rootMenus = moduleService.getChildModule(loginUser,
                Const.TOP_MODULE_ID);
        model.put("rootMenus", rootMenus);

        return MODULE_MAIN_PAGE;
    }

    /** 点击时进入所选节点详细信息页面*/
    @RequestMapping(value = "getModuleTreeSelected/{id}")
    public ModelAndView getModuleTreeSelected(@PathVariable
    Integer id,ResponseMessage res,String isDel) {
        id = (id == null) ? Const.TOP_MODULE_ID : id;

        ModelAndView view = new ModelAndView(MODULE_INFO_PAGE);

        Module module = mybatisAutoMapperService.selectByPrimaryKey(ModuleMapper.class,
                id, Module.class);
        view.addObject("module", module);
        view.addObject("isLoadTree", res.getIsLoadTree());
        view.addObject("isDel",isDel);
        view.addObject("message",ResponseMessage.parseMsg(res));
        return view;
    }

    /** 修改更新模块信息*/
    @RequestMapping(value = "update")
    public String update(Module module) {
        if(module.getIsLeaf()==null){//add default value
        	module.setIsLeaf(Const.LEAF_NO);
        }
        if(module.getIsMaintain()==null){
        	module.setIsMaintain(Const.MODULE_IS_MAINTAIN_NO);
        }
        mybatisAutoMapperService.updateByPrimaryKeySelective(ModuleMapper.class,
            module);
        
        return ResponseMessage.addPromptTypeForPath(REDI_MODULE_INFO_VIEW+module.getModuleId(), PromptType.update);
    }

    /** 删除一个模块*/
    @RequestMapping(value = "del/{moduleid}")
    public String del(@PathVariable
    Integer moduleid) {
        String path =REDI_MODULE_INFO_VIEW;
        Module module = null;
        if (!moduleid.equals(Const.TOP_MODULE_ID)) {
            Boolean boo = moduleService.isNoChildren(moduleid);

            if (boo) {
            	//先把要删除的module给查出来
            	module=mybatisAutoMapperService.selectByPrimaryKey(ModuleMapper.class, moduleid, Module.class);
                path+=module.getParentId();
            	moduleService.del(moduleid,module.getParentId());
            	path+="?isDel=true";//删除标志，根据这个标志，前台zTree会执行刷新树操作。
                path=ResponseMessage.addIsLoadTreeForPath(path,true);
            } else {
            	path+=moduleid;
            	path=ResponseMessage.addPromptTypeForPath(path, PromptType.moduleDelFail);
            }
        }
        return path;
    }

    /**新增一个模块*/
    @RequestMapping(value = "insert")
    public String insert(Module module) {
        moduleService.insert(module);
        return ResponseMessage.addIsLoadTreeForPath(REDI_MODULE_INFO_VIEW +
              module.getParentId(), true);
    }

    /**进入角色权限设置页面*/
    @RequestMapping(value = "toChooseModule/{id}")
    public ModelAndView toChooseModule(String redirect, @PathVariable
    Integer id) {
        ModelAndView view = new ModelAndView("system/module/chooseModule");
        RolesModulesKey rolesModulesKey = rolesModulesService.find(id);
        view.addObject("role_Module", rolesModulesKey);

        if ((redirect == null) || redirect.equals("true")) {
            view.addObject("message", "权限设置成功!");
        }

        return view;
    }
   /**给角色设置分配权限*/
    @RequestMapping(value = "authorizeRole")
    public ModelAndView authorizeRole(RolesModulesKey role_Module) {
        roleModuleService.authorize(role_Module);

        ModelAndView view = new ModelAndView(REDIRECT_CHOOSE_MODULE_PAGE +
                role_Module.getRoleId());

        return view;
    }
    /**根据用户权限加载下级菜单(判断用户管理员)
     * @see ModuleService#getChildModule(User, Integer)
     * */
    @RequestMapping(value = "loadChildModule")
    public void loadChildModule(HttpServletResponse response,HttpServletRequest request, Integer id) {
        id = id==null?Const.DUMMY_SUPER_TOP_MODULE_ID:id;

        //判断权限  
        List<Module> modules = moduleService.getChildModule(SystemContext.getCurrentUser(request), id);
        String trees = JsTreeUtils.moduleJsonztree(modules);
        response.setContentType("application/json");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.print(trees);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**根据用户权限加载下级菜单(不判断用户管理员)　
     * @see ModuleService#getChildModule(String, Integer)
     * */
    @RequestMapping(value = "loadChildModuleByAuthority")
    public void loadChildModuleByAuthority(HttpServletRequest request,
        HttpServletResponse response, Integer id) {
    	 id = id==null?Const.DUMMY_SUPER_TOP_MODULE_ID:id;
    	 User user =SystemContext.getCurrentUser(request);
    	 String userId = user.getUserId();
    	 String account = user.getAccount();
    	 boolean isAdmin = false;
		if(Const.SYSTEM_ADMIN_ID.equals(account)){
			isAdmin = true;
		}
        //判断权限  
        List<Module> modules = moduleService.getChildModule(userId, id,isAdmin);

        String trees = JsTreeUtils.moduleJsonztree(modules);

        response.setContentType("application/json");

        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.print(trees);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    /** 角色设置菜单权限--根据当前用户类型及角色所拥有的权限生成zTree可以解析的Json字符串.
     *  角色已有的权限所对应的节点的左侧复选框会被选中.
     * */
    @RequestMapping(value = "getModuleTreeForRole")
    public void getModuleTreeForRole(Integer id, String modules,
        HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        id = (id == null) ? Const.TOP_MODULE_ID : id;
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();

        ArrayList<Integer> moduleList = new ArrayList<Integer>();

        if (!modules.isEmpty()) {
            String[] mod = modules.split(",");

            for (int i = 0; i < mod.length; i++) {
                Integer moduleId = Integer.parseInt(mod[i].trim());
                moduleList.add(moduleId);
            }
        }

        moduleService.buildModuleTree(SystemContext.getCurrentUser(request),
            id, sb, moduleList);
        out.print(sb.toString());
        out.flush();
        out.close();
    }
    //验证菜单名是否重复
    @RequestMapping(value="checkName")
	@ResponseBody
	public boolean checkName(String moduleName,Integer moduleId){
    	String modulename = moduleName.trim();
    	ModuleExample example = new ModuleExample();
    	Criteria criteria=example.createCriteria().andModuleNameEqualTo(modulename);
    	if(moduleId!=null){
    		criteria.andModuleIdNotEqualTo(moduleId);
    	}
    	int size = mybatisAutoMapperService.countByExample(ModuleMapper.class, example);
    	if(size>0){
    		return false;
    	}
    	return true;
    }
    
    
    @RequestMapping(value="getModuleTreeData")
	@ResponseBody
	public List<Module> getModuleTreeData(HttpServletRequest request){
   	 User user =SystemContext.getCurrentUser(request);
   	 boolean isAdmin = false;
		if(Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
			isAdmin = true;
		}
    	return moduleService.getModules(user.getUserId(),isAdmin);
    }
}