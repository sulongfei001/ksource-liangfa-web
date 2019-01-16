package com.ksource.common.task;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TaskProcessor implements Runnable{

	private  List<Execution> exes;
	private DelegateTask task;
	private static final Logger log = LogManager.getLogger(TaskProcessor.class);
	public boolean exe(DelegateTask task) {
		this.task= task;
		Thread newThread = new Thread(this,"new Thread ");
		newThread.start();
		return true;
	}
	public  List<Execution> getExes() {
		return exes;
	}
	public  void setExes(List<Execution> exes) {
		this.exes = exes;
	}
	@Override
	public void run()
	{
		try
		{ 
			Collections.sort(exes,new Comparator<Execution>() {
				@Override
				public int compare(Execution exe1, Execution exe2) {
					return exe1.getOrder()-exe2.getOrder();
				}
			});
			log.info("-----开始执行任务运行器-----");
			Iterator<Execution> ite = exes.iterator();
			while(ite.hasNext()){
				Execution execution = ite.next();
				log.info("执行:"+execution.getClass().getSimpleName()+" 顺序:"+execution.getOrder());
				execution.exe(task);
			}
			log.info("-----结束执行任务运行器-----");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("-----任务运行器执行失败-----");
		}
	}
}
