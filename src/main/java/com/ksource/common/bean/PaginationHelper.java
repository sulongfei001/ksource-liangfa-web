package com.ksource.common.bean;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import com.alibaba.fastjson.annotation.JSONField;

public class PaginationHelper<T> implements PaginatedList {
	
    private List<T> list;
    private int pageNumber = 1;
//    private int objectsPerPage = 10;
    private int objectsPerPage = Paging.perpage;
    private int fullListSize = 0;
    private String sortCriterion;
    private SortOrderEnum sortDirection;
    private String searchId;
    private int totalPageNum = 0;
    private boolean success;
    private String msg;
  
    public List<T> getList() {
        return list;   
    }
  
    public void setList(List<T> list) {
        this.list = list;   
    }   
  
    public int getPageNumber() {
        return pageNumber;   
    }   
  
    public void setPageNumber(int pageNumber) {   
        this.pageNumber = pageNumber;   
    }   
    
    public int getObjectsPerPage() {   
        return objectsPerPage;   
    }   
  
    public void setObjectsPerPage(int objectsPerPage) {   
        this.objectsPerPage = objectsPerPage;   
    }   
  
    public int getFullListSize() {   
        return fullListSize;   
    }   
  
    public void setFullListSize(int fullListSize) {   
        this.fullListSize = fullListSize;   
    }   
  
    public String getSortCriterion() {   
      return sortCriterion;   
    }   
  
    public void setSortCriterion(String fieldN) {   
        this.sortCriterion = (fieldN == null || "null".equals(fieldN)) ? "1"  
                : fieldN;   
    }   
  
  public void setSortDirection(String dir) {   
        if (dir == null || "null".equals(dir) || "asc".equalsIgnoreCase(dir)) {   
            sortDirection = SortOrderEnum.ASCENDING;   
        } else {   
            sortDirection = SortOrderEnum.DESCENDING;   
        }   
    }   
  
    public String getSortDirectionStr() {   
        if (sortDirection == SortOrderEnum.ASCENDING)   
            return "ASC";   
        else  
           return "DESC";   
    }   
  
    public SortOrderEnum getSortDirection() {   
        return sortDirection;   
    }   
  
    public void setSortDirection(SortOrderEnum sortDirection) {   
        this.sortDirection = sortDirection;   
    }   
 
    public String getSearchId() {   
       return searchId;   
    }   
 
   public void setSearchId(String searchId) {   
        this.searchId = searchId;   
   }   
   
   public int getStartNum(){
	   int start=0;
	   start = (pageNumber-1)*objectsPerPage;
	   return start;
   }
   
   public int getEndNum(){
	   int end=0;
	   end =  pageNumber*objectsPerPage;
	   return end;
   }

	public int getTotalPageNum() {
	    int totalPageNum = 0;
	    totalPageNum = (fullListSize  +  objectsPerPage - 1) / objectsPerPage;
	    return totalPageNum;
	}
	
	public void setTotalPageNum(int totalPageNum) {
	    this.totalPageNum = totalPageNum;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
   
}
