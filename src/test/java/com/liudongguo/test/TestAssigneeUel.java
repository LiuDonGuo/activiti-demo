package com.liudongguo.test;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liudongguo
 * @title: TestAssigneeUel
 * @projectName activiti-demo
 * @description: TODO
 * @date 2021/5/11下午9:01
 */
public class TestAssigneeUel {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     *  流程部署
     */
    @Test
    public void testDeployment(){
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment()
                .name("流程监听器")
                .addClasspathResource("bpmn/demo-listen.bpmn")
                //.addClasspathResource("bpmn/evection-uel.png")
                .deploy();

        System.out.println("流程部署ID：" + deployment.getId());
        System.out.println("流程部署name：" + deployment.getName());


    }

    @Test
    public void startAssigneeUel(){
        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> map = new HashMap<>();
        map.put("assignee0","张三");
        map.put("assignee1","李经理");
        map.put("assignee2","王总经理");
        map.put("assignee3","小赵财务");

        runtimeService.startProcessInstanceByKey("myEvection-uel",map);
    }

    @Test
    public void startListener(){
        RuntimeService runtimeService = processEngine.getRuntimeService();

        runtimeService.startProcessInstanceByKey("testListener");
    }
    static String processDefinitionKey = "myEvection-uel";

    @Test
    public void testtask(){

        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("张三")
                .list();

        for (Task task : list) {
            System.out.println("----------------------------");
            System.out.println("流程实例id： " + task.getProcessInstanceId());
            System.out.println("任务id： " + task.getId());
            System.out.println("任务负责人： " + task.getAssignee());
            System.out.println("任务名称： " + task.getName());
        }
    }
}
