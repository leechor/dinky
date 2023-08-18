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

package com.zdpx.controller;

import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.operator.FieldFunction;
import com.zdpx.model.FunctionManagement;
import org.dinky.data.model.Task;
import org.dinky.data.result.Result;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.zdpx.service.TaskFlowGraphService;

import lombok.extern.slf4j.Slf4j;

/** */
@Slf4j
@RestController
@RequestMapping("/api/zdpx")
public class TaskFlowGraphController {

    @Autowired
    private TaskFlowGraphService taskFlowGraphService;

    @PutMapping
    public Result<List<CheckInformationModel>> submitSql(@RequestBody Task task) {
        taskFlowGraphService.updateTaskFlowGraph(task);//暂存
        List<CheckInformationModel> msg = taskFlowGraphService.saveOrUpdateTask(task);
        if (!msg.isEmpty()) {
            return Result.succeed(msg);
        } else {
            return Result.succeed("保存成功，并且无报错信息");
        }
    }

    @PutMapping("testGraphSql")
    public Result<Void> testGraphStatement(@RequestBody String graph) {
        String sql = taskFlowGraphService.testGraphStatement(graph);
        return Result.succeed();
    }

    @GetMapping("/operatorConfigure")
    public Result<List<JsonNode>> getOperatorConfigurations() {
        List<JsonNode> configurations = taskFlowGraphService.getOperatorConfigurations();
        if (configurations == null || configurations.isEmpty()) {
            return Result.failed("get configuration failed");
        }
        return Result.succeed(configurations);
    }

    //通用算子处理类，入参：当前算子的json，需要处理的方法。 返回处理结果。 包含功能：预览、函数转换
    @PostMapping("/operator/generalProcess/{operate}")
    public Result<Object> generalProcess(@RequestBody String graph, @PathVariable String operate) {
        return Result.succeed(taskFlowGraphService.generalProcess(graph, operate));
    }

    @PutMapping("/preview")
    public Result<String> operatorPreview(@RequestBody String graph) {
        String s = taskFlowGraphService.operatorPreview(graph);
        return Result.data(s);
    }

    @PostMapping("/getFunction")
    public Result<List<String>> getFunction(@RequestBody Map<String,List<Map<String,Object>>>  columns) {

        List<String> lists = new ArrayList<>();

        List<Map<String, Object>> column = columns.get("columns");

        if(column.get(0).get("function")==null){//是一个源算子
            lists.addAll(column.stream().map(i-> i.get("name").toString()).collect(Collectors.toList()));
        }else{//是一个中间算子
            List<FieldFunction> fieldFunctions = FieldFunction.analyzeParameters("", column, false, null, null);
            lists.addAll(fieldFunctions.stream().map(FieldFunction::getFunctionName).collect(Collectors.toList()));
        }

        return Result.succeed(lists);
    }

    @GetMapping("/getFillAllByVersion")
    public Result<List<String>>  getFillAllByVersion(){
        FunctionManagement[] values = FunctionManagement.values();
        List<String> collect = Arrays.stream(values).map(FunctionManagement::getFunctionName).collect(Collectors.toList());
        return Result.succeed(collect);
    }

}
