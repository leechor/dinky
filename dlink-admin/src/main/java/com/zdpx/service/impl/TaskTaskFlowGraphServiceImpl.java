package com.zdpx.service.impl;

import com.dlink.db.service.impl.SuperServiceImpl;
import com.dlink.model.Task;
import com.dlink.service.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdpx.coder.SceneCodeBuilder;
import com.zdpx.coder.graph.Scene;
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
        boolean result = true;
        result &= taskService.saveOrUpdateTask(task);

        FlowGraph flowGraph = new FlowGraph();
        flowGraph.setTaskId(task.getId());
        flowGraph.setScript(flowGraphScript);
        result &= this.saveOrUpdate(flowGraph);
        return result;
    }

    private String convertConfigToSource(String flowGraphScript) {
        List<Task> tasks = taskService.list(new QueryWrapper<Task>().eq("dialect", "Java"));
        Map<String, String> udfDatabase = tasks.stream().collect(Collectors.toMap(Task::getName, Task::getSavePointPath,
            (existing, replacement) -> replacement));
        SceneNode scene = SceneCodeBuilder.readScene(flowGraphScript);
        var sceneInternal = SceneCodeBuilder.convertToInternal(scene);
        Map<String, String> udfAll = new HashMap<>();
        udfAll.putAll(Scene.USER_DEFINED_FUNCTION);
        udfAll.putAll(udfDatabase);
        SceneCodeBuilder su = new SceneCodeBuilder(sceneInternal);
        su.setUdfFunctionMap(udfAll);
        return su.build();
    }

}
