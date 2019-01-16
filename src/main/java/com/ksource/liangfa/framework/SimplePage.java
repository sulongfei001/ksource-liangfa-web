package com.ksource.liangfa.framework;

/**
 * 简单分页类
 * 
 * @author junxy
 * 
 */
public class SimplePage implements Paginable {
	private static final long serialVersionUID = 1L;
	public static final int DEF_COUNT = 10;

	/**
	 * 检查页码 checkPageNo
	 * 
	 * @param pageNo
	 * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
	 */
	public static Integer checkPageNo(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}

	public SimplePage() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public SimplePage(Integer pageNo, Integer pageSize, Integer totalCount) {
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
	}
	
	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 */
	public SimplePage(Integer pageNo) {
		setPageSize(pageSize);
		setPageNo(pageNo);
	}

	/**
	 * 调整页码，使不超过最大页数
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		Integer tp = getTotalPage();
		if (pageNo > tp) {
			pageNo = tp;
		}
	}

	/**
	 * 获得页码
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * 每页几条数据
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 总共几条数据
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * 总共几页
	 */
	public Integer getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	/**
	 * 是否第一页
	 */
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/**
	 * 是否最后一页
	 */
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	/**
	 * 下一页页码
	 */
	public Integer getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/**
	 * 上一页页码
	 */
	public Integer getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	protected Integer totalCount = 0;
	protected Integer pageSize = 10;
	protected Integer pageNo = 1;

	/**
	 * if totalCount<0 then totalCount=0
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
	}

	/**
	 * if pageSize< 1 then pageSize=DEF_COUNT
	 * 
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
	}

	/**
	 * if pageNo < 1 then pageNo=1
	 * 
	 * @param pageNo
	 */
	public void setPageNo(Integer pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}
}
