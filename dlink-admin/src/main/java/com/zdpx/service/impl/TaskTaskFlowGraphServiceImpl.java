package com.zdpx.service.impl;

import com.dlink.db.service.impl.SuperServiceImpl;
import com.dlink.model.Task;
import com.dlink.service.TaskService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdpx.coder.SceneCodeBuilder;
import com.zdpx.coder.json.SceneNode;
import com.zdpx.mapper.FlowGraphScriptMapper;
import com.zdpx.model.FlowGraph;
import com.zdpx.service.TaskFlowGraphService;

/**
 *
 */
@Service
public class TaskTaskFlowGraphServiceImpl extends SuperServiceImpl<FlowGraphScriptMapper, FlowGraph> implements TaskFlowGraphService {

    private final TaskService taskService;

    public TaskTaskFlowGraphServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public boolean insert(FlowGraph statement) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateTask(Task task) {
        String flowGraphScript = task.getStatement();
        String sql = convertConfigToSource(flowGraphScript);
        task.setStatement(sql);
        taskService.saveOrUpdateTask(task);

        FlowGraph flowGraph = new FlowGraph();
        flowGraph.setTaskId(task.getId());
        flowGraph.setScript(flowGraphScript);
        this.saveOrUpdate(flowGraph);
        return true;
    }

    private String convertConfigToSource(String flowGraphScript) {
        SceneNode scene = SceneCodeBuilder.readScene(flowGraphScript);
        var sceneInternal = SceneCodeBuilder.convertToInternal(scene);
        SceneCodeBuilder su = new SceneCodeBuilder(sceneInternal);
        return su.build();
    }

}
