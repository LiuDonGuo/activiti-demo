package com.liudongguo.demo.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author liudongguo
 * @title: MyTaskListener
 * @projectName activiti-demo
 * @description: TODO
 * @date 2021/5/11下午9:32
 */
public class MyTaskListener implements TaskListener {
    /**
     * 指定负责人
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        if (delegateTask.getEventName().equals("create")) {
            if (delegateTask.getName().equals("创建申请")) {
                delegateTask.setAssignee("张三");
            }
            if (delegateTask.getName().equals("审核申请")) {
                delegateTask.setAssignee("李四");
            }
        }
    }
}
