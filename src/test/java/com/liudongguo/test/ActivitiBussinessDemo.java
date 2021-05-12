package com.liudongguo.test;

import cn.hutool.json.JSONUtil;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * @author liudongguo
 * @title: ActivitiBussinessDemo
 * @projectName activiti-demo
 * @description: TODO
 * @date 2021/5/11下午6:21
 */
public class ActivitiBussinessDemo {
    ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
    static String processDefinitionKey = "myEvection";


    /**
     * 创建流程实例时绑定业务主键
     */
    @Test
    public void addBussinessKey(){
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey, "1001");
        System.out.println(instance.getBusinessKey());

    }

    /**
     * 挂起、激活全部流程实例
     */
    @Test
    public void suspendAllProcessInstance(){
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();

        //通过流程定义查询所有的流程实例
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();
        System.out.println(JSONUtil.toJsonStr(processDefinition));

        boolean suspended = processDefinition.isSuspended();
        System.out.println("流程挂起状态：" + suspended);
        System.out.println("流程id：" + processDefinition.getId());
        if (suspended) {
            //如果是挂起状态
            repositoryService.activateProcessDefinitionById(processDefinition.getId(),true,null);
            System.out.println("流程id：" + processDefinition.getId() + "已激活");
        } else {
            //挂起当前流程定义
            repositoryService.suspendProcessDefinitionById(processDefinition.getId(),true,null);
            System.out.println("流程id：" + processDefinition.getId() + "已挂起");
        }
    }

    /**
     * 挂起单个流程实例
     */
    @Test
    public void suspendsingle(){
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();

        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("27501")
                .singleResult();

        boolean suspended = instance.isSuspended();

        if (suspended) {
            //激活
            runtimeService.activateProcessInstanceById(instance.getId());
            System.out.println("流程id：" + instance.getId() + "已激活");
        } else {
            //挂起
            runtimeService.suspendProcessInstanceById(instance.getId());
            System.out.println("流程id：" + instance.getId() + "已挂起");
        }
    }

    /**
     * 完成所有已激活任务
     */
    @Test
    public void testcomplete(){
        TaskService taskService = defaultProcessEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery()
                .active()
                .list();

        for (Task task : list) {
            taskService.complete(task.getId());
        }
    }

    /**
     * 测试完成挂起任务
     *
     * 执行结果：Cannot complete a suspended task
     */
    @Test
    public void testcomplete2(){
        TaskService taskService = defaultProcessEngine.getTaskService();

        Task task = taskService.createTaskQuery()
                .processInstanceId("27501")
                .taskAssignee("张三")
                .singleResult();
        System.out.println("流程实例id="+task.getProcessInstanceId());
        System.out.println("任务Id="+task.getId());
        System.out.println("任务负责人="+task.getAssignee());
        System.out.println("任务名称="+task.getName());
        if (task.isSuspended()) {
            RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
            runtimeService.activateProcessInstanceById(task.getProcessInstanceId());
        }
        taskService.complete(task.getId());
    }
}
