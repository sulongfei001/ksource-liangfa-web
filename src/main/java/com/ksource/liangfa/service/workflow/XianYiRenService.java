package com.ksource.liangfa.service.workflow;

import java.util.List;

import com.ksource.liangfa.domain.CaseXianyiren;

/**
 * 嫌疑人操作service
 * @author rengeng
 *
 */
public interface XianYiRenService {

	/**
	 * 新增嫌疑人，同时更新个人库、处理罪名信息（根据caseXianyiren domain的字段tiqingdaibuZm、tiqingqisuZm...来进行判断处理）
	 * @param caseXianyiren
	 */
	void addXianyiren(CaseXianyiren caseXianyiren);
	/**
	 * 修改嫌疑人信息，同时更新个人库、处理罪名信息（如果zmForDelete为nul，根据caseXianyiren domain的字段tiqingdaibuZm、tiqingqisuZm...来进行判断处理；
	 * 如果zmForDelete不为nul，删除该嫌疑人相应的罪名）
	 * @param caseXianyiren		嫌疑人对象信息
	 * @param updatePeopleLib		是否更新个人库
	 * @param zmForDelete		要删除的嫌疑人的罪名类型
	 */
	void updateXianyiren(CaseXianyiren caseXianyiren,boolean updatePeopleLib,Integer zmForDelete);
	
	/**
	 * 嫌疑人的后悔操作，如从提请起诉、提请逮捕、提起公诉列表..中移除。同时删除对应罪名信息
	 * @param xianyirenForUpdate	更新对象
	 * @param zmType	罪名类型
	 */
	void deleteXianyiren(CaseXianyiren xianyirenForUpdate,
			int zmType);
	
	/**
	 * 删除罪名
	 * @param xianyirenForUpdate	更新对象
	 * @param zmType	罪名类型
	 */
	void deleteZm(String caseId,Long xianyirenId,
			Integer zmType);
	/**
	 * 案件不起诉操作
	 */
	void buqisuAl(String caseId);
	
	/**
	 * 案件罪名
	 */
	void addCaseZm(String caseId,String[] yisonggonganZmArr,int caseZmType);

    /**
     * 更新嫌疑人判决结果
     * @param caseXianyiren
     * @param updatePeopleLib
     * @param zmForDelete
     */
    void updatePanjue(CaseXianyiren caseXianyiren,boolean updatePeopleLib,Integer zmForDelete);
	/**
	 * 根据ID查询
	 * @param xianyirenId
	 * @return
	 */
    CaseXianyiren selectById(Integer xianyirenId);
    /**
     * 根据条件查找嫌疑人
     * @param caseXianyiren
     * @return
     */
	List<CaseXianyiren> findAll(CaseXianyiren caseXianyiren);
	/**
	 * 更新嫌疑人因未提交而已经是起诉状态的人员和案件关系，并删除相应的罪名 
	 * @return:void
	 * @createTime:2017年11月7日 下午6:51:19
	 */
	void updateXianyirenStateByCaseId(String caseId,String caseIndex);
}
