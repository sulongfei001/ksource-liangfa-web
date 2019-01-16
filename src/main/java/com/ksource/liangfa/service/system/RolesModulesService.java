package com.ksource.liangfa.service.system;

import com.ksource.liangfa.domain.RolesModulesKey;


/**
 * 角色权限管理服务层接口
 *
 * @author chen
 *
 */
public interface RolesModulesService {
    /**
     * 给角色重新分配权限,修改和新增
     */
    public void authorize(RolesModulesKey rm);

    /**
     * 能过角色ID 删除角色在Role_Module表中的信息，用于角色授权
     * @param roleid 角色ID
     */
    public void del(int roleid);

    /**
     * 通过角色ID获取角色信息和权限信息
     * @param id 角色ID
     * @return
     */
    public RolesModulesKey find(int id);
}
