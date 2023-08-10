/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.zdpx.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdpx.coder.SceneCode;
import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.json.preview.OperatorPreviewBuilder;
import com.zdpx.mapper.CustomerOperatorMapper;
import com.zdpx.model.CustomerOperator;
import org.dinky.data.dto.StudioExecuteDTO;
import org.dinky.data.model.Statement;
import org.dinky.data.model.Task;
import org.dinky.data.result.SqlExplainResult;
import org.dinky.mybatis.service.impl.SuperServiceImpl;
import org.dinky.service.StatementService;
import org.dinky.service.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dinky.service.impl.StudioServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.zdpx.coder.SceneCodeBuilder;
import com.zdpx.coder.graph.Scene;
import com.zdpx.coder.json.ToInternalConvert;
import com.zdpx.coder.json.x6.X6ToInternalConvert;
import com.zdpx.mapper.FlowGraphScriptMapper;
import com.zdpx.model.FlowGraph;
import com.zdpx.service.TaskFlowGraphService;

import lombok.extern.slf4j.Slf4j;

/** */
@Slf4j
@Service
public class TaskFlowGraphServiceImpl extends SuperServiceImpl<FlowGraphScriptMapper, FlowGraph>
        implements TaskFlowGraphService {

    private final TaskService taskService;
    private final StatementService statementService;

    private final FlowGraphScriptMapper flowGraphScriptMapper;

    private final StudioServiceImpl studioServiceImpl;

    private final CustomerOperatorMapper customerOperatorMapper;

    public TaskFlowGraphServiceImpl(TaskService taskService, StatementService statementService, FlowGraphScriptMapper flowGraphScriptMapper, StudioServiceImpl studioServiceImpl,CustomerOperatorMapper customerOperatorMapper) {
        this.taskService = taskService;
        this.statementService=statementService;
        this.flowGraphScriptMapper = flowGraphScriptMapper;
        this.studioServiceImpl = studioServiceImpl;
        this.customerOperatorMapper=customerOperatorMapper;
    }

    @Override
    public boolean insert(FlowGraph statement) {
        flowGraphScriptMapper.insert(statement);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CheckInformationModel> saveOrUpdateTask(Task task) {
        Map<String, Object> map = convertConfigToSource(task);
        task.setStatement(map.get("SQL").toString());
        taskService.saveOrUpdateTask(task);
        @SuppressWarnings("unchecked")
        List<CheckInformationModel> msg = (List<CheckInformationModel>)map.get("MSG");
        return msg;
    }

    @Override
    public List<JsonNode> getOperatorConfigurations() {
        List<JsonNode> operatorConfigurations = Scene.getOperatorConfigurations();
        customerOperatorMapper.selectList(new QueryWrapper<CustomerOperator>().isNull("delete_time")).forEach(item->{
            ObjectMapper mapper = new ObjectMapper();
            try {
                item.setSpecification(Specifications.readSpecializationFileByClassName("ProcessGroupOperator"));
                operatorConfigurations.add(mapper.readTree(item.toString()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return operatorConfigurations;
    }

    @Override
    public String operatorPreview(String graph) {

        OperatorPreviewBuilder preview = new OperatorPreviewBuilder(graph);

        return preview.operatorPreview();
    }

    @Override
    public String testGraphStatement(String graph) {
        return convertToSql(graph).get("SQL").toString();
    }

    private Map<String, Object> convertConfigToSource(Task task) {

        //更新表dinky_task_statement
        Statement statement = new Statement();
        statement.setId(task.getId());
        Statement old = statementService.selectById(task.getId());
        if(old!=null){
            statement.setStatement(old.getStatement());
            statementService.updateById(statement);
        }else{
            statement.setStatement("");
            statementService.insert(statement);
        }
        //更新表zdpx_task_flow_graph
        String flowGraphScript = task.getStatement();
        FlowGraph flowGraph = new FlowGraph();
        flowGraph.setTaskId(task.getId());
        flowGraph.setScript(flowGraphScript);
        FlowGraph oldGraph = this.getOne(new QueryWrapper<FlowGraph>().eq("task_id", task.getId()));
        if(oldGraph==null){
            this.insert(flowGraph);
        }else{
            this.update(flowGraph,new QueryWrapper<FlowGraph>().eq("task_id", oldGraph.getTaskId()));
        }

        Map<String, Object> checkAndSQL = convertToSql(flowGraphScript);
        String sql = checkAndSQL.get("SQL").toString();

        //sql校验
        StudioExecuteDTO studio = new StudioExecuteDTO().task2DTO(task);
        studio.setStatement(sql);
        List<SqlExplainResult> sqlExplainResults = studioServiceImpl.explainSql(studio);

        @SuppressWarnings("unchecked")
        List<CheckInformationModel> list = (List<CheckInformationModel>)checkAndSQL.get("MSG");

        list.stream().filter(c->c.getTableName()!=null).forEach(c->{
            sqlExplainResults.stream().filter(s->s.getError()!=null).forEach(s->{
                String error = s.getError();
                String substring = error.substring(0, error.indexOf("\n"));
                if (s.getSql().split(" ")[2].contains(c.getTableName())){
                    c.setSqlErrorMsg(substring);
                }else if(s.getSql().contains(c.getTableName())){//特殊算子 ADD JAR 异常参数添加
                    c.setSqlErrorMsg(substring);
                }
            });
        });


        Map<String, Object> map = new HashMap<>();
        map.put("SQL",sql);
        map.put("MSG",list);
        return map;
    }

    public Map<String, Object> convertToSql(String flowGraphScript) {
        List<Task> tasks = taskService.list(new QueryWrapper<Task>().eq("dialect", "Java"));
        Map<String, String> udfDatabase =
                tasks.stream()
                        .collect(
                                Collectors.toMap(
                                        Task::getName,
                                        Task::getSavePointPath,
                                        (existing, replacement) -> replacement));
        Map<String, String> udfAll = new HashMap<>();
        udfAll.putAll(Scene.USER_DEFINED_FUNCTION);
        udfAll.putAll(udfDatabase);

        ToInternalConvert toic = new X6ToInternalConvert();
        Scene sceneInternal = toic.convert(flowGraphScript);
        SceneCode su = new SceneCodeBuilder(sceneInternal);
        su.setUdfFunctionMap(udfAll);
        return su.build();
    }

}
