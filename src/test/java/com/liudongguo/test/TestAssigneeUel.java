package com.liudongguo.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.util.HashMap;
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
}
