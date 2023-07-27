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
import org.dinky.data.model.Task;
import org.dinky.data.result.Result;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.zdpx.service.TaskFlowGraphService;

import lombok.extern.slf4j.Slf4j;

/** */
@Slf4j
@RestController
@RequestMapping("/api/zdpx")
public class TaskFlowGraphController {

    private final TaskFlowGraphService taskFlowGraphService;

    public TaskFlowGraphController(TaskFlowGraphService taskFlowGraphService) {
        this.taskFlowGraphService = taskFlowGraphService;
    }

    @PutMapping
    public Result<List<CheckInformationModel>> submitSql(@RequestBody Task task) {
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

    @PutMapping("/preview")
    public Result<String> operatorPreview(@RequestBody String graph) {
        String s = taskFlowGraphService.operatorPreview(graph);
        return Result.data(s);
    }

}
