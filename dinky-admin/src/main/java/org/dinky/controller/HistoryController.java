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

package org.dinky.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dinky.common.result.ProTableResult;
import org.dinky.common.result.Result;
import org.dinky.model.History;
import org.dinky.service.HistoryService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * HistoryController
 *
 * @since 2021/6/26 23:09
 */
@Slf4j
@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@Api(value = "HistoryController" , description = "执行历史管理相关接口")
public class HistoryController {

    private final HistoryService historyService;

    /** 动态查询列表 */
    @PostMapping
    @ApiOperation(value = "执行历史查询(分页)" , notes = "执行历史查询(分页)")
    public ProTableResult<History> listHistory(@RequestBody JsonNode para) {
        return historyService.selectForProTable(para);
    }

    /** 批量删除 */
    @DeleteMapping
    @ApiOperation(value = "批量删除" , notes = "批量删除")
    public Result<Void> deleteMul(@RequestBody JsonNode para) {
        if (para.size() > 0) {
            List<Integer> error = new ArrayList<>();
            for (final JsonNode item : para) {
                Integer id = item.asInt();
                if (!historyService.removeHistoryById(id)) {
                    error.add(id);
                }
            }
            if (error.size() == 0) {
                return Result.succeed("删除成功");
            } else {
                return Result.succeed(
                        "删除部分成功，但" + error.toString() + "删除失败，共" + error.size() + "次失败。");
            }
        } else {
            return Result.failed("请选择要删除的记录");
        }
    }

    /** 获取指定ID的信息 */
    @PostMapping("/getOneById")
    @ApiOperation(value = "获取指定ID的信息" , notes = "获取指定ID的信息")
    public Result<History> getOneById(@RequestBody History history) throws Exception {
        history = historyService.getById(history.getId());
        return Result.succeed(history, "获取成功");
    }
}
