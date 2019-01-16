package com.ksource.liangfa.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ProcDeploy;
import com.ksource.liangfa.domain.ProcDeployExample;
import com.ksource.liangfa.domain.ProcDeployWithBLOBs;
import com.ksource.liangfa.domain.ProcDiagram;
import com.ksource.liangfa.domain.ProcDiagramExample;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.ProcSequence;
import com.ksource.liangfa.domain.ProcSequenceExample;
import com.ksource.liangfa.domain.ProcSequencePoint;
import com.ksource.liangfa.domain.ProcSequencePointExample;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskActionExample;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindExample;
import com.ksource.liangfa.mapper.ProcDeployMapper;
import com.ksource.liangfa.mapper.ProcDiagramMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.mapper.ProcSequenceMapper;
import com.ksource.liangfa.mapper.ProcSequencePointMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.syscontext.Const;

@Service("procDeployManagerService")
public class ProcDeployManagerService {

	@Autowired
	SystemDAO systemDAO;
	@Autowired
	ProcDiagramMapper procDiagramMapper;
	@Autowired
	ProcKeyMapper procKeyMapper;
	@Autowired
	ProcDeployMapper procDeployMapper;
	@Autowired
	TaskBindMapper taskBindMapper;
	@Autowired
	TaskActionMapper taskActionMapper;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	TaskService taskService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	ProcSequenceMapper sequenceMapper;
	@Autowired
	ProcSequencePointMapper procSequencePointMapper;
	
	
	/**
	 * 查询流程部署历史
	 * @param procDefKey	流程类型(key)
	 * @param deployState	部署状态
	 * @param taskFormState	表单绑定状态
	 * @param page TODO
	 * @return
	 */
	@Transactional(readOnly=true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PaginationHelper<ProcDeploy> queryProcDeploy(String procDefKey,Integer deployState,Integer taskFormState, String page){
		Map param = new HashMap(3);
		param.put("procDefKey", procDefKey);
		param.put("deployState", deployState);
		param.put("taskFormState", taskFormState);
		PaginationHelper<ProcDeploy> help= systemDAO.find(new ProcDeploy(),page, param);
		return help;
	}
	
	
	/**
	 * 流程上传
	 * @param bpmnFile	流程定义xml文件
	 * @param pictFile	流程图
	 * @param procKey	流程key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ServiceResponse uploadProc(MultipartFile bpmnFile,MultipartFile pictFile,String procKey){
		ServiceResponse response = new ServiceResponse(true,"流程上传成功");
		
		//----------验证开始
		
		//验证如果还有未部署的，那么不能上传
		ProcDeployExample example = new ProcDeployExample();
		example.createCriteria().andProcDefKeyEqualTo(procKey).andDeployStateEqualTo(Const.STATE_INVALID);
		int count = procDeployMapper.countByExample(example);
		if(count>0){
			response.setingError("此流程key还有未部署的流程定义，请先删除或部署！");
			return response;
		}
		
		SAXReader reader=new SAXReader();
		Document document=null;
		try {
			document=reader.read(bpmnFile.getInputStream());
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        //判断流程设计器(1.activiti designer 2.耀强)
		Map<String, String> map=new HashMap<String, String>(3);
		//把默认名称空间也加在程序里一个前缀方便在xpath中用前缀确定Element
		//默认名称空间在程序中加入前缀就可以用xpath表达式了
		/*map.put("bpmndi","http://www.omg.org/spec/BPMN/20100524/DI");
		map.put("omgdc", "http://www.omg.org/spec/DD/20100524/DC");
		map.put("omgdi", "http://www.omg.org/spec/DD/20100524/DI");
		map.put("bpmnModel", "http://www.omg.org/spec/BPMN/20100524/MODEL");*/
         XPath xpathSelector= DocumentHelper.createXPath("definitions");
		xpathSelector.setNamespaceURIs(map);
		List<Element> definitionsElementList = xpathSelector.selectNodes(document);
		Assert.notEmpty(definitionsElementList);
		DefaultElement definitionsElement = (DefaultElement)definitionsElementList.get(0);
        List spaces= definitionsElement.content();
        Namespace namespace=null;
        String prefix=null;
        for(Object obj:spaces){
            if(obj instanceof Namespace){
                namespace= (Namespace)obj;
                prefix=namespace.getPrefix();
                if(prefix.equals("")){
                    prefix="bpmnModel";
                }
                map.put(prefix,namespace.getURI());
            }
        }
        String pic_bound = "omgdc:Bounds";
        String pic_point = "omgdi:waypoint";
        if(map.get("omgdc")==null){
           pic_bound = "dc:Bounds";
           pic_point = "di:waypoint";
        }

		//1验证procKey
		xpathSelector=DocumentHelper.createXPath("//bpmnModel:process");
		xpathSelector.setNamespaceURIs(map);
		List<Element> elementList = xpathSelector.selectNodes(document);
		Assert.notEmpty(elementList);
		Element processElement = elementList.get(0);
        Namespace space= processElement.getNamespaceForPrefix("xmlns");
        Namespace pointNameSpace =space.get("http://www.omg.org/spec/DD/20100524/DC");
        pointNameSpace.getName();
		String procDefXmlId = processElement.attributeValue("id");
		String procDefXmlName = processElement.attributeValue("name","");
		if(!procDefXmlId.equals(procKey)){
			response.setingError("流程定义id与所选流程key不一致！");
			return response;
		}
		//----------验证结束
		//获得startEvent
		xpathSelector = DocumentHelper.createXPath("//bpmnModel:startEvent");
		xpathSelector.setNamespaceURIs(map);
		List<Element> tempElement = xpathSelector.selectNodes(document);
		Element startEventElement = tempElement.get(0);
		String startEventId=startEventElement.attributeValue("id");
		xpathSelector = DocumentHelper.createXPath("//bpmnModel:sequenceFlow[@sourceRef='"+startEventId+"']");
		xpathSelector.setNamespaceURIs(map);
		Element tempFlow =  (Element) xpathSelector.selectNodes(document).get(0);
		String firstTaskDefId = tempFlow.attributeValue("targetRef");
		
		//得到所有user task节点的id
		xpathSelector=DocumentHelper.createXPath("//bpmnModel:userTask");
		xpathSelector.setNamespaceURIs(map);
		List<Element> userTaskElementList = xpathSelector.selectNodes(document);
		Assert.notEmpty(userTaskElementList);
		
		//新增解析userTasksequenceFlowElementList和判定表达式并生成【任务按钮】
		//得到部署ID序列值
		Integer procDeployId = systemDAO.getSeqNextVal(Const.TABLE_PROC_DEPLOY);
		
		List<ProcDiagram> diagramListForInsert = new ArrayList<ProcDiagram>();//待保存设计图信息列表
		List<TaskAction> taskActionListForInsert = new ArrayList<TaskAction>();//待保存任务动作列表
		for(Element taskElement : userTaskElementList){
			String userTaskId = taskElement.attributeValue("id");
			String userTaskName = taskElement.attributeValue("name",userTaskId);
			//1设计图信息获取

            xpathSelector = DocumentHelper.createXPath("//bpmndi:BPMNDiagram/bpmndi:BPMNPlane/bpmndi:BPMNShape[@bpmnElement='"+userTaskId+ "']/"+ pic_bound);
			xpathSelector.setNamespaceURIs(map);
			List<Element> userTaskDiagramElementList = xpathSelector.selectNodes(document);
			Element userTaskDiagram = userTaskDiagramElementList.get(0);
			String height=userTaskDiagram.attributeValue("height");
			String width=userTaskDiagram.attributeValue("width");
			String x=userTaskDiagram.attributeValue("x");
			String y=userTaskDiagram.attributeValue("y");
			
			ProcDiagram  diagram = new ProcDiagram();
			diagram.setElementId(userTaskId);
			diagram.setElementName(userTaskName);
			diagram.setProcDefId(procDeployId.toString());//部署时需要回填实际工作流定义ID
			diagram.setHeight(new BigDecimal(height));
			diagram.setWidth(new BigDecimal(width));
			diagram.setPointX(new BigDecimal(x));
			diagram.setPointY(new BigDecimal(y));
			diagramListForInsert.add(diagram);
			//2解析唯一网关（exclusiveGateway）或任务流出生成默认提交动作
			xpathSelector = DocumentHelper.createXPath("//bpmnModel:sequenceFlow[@sourceRef='"+userTaskId+"']");
			xpathSelector.setNamespaceURIs(map);
			List<Object> list = xpathSelector.selectNodes(document);
			if(list.size() > 0){
				Element taskOutFlow =  (Element) xpathSelector.selectNodes(document).get(0);
				String mayBeExclusiveGatewayId = taskOutFlow.attributeValue("targetRef");
				xpathSelector = DocumentHelper.createXPath("//bpmnModel:exclusiveGateway[@id='"+mayBeExclusiveGatewayId+"']");
				xpathSelector.setNamespaceURIs(map);
				if(xpathSelector.selectNodes(document).size()>0){//任务流向到唯一网关
					//解析唯一网关流出、生成默认的TaskAction
					xpathSelector = DocumentHelper.createXPath("//bpmnModel:sequenceFlow[@sourceRef='"+mayBeExclusiveGatewayId+"']");
					xpathSelector.setNamespaceURIs(map);
					List<Element> taskActionFlowList = xpathSelector.selectNodes(document);
					for(Element taskActionFlow : taskActionFlowList){
						String actionName=taskActionFlow.attributeValue("name");
						if(StringUtils.isBlank(actionName)){
							actionName = "未定义";
						}
						//targetUserTask
						String targetUserTaskId =taskActionFlow.attributeValue("targetRef");
						xpathSelector = DocumentHelper.createXPath("//bpmnModel:userTask[@id='"+targetUserTaskId+"']");
						xpathSelector.setNamespaceURIs(map);
						if(xpathSelector.selectNodes(document).size()==0){//如果不是流向任务，而是其他（如endEvent）
							targetUserTaskId=null;
						}
						
						//解析表达式：${action==1}的形式
						xpathSelector = DocumentHelper.createXPath("//bpmnModel:sequenceFlow[@id='"+taskActionFlow.attributeValue("id")+"']/bpmnModel:conditionExpression");
						xpathSelector.setNamespaceURIs(map);
						Element conditionExpressionElement =  (Element) xpathSelector.selectNodes(document).get(0);
						String conditionExpression = conditionExpressionElement.getText();
						conditionExpression = conditionExpression.replace("${", "").replace("}", "").trim();
						String sp[] = conditionExpression.split("==");
						String procVarName = sp[0].trim();
						String procVarVal  =sp[1].trim();
						TaskAction taskAction = new TaskAction();
						taskAction.setTaskDefId(userTaskId);
						taskAction.setTargetTaskDefId(targetUserTaskId);
						taskAction.setProcDefId(procDeployId.toString());//默认是部署id，真正部署流程时需要回填实际工作流定义ID
						taskAction.setActionName(actionName);
						taskAction.setTaskCaseState("-1212123");//默认给个无效值
						taskAction.setFormDefId(-1212123);//默认给个无效值
						taskAction.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
						taskAction.setProcVarName(procVarName);
						taskAction.setProcVarDataType(Const.INPUT_DATA_TYPE_INT);
						taskAction.setProcVarValue(procVarVal);
						taskAction.setState(Const.STATE_INVALID);//设置绑定完成后有效。
						taskActionListForInsert.add(taskAction);
					}
				}else{//任务流出(目前流程图解析只支持设计为任务到任务或任务到唯一网关)
					String targetUserTaskId = taskOutFlow.attributeValue("targetRef");
					xpathSelector = DocumentHelper.createXPath("//bpmnModel:userTask[@id='"+targetUserTaskId+"']");
					xpathSelector.setNamespaceURIs(map);
					if(xpathSelector.selectNodes(document).size()==0){//如果不是流向任务，而是其他（如endEvent）
						targetUserTaskId=null;
					}
					
					TaskAction taskAction = new TaskAction();
					taskAction.setTaskDefId(userTaskId);
					taskAction.setTargetTaskDefId(targetUserTaskId);
					taskAction.setProcDefId(procDeployId.toString());//默认是部署id，真正部署流程时需要回填实际工作流定义ID
					taskAction.setActionName(userTaskName);
					taskAction.setTaskCaseState("-1212123");//默认给个无效值
					taskAction.setFormDefId(-1212123);//默认给个无效值
					taskAction.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
					taskAction.setState(Const.STATE_INVALID);//设置绑定完成后有效。
					taskActionListForInsert.add(taskAction);
				}
			}
		}
		//获取所有得sequenceflow
		xpathSelector=DocumentHelper.createXPath("//bpmnModel:sequenceFlow");
		xpathSelector.setNamespaceURIs(map);
		List<Element> sequenceflowList = xpathSelector.selectNodes(document);
		Assert.notEmpty(sequenceflowList);
		ProcSequenceExample procSequenceExample = new ProcSequenceExample();
		procSequenceExample.createCriteria().andProcKeyEqualTo(procKey);
		sequenceMapper.deleteByExample(procSequenceExample);
		ProcSequencePointExample sequencePointExample = new ProcSequencePointExample();
		sequencePointExample.createCriteria().andProcKeyEqualTo(procKey);
		procSequencePointMapper.deleteByExample(sequencePointExample);
		for(Element sequenceflow : sequenceflowList){
			String flowId=sequenceflow.attributeValue("id");
			String flowName=sequenceflow.attributeValue("name");
			String sourceRef=sequenceflow.attributeValue("sourceRef");
			String targetRef=sequenceflow.attributeValue("targetRef");
			ProcSequence procSequence = new ProcSequence();
			procSequence.setProcKey(procKey);
			procSequence.setFlowId(flowId);
			procSequence.setFlowName(flowName);
			procSequence.setSourceRef(sourceRef);
			procSequence.setTargetRef(targetRef);
			sequenceMapper.insert(procSequence);
			xpathSelector = DocumentHelper.createXPath("//bpmndi:BPMNDiagram/bpmndi:BPMNPlane/bpmndi:BPMNEdge[@bpmnElement='"+flowId+ "']/omgdi:waypoint");
			xpathSelector.setNamespaceURIs(map);
			List<Element> flowCoordsElementList = xpathSelector.selectNodes(document);
			for(int i = 0 ;i < flowCoordsElementList.size(); i++){
				ProcSequencePoint procSequencePoint = new ProcSequencePoint();
				procSequencePoint.setPointId(Long.parseLong(systemDAO.getSeqNextVal(Const.TABLE_PROC_SEQUENCE_POINT)+""));
				procSequencePoint.setProcKey(procKey);
				procSequencePoint.setFlowId(flowId);
				procSequencePoint.setPointX(flowCoordsElementList.get(i).attributeValue("x"));
				procSequencePoint.setPointY(flowCoordsElementList.get(i).attributeValue("y"));
				procSequencePoint.setPointOrder(i);
				procSequencePointMapper.insert(procSequencePoint);
			}
		}
		
		BigDecimal minX = BigDecimal.ZERO, minY = BigDecimal.ZERO;
		if(map.get("omgdc")==null){
			minX = BigDecimal.valueOf(999999);
			minY = BigDecimal.valueOf(999999);
			//！！！！！！特殊处xml的设计信息（取决于设计器，yaoqiang bpmn editor）！！！！
            xpathSelector = DocumentHelper.createXPath("//bpmndi:BPMNDiagram/bpmndi:BPMNPlane/bpmndi:BPMNShape/" + pic_bound);
            xpathSelector.setNamespaceURIs(map);
            List<Element> diagramElementList = xpathSelector.selectNodes(document);
            for (Element element : diagramElementList) {
                BigDecimal x=new BigDecimal(element.attributeValue("x"));
                BigDecimal y=new BigDecimal(element.attributeValue("y"));
                if(minX.compareTo(x)>0){
                    minX = x;
                }
                if(minY.compareTo(y)>0){
                    minY = y;
                }
            }

            xpathSelector = DocumentHelper.createXPath("//bpmndi:BPMNDiagram/bpmndi:BPMNPlane/bpmndi:BPMNEdge/" + pic_point);
            xpathSelector.setNamespaceURIs(map);
            diagramElementList = xpathSelector.selectNodes(document);
            for (Element element : diagramElementList) {
                BigDecimal x=new BigDecimal(element.attributeValue("x"));
                BigDecimal y=new BigDecimal(element.attributeValue("y"));
                if(minX.compareTo(x)>0){
                    minX = x;
                }
                if(minY.compareTo(y)>0){
                    minY = y;
                }
            }
			//minX = minX.add(new BigDecimal(2));
			//minY = minX.add(new BigDecimal(18));asdadasdasdadadadasd
			/*
			for(ProcDiagram  diagram: diagramListForInsert){
				if(minX.compareTo(diagram.getPointX())>0){
					minX = diagram.getPointX();
				}
				if(minY.compareTo(diagram.getPointY())>0){
					minY = diagram.getPointY();
				}
			}
			//minX = minX.add(new BigDecimal(160));
			//minY = minX.add(new BigDecimal(60));
			 */
		}
		//保存流程图设计(定位)信息
		for(ProcDiagram  diagram: diagramListForInsert){
			diagram.setPointX(diagram.getPointX().subtract(minX));
			diagram.setPointY(diagram.getPointY().subtract(minY));
			procDiagramMapper.insert(diagram);
		}
		//保存默认生成的taskAction
		for(TaskAction  taskAction: taskActionListForInsert){
			taskAction.setActionId(systemDAO.getSeqNextVal(Const.TABLE_TASK_ACTION));
			taskActionMapper.insert(taskAction);
		}
		//保存流程文件和图片
		ProcDeployWithBLOBs procDeploy = new ProcDeployWithBLOBs();
		
		procDeploy.setProcDeployId(procDeployId);
		procDeploy.setProcDefId(procDeployId.toString());//部署时需要回填实际工作流定义ID
        procDeploy.setBpmnFileName("deploy."+procKey+".bpmn20.xml"); //由于activiti只识别deploy.chufaProc.bpmn20.xml这样的文件名，所以此处动态修改一下名字
		try {
			procDeploy.setBpmnFile(bpmnFile.getBytes());
			procDeploy.setPictFile(pictFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		procDeploy.setDeployState(Const.STATE_INVALID);
		procDeploy.setTaskFormState(Const.STATE_INVALID);
		procDeploy.setPictFileName(pictFile.getOriginalFilename());
		
		procDeploy.setProcDefKey(procDefXmlId);
		procDeploy.setProcDefName(procDefXmlName);
		procDeploy.setUploadDate(new Date());
		procDeploy.setVersion(-1);//部署后回填
		procDeploy.setFirstTaskDefId(firstTaskDefId);
		procDeployMapper.insert(procDeploy);
		
		//更新流程key的使用状态
		ProcKey procKeyDomain = new ProcKey();
		procKeyDomain.setProcKey(procKey);
		procKeyDomain.setUploadState(Const.STATE_VALID);
		procKeyMapper.updateByPrimaryKeySelective(procKeyDomain);
		
		return response;
	}
	
	/**
	 * 部署流程
	 * @param procDefId
	 */
	@Transactional
	public ServiceResponse deployProc(String procDefId){
		ServiceResponse serviceResponse=new ServiceResponse(true, "部署成功！");
		ProcDeployWithBLOBs procDeploy = procDeployMapper.selectByPrimaryKey(procDefId);
		if(procDeploy.getDeployState()==Const.STATE_VALID){//已部署
			return serviceResponse;
		}
		if(procDeploy.getTaskFormState()==Const.STATE_INVALID){//
			serviceResponse.setingError("还未完成表单绑定，不能部署！");
			return serviceResponse;
		}
		
		String procDefKey = procDeploy.getProcDefKey();
		
		ByteArrayInputStream bpmnInputStream = new ByteArrayInputStream(procDeploy.getBpmnFile());
		ByteArrayInputStream pictInputStream = new ByteArrayInputStream(procDeploy.getPictFile());
		
		String deployId =  repositoryService.createDeployment().addInputStream(procDeploy.getBpmnFileName(), bpmnInputStream)
											.addInputStream(procDeploy.getPictFileName(), pictInputStream)
											.deploy().getId();
		
		ProcessDefinition processDefinition =  repositoryService.createProcessDefinitionQuery().
							processDefinitionKey(procDefKey).latestVersion().singleResult();
		String latestProcDefId=processDefinition.getId();
		int latestVersion = processDefinition.getVersion();
		//更新(回填)流程部署定义信息
		procDeployMapper.updateProcDefIdAndVersion(procDefId, deployId, latestProcDefId, latestVersion, new Date());
		//更新(回填)流程图信息
		ProcDiagram procDiagram = new ProcDiagram();
		procDiagram.setProcDefId(latestProcDefId);
		ProcDiagramExample procDiagramExample = new ProcDiagramExample();
		procDiagramExample.createCriteria().andProcDefIdEqualTo(procDefId);
		procDiagramMapper.updateByExampleSelective(procDiagram, procDiagramExample);
		//更新(回填)任务表单的流程定义ID
		TaskBind taskBind = new TaskBind();
		taskBind.setProcDefId(latestProcDefId);
		TaskBindExample taskBindExample =new TaskBindExample();
		taskBindExample.createCriteria().andProcDefIdEqualTo(procDefId);
		taskBindMapper.updateByExampleSelective(taskBind, taskBindExample);
		//更新(回填)任务动作的流程定义ID
		TaskAction taskAction = new TaskAction();
		taskAction.setProcDefId(latestProcDefId);
		TaskActionExample taskActionExample =new TaskActionExample();
		taskActionExample.createCriteria().andProcDefIdEqualTo(procDefId);
		taskActionMapper.updateByExampleSelective(taskAction, taskActionExample);
		
		//更新(回填)流程key的当前版本和当前定义ID
		ProcKey procKey = new ProcKey();
		procKey.setProcKey(procDefKey);
		procKey.setCurVersion(latestVersion);
		procKey.setCurProcDefId(latestProcDefId);
		procKey.setDeployState(Const.STATE_VALID);
		procKeyMapper.updateByPrimaryKeySelective(procKey);
		return serviceResponse;
		
	}
		
	/**
	 * 删除流程部署（流程定义）
	 * @param procDefId
	 * @return
	 */
	@Transactional
	public ServiceResponse deleteDeploy(String procDefId) {
		ServiceResponse serviceResponse=new ServiceResponse(true, "删除成功！");
		ProcDeployWithBLOBs procDeploy = procDeployMapper.selectByPrimaryKey(procDefId);
		String procDefKey = procDeploy.getProcDefKey();
		//删除activiti部署流程
		try{
		//	repositoryService.deleteDeployment(procDeploy.getProcDeployId().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		//删除流程图坐标信息
		ProcDiagramExample diagramExample = new ProcDiagramExample();
		diagramExample.createCriteria().andProcDefIdEqualTo(procDefKey);
		procDiagramMapper.deleteByExample(diagramExample);

		//删除流程部署
		procDeployMapper.deleteByPrimaryKey(procDefId);
		//删除任务表单绑定
		TaskBindExample  example= new TaskBindExample();
		example.createCriteria().andProcDefIdEqualTo(procDefId);
		taskBindMapper.deleteByExample(example);
		//删除任务动作
		TaskActionExample taskActionExample = new TaskActionExample();
		taskActionExample.createCriteria().andProcDefIdEqualTo(procDefId);
		taskActionMapper.deleteByExample(taskActionExample);
		return serviceResponse;
	}
	
	/**
	 * 任务表单绑定
	 * @param taskFormBindList 任务表单绑定集合
	 */
	@Transactional
	public ServiceResponse bindTaskForm(List<TaskBind> taskFormBindList){
		Assert.notEmpty(taskFormBindList);
		ServiceResponse serviceResponse=new ServiceResponse(true, "任务表单绑定成功！");
		
		String procDefId = taskFormBindList.get(0).getProcDefId();
		//FIXME 验证是否已修改
		/*ProcDeployExample procDeployExample = new ProcDeployExample();
		procDeployExample.createCriteria().andProcDefIdEqualTo(procDefId);
		List<ProcDeploy> procDeploys =  procDeployMapper.selectByExample(procDeployExample);
		Assert.notEmpty(procDeploys);
		ProcDeploy procDeploy = procDeploys.get(0);
		if(Const.STATE_VALID==procDeploy.getDeployState()){//已经部署
			serviceResponse.setingError("已经部署、不允许再修改任务表单绑定关系！");
		}*/
		//先删除再添加
		TaskBindExample example = new TaskBindExample();
		example.createCriteria().andProcDefIdEqualTo(procDefId);
		taskBindMapper.deleteByExample(example);
		for(TaskBind taskFormDef : taskFormBindList){
			taskBindMapper.insert(taskFormDef);
		}
		//更新绑定状态
		ProcDeployWithBLOBs procDeployForUpdate = new ProcDeployWithBLOBs();
		procDeployForUpdate.setProcDefId(procDefId);
		procDeployForUpdate.setTaskFormState(Const.STATE_VALID);
		procDeployMapper.updateByPrimaryKeySelective(procDeployForUpdate);
		
		return serviceResponse;
	}
	
	/**
	 * 任务提交动作绑定
	 * @param taskActionList 任务动作集合
	 * @return
	 */
	@Transactional
	public ServiceResponse bindTaskAction(List<TaskAction> taskActionList){
		Assert.notEmpty(taskActionList);
		ServiceResponse serviceResponse=new ServiceResponse(true, "任务提交动作绑定成功！");
		//FIXME 验证是否已修改
		String procDefId = taskActionList.get(0).getProcDefId();
		/*ProcDeployExample procDeployExample = new ProcDeployExample();
		procDeployExample.createCriteria().andProcDefIdEqualTo(procDefId);
		List<ProcDeploy> procDeploys =  procDeployMapper.selectByExample(procDeployExample);
		Assert.notEmpty(procDeploys);
		ProcDeploy procDeploy = procDeploys.get(0);
		if(Const.STATE_VALID==procDeploy.getDeployState()){//已经部署
			serviceResponse.setingError("已经部署、不允许再修改任务表单绑定关系！");
		}*/
		//先更新，后删除无效的
        List<Integer> temp= new ArrayList<Integer>();
		for(TaskAction taskAction : taskActionList){
            if(taskAction.getCaseInd()==null){
               temp.add(taskAction.getActionId());
            }
			taskAction.setState(Const.STATE_VALID);
			taskActionMapper.updateByPrimaryKeySelective(taskAction);
		}
        TaskActionExample taskActionExample = new TaskActionExample();
        if(temp.size()!=0){
           taskActionExample.createCriteria().andActionIdIn(temp);
           for(TaskAction taskAction:taskActionMapper.selectByExample(taskActionExample)){
                    if(taskAction.getCaseInd()!=null){
                         taskAction.setCaseInd(null);
                         taskAction.setCaseIndVal(null);
                         taskActionMapper.updateByPrimaryKey(taskAction);
                    }
           }
           taskActionExample.clear();
        }

		taskActionExample.createCriteria().andProcDefIdEqualTo(procDefId).andStateEqualTo(Const.STATE_INVALID);
		taskActionMapper.deleteByExample(taskActionExample);
		return serviceResponse;
	}
}
