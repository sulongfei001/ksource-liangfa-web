package com.ksource.liangfa.service.office;

import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.OfficeIdentity;

public interface OfficeIdentityService {

	PaginationHelper<OfficeIdentity> find(OfficeIdentity identity, String page,
			Map<String,Object> params);

	void add(OfficeIdentity identity);

	Integer checkAliasExisted(String alias, Integer identityId);

	int delete(Integer identityId);

	OfficeIdentity selectById(Integer identityId);

	void update(OfficeIdentity identity);

	String nextId(String alias);

}
