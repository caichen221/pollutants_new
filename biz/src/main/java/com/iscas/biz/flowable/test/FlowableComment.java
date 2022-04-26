package com.iscas.biz.flowable.test;

/**
 * 1、设计流程定义。编写bpmn20.xml格式的文件，可以通过流程设计器来设计(后面让前端来集成一下流程设计器)
 * 通过FlowDefinitionController类中接口/flowable/definition/import?name=xxx&category=xxx，导入一个流程(XML文件)
 *
 * 2、查询流程定义列表。FlowDefinitionController类中接口：/flowable/definition/list，返回表格结构
 *
 * 3、修改流程定义。通过FlowDefinitionController类中接口/flowable/definition/save可以修改第一步设计的流程
 *
 * 4、查询流程定义的图片。FlowDefinitionController类中接口/flowable/definition/readImage/{deployId}，deployId可以在第2步返回的列表中获取到
 *
 * 5、挂起/激活流程定义。FlowDefinitionController类中接口/flowable/definition/updateState
 *
 * 6、删除流程定义。FlowDefinitionController类中接口/flowable/definition/delete
 *
 * 7、启动一个流程。FlowDefinitionController类中接口/flowable/definition/start/{procDefId}，变量可以自己定义
 *
 * 8、查询我发起的流程实例列表。FlowTaskController类中接口/flowable/task/myProcess
 *
 * 9、查询我的待办。FlowTaskController类中接口/flowable/task/myProcess/todoList
 *
 * 10、查询已办。FlowTaskController类中接口/flowable/task/myProcess/finishedList
 *
 * 11、审批任务。 FlowTaskController类中接口/flowable/task/myProcess/complete
 *
 * 12、获取历史流转记录。FlowTaskController类中接口/flowable/task/myProcess/flowRecord
 *
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/21 11:08
 * @since jdk11
 */
public class FlowableComment {
}
