package com.ksource.liangfa.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.mapper.DistrictMapper;

/**
 * 此类为行政区划服务类
 * 
 * @author zxl :)
 * @version 1.0 date 2011-8-22 time 下午04:45:49
 */
@Service
public class DistrictServiceImpl implements DistrictService {
	@Autowired
	private DistrictMapper districtMapper;
	@Autowired
	private SystemDAO systemDAO;
	// 日志
	private static final Logger log = LogManager
			.getLogger(DistrictServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<District> getDescendants(String districtId) {
		List<District> list = new ArrayList<District>();
		try {
			findByRecursion(districtId, list, true);
			return list;
		} catch (Exception e) {
			log.error("组织机构查询失败：" + e.getMessage());
			throw new BusinessException("组织机构查询失败");
		}
	}

	public void findByRecursion(String districtId, List<District> disList,
			boolean haveTopDis) {
		if (StringUtils.isNotBlank(districtId)) {
			List<District> list = districtMapper.find(districtId,haveTopDis);
			disList.addAll(list);
			Iterator<District> it = list.iterator();
			while (it.hasNext()) {
				District tempOrg = it.next();
				if (!tempOrg.getChirdNum().equals(0)&&!districtId.equals(tempOrg.getDistrictCode())) {
					findByRecursion(tempOrg.getDistrictCode(), disList, false);
				}
			}
		}
	}

	@Override
	public List<District> getUsedXingzheng() {
		return districtMapper.getUsedXingzheng();
	}

	@Override
	public int getUsedXingzhengCount() {
		return districtMapper.getUsedXingzhengCount();
	}
	
	@Override
	@Transactional
	public List<District> districtTreeManage(Map<String, Object> map) {
		try {
			return districtMapper.districtTreeManage(map);
		} catch (Exception e) {
			log.error("行政区划树管理查询失败：" + e.getMessage());
			throw new BusinessException("行政区划树管理查询失败");
		}
	}

	@Override
	@Transactional
	public PaginationHelper<District> districtManage(District district, String page) {
		try {
			return systemDAO.find(district, page);
		} catch (Exception e) {
			log.error("行政区划查询失败：" + e.getMessage());
			throw new BusinessException("行政区划查询失败");
		}
	}

    @Override
    @Transactional
    public ServiceResponse initAuthority() {
        ServiceResponse res = new ServiceResponse(true,"初始化成功!");
        try{
            districtMapper.initAuthority();
        } catch (Exception e) {
            log.error("初始化失败：" + e.getMessage());
            throw new BusinessException("初始化失败");
        }
        return res;
    }

	@Override
	public List<District> findDistrictByParentId(String districtId) {
		try {
			return districtMapper.findDistrictByParentId(districtId);
		} catch (Exception e) {
			log.error("行政区划查询失败：" + e.getMessage());
			throw new BusinessException("行政区划查询失败");
		}
	}
	
	@Override
	@Transactional
	public List<District> queryByParentId(String districtCode) {
		Map<String, Object> map=new HashMap();
		map.put("districtCode", districtCode);
		try {
			return districtMapper.queryByParentId(map);
		} catch (Exception e) {
			log.error("行政区划树管理查询失败：" + e.getMessage());
			throw new BusinessException("行政区划树管理查询失败");
		}
	}

	@Override
	public District selectByPrimaryKey(String districtCode) {
		return districtMapper.selectByPrimaryKey(districtCode);
	}

	@Override
	@Transactional
	public List<District> districtTreeCommunion(Map<String, Object> map) {
		try {
			return districtMapper.districtTreeCommunion(map);
		} catch (Exception e) {
			log.error("行政区划树管理查询失败：" + e.getMessage());
			throw new BusinessException("行政区划树管理查询失败");
		}
	}
	/**
	 * 异步加载树
	 * 
	 * @author: LXL
	 * @createTime:2017年9月19日 下午2:09:32
	 */
	public List<District> loadChildAsync(District district) {
		return districtMapper.loadChildAsync(district);
	}

	/**
	 * 获取专项互动的组织区划列表
	 * @author: LXL
	 * @createTime:2017年10月18日 上午11:25:53
	 */
	@Override
	public List<District> getDistrictListForSpecialAct(Map<String, Object> paramMap) {
		return districtMapper.getDistrictListForSpecialAct(paramMap);
	}
	
}
