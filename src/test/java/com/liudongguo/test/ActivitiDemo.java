package com.liudongguo.test;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class ActivitiDemo {

    static String processDefinitionKey = "myEvection";
    static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    /**
     *  流程部署
     */
    @Test
    public void testDeployment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment()
                .name("出差申请流程")
                .addClasspathResource("bpmn/evection.bpmn")
                .addClasspathResource("bpmn/evection.png")
                .deploy();

        System.out.println("流程部署ID：" + deployment.getId());
        System.out.println("流程部署name：" + deployment.getName());
    }

    /**
     *  启动流程实例
     */
    @Test
    public void testStartProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程定义的ID：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例的ID：" + processInstance.getId());
        System.out.println("当前活动的ID：" + processInstance.getActivityId());
    }

    /**
     *  查询个人任务
     */
    @Test
    public void testQueryTask(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("张三")
                .list();

        for (Task task : taskList) {
            System.out.println("流程实例的ID：" + task.getProcessInstanceId());
            System.out.println("任务的ID：" + task.getId());
            System.out.println("任务的负责人：" + task.getAssignee());
            System.out.println("任务的名称：" + task.getName());
        }
    }

    /**
     *  完成个人任务
     */
    @Test
    public void testCompleteTask(){
        TaskService taskService = processEngine.getTaskService();
        //张三发起流程
        /*taskService.complete("2505");*/

        //经理审批
        /*Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("Jerry")
                .singleResult();*/

        //总经理审批
        /*Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("Jack")
                .singleResult();*/

        //财务审批
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("Rose")
                .singleResult();

        System.out.println("流程实例的ID：" + task.getProcessInstanceId());
        System.out.println("任务的ID：" + task.getId());
        System.out.println("任务的负责人：" + task.getAssignee());
        System.out.println("任务的名称：" + task.getName());

        taskService.complete(task.getId());
    }


    /**
     * 流程定义信息的查询
     */
    @Test
    public void testdefinition(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = definitionQuery.processDefinitionKey("myEvection")
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        for (ProcessDefinition definition : list) {
            System.out.println("流程定义的ID：" + definition.getId());
            System.out.println("流程定义的名称：" + definition.getName());
            System.out.println("流程定义的KEY：" + definition.getKey());
            System.out.println("流程定义的版本：" + definition.getVersion());
            System.out.println("流程定义的部署ID：" + definition.getDeploymentId());
        }
    }

    /**
     * 删除流程部署信息
     */
    @Test
    public void testDeleteDeployment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        repositoryService.deleteDeployment("1");
    }

}
