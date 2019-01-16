package com.ksource.liangfa.web.bean;

import java.util.List;

import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.Post;
import com.ksource.liangfa.domain.TaskAssign;

/**
 * 
 * @author gengzi
 *
 */
public class OrgTaskAssign {
	private Organise dept;
	private TaskAssign taskAssign;
	private List<Post> postList;
	public Organise getDept() {
		return dept;
	}
	public void setDept(Organise dept) {
		this.dept = dept;
	}
	public TaskAssign getTaskAssign() {
		return taskAssign;
	}
	public void setTaskAssign(TaskAssign taskAssign) {
		this.taskAssign = taskAssign;
	}
	public List<Post> getPostList() {
		return postList;
	}
	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}
}
