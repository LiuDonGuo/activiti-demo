package com.liudongguo.test;

import com.liudongguo.demo.pojo.Evection;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liudongguo
 * @title: TestGlobalVariable
 * @projectName activiti-demo
 * @description: TODO
 * @date 2021/5/13下午8:52
 */
public class TestGlobalVariable {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    String key = "evection-global";

    @Test
    public void testdeploument() {

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("出差申请-variable")
                .addClasspathResource("bpmn/evection-global.bpmn")
                .deploy();

        System.out.println("流程部署ID：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    @Test
    public void delete(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment("97501");

    }

    @Test
    public void startprocess() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Evection evection = new Evection();
        evection.setNum(4d);
        Map<String, Object> variables = new HashMap<>();
        variables.put("evection", evection);
        variables.put("assignee0","张三");
        variables.put("assignee1","部门经理");
        variables.put("assignee2","总经理");
        variables.put("assignee3","财务");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("evection-global", variables);
        System.out.println("流程实例ID：" + instance.getId());
    }

    @Test
    public void testTask(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().processInstanceId("112501").list();
        for (Task task : list) {
            System.out.println("----------------------------");
            System.out.println("流程实例id： " + task.getProcessInstanceId());
            System.out.println("任务id： " + task.getId());
            System.out.println("任务负责人： " + task.getAssignee());
            System.out.println("任务名称： " + task.getName());

            taskService.complete(task.getId());
        }
    }
}
