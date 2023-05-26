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
import org.dinky.model.Cluster;
import org.dinky.model.JobInstance;
import org.dinky.service.ClusterService;
import org.dinky.service.JobInstanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
 * ClusterController
 *
 * @since 2021/5/28 14:03
 */
@Slf4j
@RestController
@RequestMapping("/api/cluster")
@RequiredArgsConstructor
@Api(value = "ClusterController" , description = "Flink实例管理相关接口")
public class ClusterController {

    private final ClusterService clusterService;
    private final JobInstanceService jobInstanceService;

    /** 新增或者更新 */
    @PutMapping
    @ApiOperation(value = "新增或更新Flink实例" , notes = "新增或更新Flink实例")
    public Result<Void> saveOrUpdate(@RequestBody Cluster cluster) throws Exception {
        cluster.setAutoRegisters(false);
        Integer id = cluster.getId();
        clusterService.registersCluster(cluster);
        return Result.succeed(Asserts.isNotNull(id) ? "修改成功" : "新增成功");
    }

    /** 启用和禁用 */
    @PutMapping("/enable")
    @ApiOperation(value = "启用或禁用Flink实例" , notes = "启用或禁用Flink实例")
    public Result<Void> enableCluster(@RequestBody Cluster cluster) {
        clusterService.enableCluster(cluster);
        return Result.succeed("修改成功");
    }

    /** 动态查询列表 */
    @PostMapping
    @ApiOperation(value = "动态查询Flink实例(分页)" , notes = "动态查询Flink实例(分页)")
    public ProTableResult<Cluster> listClusters(@RequestBody JsonNode para) {
        return clusterService.selectForProTable(para);
    }

    /** 批量删除 */
    @DeleteMapping
    @ApiOperation(value = "批量删除Flink实例" , notes = "批量删除Flink实例")
    public Result<Void> deleteMul(@RequestBody JsonNode para) {
        if (para.size() > 0) {
            List<JobInstance> instances = jobInstanceService.listJobInstanceActive();
            Set<Integer> ids =
                    instances.stream().map(JobInstance::getClusterId).collect(Collectors.toSet());
            List<String> error = new ArrayList<>();
            for (final JsonNode item : para) {
                Integer id = item.asInt();
                if (ids.contains(id) || !clusterService.removeById(id)) {
                    error.add(clusterService.getById(id).getName());
                }
            }
            if (error.size() == 0) {
                return Result.succeed("删除成功");
            } else {
                if (para.size() > error.size()) {
                    return Result.succeed(
                            "删除部分成功，但"
                                    + error
                                    + "删除失败，共"
                                    + error.size()
                                    + "次失败。\n请检查集群实例是否已被集群使用！");
                } else {
                    return Result.succeed(
                            error + "删除失败，共" + error.size() + "次失败。\n请检查集群实例是否已被集群使用！");
                }
            }
        } else {
            return Result.failed("请选择要删除的记录");
        }
    }

    /** 获取指定ID的信息 */
    @PostMapping("/getOneById")
    @ApiOperation(value = "根据id获取Flink实例" , notes = "根据id获取Flink实例")
    public Result<Cluster> getOneById(@RequestBody Cluster cluster) throws Exception {
        cluster = clusterService.getById(cluster.getId());
        return Result.succeed(cluster, "获取成功");
    }

    /** 获取可用的集群列表 */
    @GetMapping("/listEnabledAll")
    @ApiOperation(value = "获取所有存活的Flink实例(不分页)" , notes = "获取所有存活的Flink实例(不分页)")
    public Result<List<Cluster>> listEnabledAll() {
        List<Cluster> clusters = clusterService.listEnabledAll();
        return Result.succeed(clusters, "获取成功");
    }

    /** 获取可用的集群列表 */
    @GetMapping("/listSessionEnable")
    @ApiOperation(value = "获取自定义Flink实例列表(不分页)" , notes = "获取自定义Flink实例列表(不分页)")
    public Result<List<Cluster>> listSessionEnable() {
        List<Cluster> clusters = clusterService.listSessionEnable();
        return Result.succeed(clusters, "获取成功");
    }

    /** 全部心跳监测 */
    @PostMapping("/heartbeats")
    @ApiOperation(value = "监听所有Flink实例心跳" , notes = "监听所有Flink实例心跳")
    public Result<Void> heartbeat() {
        List<Cluster> clusters = clusterService.listEnabledAll();
        for (Cluster cluster : clusters) {
            clusterService.registersCluster(cluster);
        }
        return Result.succeed("状态刷新完成");
    }

    /** 回收过期集群 */
    @GetMapping("/clear")
    @ApiOperation(value = "回收过期Flink实例" , notes = "回收过期Flink实例")
    public Result<Integer> clear() {
        return Result.succeed(clusterService.clearCluster(), "回收完成");
    }

    /** 停止集群 */
    @GetMapping("/killCluster")
    @ApiOperation(value = "根据id停止Flink实例" , notes = "根据id停止Flink实例")
    public Result<Void> killCluster(@RequestParam("id") Integer id) {
        clusterService.killCluster(id);
        return Result.succeed("Kill Cluster Succeed.");
    }

    /** 批量停止 */
    @DeleteMapping("/killMulCluster")
    @ApiOperation(value = "批量停止Flink实例" , notes = "批量停止Flink实例")
    public Result<Void> killMulCluster(@RequestBody JsonNode para) {
        if (para.size() > 0) {
            for (final JsonNode item : para) {
                clusterService.killCluster(item.asInt());
            }
        }
        return Result.succeed("Kill cluster succeed.");
    }

    /** 启动 Session 集群 */
    @GetMapping("/deploySessionCluster")
    @ApiOperation(value = "格局id启动 Session 集群" , notes = "格局id启动 Session 集群")
    public Result<Cluster> deploySessionCluster(@RequestParam("id") Integer id) {
        return Result.succeed(
                clusterService.deploySessionCluster(id), "Deploy session cluster succeed.");
    }
}
