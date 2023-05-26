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
import org.dinky.assertion.Asserts;
import org.dinky.common.result.ProTableResult;
import org.dinky.common.result.Result;
import org.dinky.constant.CommonConstant;
import org.dinky.metadata.driver.DriverPool;
import org.dinky.metadata.result.JdbcSelectResult;
import org.dinky.model.Column;
import org.dinky.model.DataBase;
import org.dinky.model.QueryData;
import org.dinky.model.Schema;
import org.dinky.model.SqlGeneration;
import org.dinky.service.DataBaseService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DataBaseController
 *
 * @since 2021/7/20 23:48
 */
@Slf4j
@RestController
@RequestMapping("/api/database")
@RequiredArgsConstructor
@Api(value = "DataBaseController" , description = "数据源管理相关接口")
public class DataBaseController {

    private final DataBaseService databaseService;

    /** 新增或者更新 */
    @PutMapping
    @ApiOperation(value = "新增或更新数据源" , notes = "新增或更新数据源")
    public Result<Void> saveOrUpdate(@RequestBody DataBase database) {
        if (databaseService.saveOrUpdateDataBase(database)) {
            DriverPool.remove(database.getName());
            return Result.succeed("更新成功");
        } else {
            return Result.failed("更新失败");
        }
    }

    /** 动态查询列表 */
    @PostMapping
    @ApiOperation(value = "动态查询数据源列表(分页)" , notes = "动态查询数据源列表(分页)")
    public ProTableResult<DataBase> listDataBases(@RequestBody JsonNode para) {
        return databaseService.selectForProTable(para);
    }

    /** 批量删除 */
    @DeleteMapping
    @ApiOperation(value = "批量删除数据源" , notes = "批量删除数据源")
    public Result<Void> deleteMul(@RequestBody JsonNode para) {
        if (para.size() > 0) {
            List<Integer> error = new ArrayList<>();
            for (final JsonNode item : para) {
                Integer id = item.asInt();
                if (!databaseService.removeById(id)) {
                    error.add(id);
                }
            }
            if (error.size() == 0) {
                return Result.succeed("删除成功");
            } else {
                return Result.succeed("删除部分成功，但" + error + "删除失败，共" + error.size() + "次失败。");
            }
        } else {
            return Result.failed("请选择要删除的记录");
        }
    }

    /** 获取指定ID的信息 */
    @PostMapping("/getOneById")
    @ApiOperation(value = "获取指定id的数据源信息" , notes = "获取指定id的数据源信息")
    public Result<DataBase> getOneById(@RequestBody DataBase database) {
        database = databaseService.getById(database.getId());
        return Result.succeed(database, "获取成功");
    }

    /** 获取可用的数据库列表 */
    @GetMapping("/listEnabledAll")
    @ApiOperation(value = "获取可用的数据库列表" , notes = "获取可用的数据库列表")
    public Result<List<DataBase>> listEnabledAll() {
        List<DataBase> dataBases = databaseService.listEnabledAll();
        return Result.succeed(dataBases, "获取成功");
    }

    /** 连接测试 */
    @PostMapping("/testConnect")
    @ApiOperation(value = "数据源连接测试" , notes = "数据源连接测试")
    public Result<Void> testConnect(@RequestBody DataBase database) {
        String msg = databaseService.testConnect(database);
        boolean isHealthy = Asserts.isEquals(CommonConstant.HEALTHY, msg);
        if (isHealthy) {
            return Result.succeed("数据源连接测试成功!");
        } else {
            return Result.failed(msg);
        }
    }

    /** 全部心跳监测 */
    @PostMapping("/checkHeartBeats")
    @ApiOperation(value = "全部数据源的心跳监测" , notes = "全部数据源的心跳监测")
    public Result<Void> checkHeartBeats() {
        List<DataBase> dataBases = databaseService.listEnabledAll();
        for (DataBase dataBase : dataBases) {
            try {
                databaseService.checkHeartBeat(dataBase);
            } finally {
                databaseService.updateById(dataBase);
            }
        }
        return Result.succeed("状态刷新完成");
    }

    /** 心跳检测指定ID */
    @GetMapping("/checkHeartBeatById")
    @ApiOperation(value = "监测指定id的数据源心跳" , notes = "监测指定id的数据源心跳")
    public Result<Void> checkHeartBeatById(@RequestParam Integer id) {
        DataBase dataBase = databaseService.getById(id);
        Asserts.checkNotNull(dataBase, "该数据源不存在！");
        String error = "";
        try {
            databaseService.checkHeartBeat(dataBase);
        } catch (Exception e) {
            error = e.getMessage();
        }
        databaseService.updateById(dataBase);
        if (Asserts.isNotNullString(error)) {
            return Result.failed(error);
        }
        return Result.succeed("数据源连接正常");
    }

    /** 获取元数据的表 */
    @Cacheable(cacheNames = "metadata_schema", key = "#id")
    @GetMapping("/getSchemasAndTables")
    @ApiOperation(value = "获取元数据的表" , notes = "获取元数据的表")
    public Result<List<Schema>> getSchemasAndTables(@RequestParam Integer id) {
        return Result.succeed(databaseService.getSchemasAndTables(id), "获取成功");
    }

    /** 清除元数据表的缓存 */
    @CacheEvict(cacheNames = "metadata_schema", key = "#id")
    @GetMapping("/unCacheSchemasAndTables")
    @ApiOperation(value = "清除元数据表的缓存" , notes = "清除元数据表的缓存")
    public Result<String> unCacheSchemasAndTables(@RequestParam Integer id) {
        return Result.succeed("clear cache", "success");
    }

    /** 获取元数据的指定表的列 */
    @GetMapping("/listColumns")
    @ApiOperation(value = "获取元数据的指定表的列" , notes = "获取元数据的指定表的列")
    public Result<List<Column>> listColumns(
            @RequestParam Integer id,
            @RequestParam String schemaName,
            @RequestParam String tableName) {
        return Result.succeed(databaseService.listColumns(id, schemaName, tableName), "获取成功");
    }

    /** 获取元数据的指定表的数据 */
    @PostMapping("/queryData")
    @ApiOperation(value = "获取元数据的指定表的数据" , notes = "获取元数据的指定表的数据")
    public Result<JdbcSelectResult> queryData(@RequestBody QueryData queryData) {
        JdbcSelectResult jdbcSelectResult = databaseService.queryData(queryData);
        if (jdbcSelectResult.isSuccess()) {
            return Result.succeed(jdbcSelectResult, "获取成功");
        } else {
            return Result.failed(jdbcSelectResult, "查询失败");
        }
    }

    /** 执行sql */
    @PostMapping("/execSql")
    @ApiOperation(value = "执行sql" , notes = "执行sql")
    public Result<JdbcSelectResult> execSql(@RequestBody QueryData queryData) {
        JdbcSelectResult jdbcSelectResult = databaseService.execSql(queryData);
        if (jdbcSelectResult.isSuccess()) {
            return Result.succeed(jdbcSelectResult, "获取成功");
        } else {
            return Result.failed(jdbcSelectResult, "查询失败");
        }
    }

    /** 获取 SqlGeneration */
    @GetMapping("/getSqlGeneration")
    @ApiOperation(value = "获取指定的 SqlGeneration" , notes = "获取指定的 SqlGeneration")
    public Result<SqlGeneration> getSqlGeneration(
            @RequestParam Integer id,
            @RequestParam String schemaName,
            @RequestParam String tableName) {
        return Result.succeed(databaseService.getSqlGeneration(id, schemaName, tableName), "获取成功");
    }

    /** copyDatabase */
    @PostMapping("/copyDatabase")
    @ApiOperation(value = "复制数据源信息" , notes = "复制数据源信息")
    public Result<Void> copyDatabase(@RequestBody DataBase database) {
        if (databaseService.copyDatabase(database)) {
            return Result.succeed("复制成功!");
        } else {
            return Result.failed("复制失败！");
        }
    }
}
