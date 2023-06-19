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

import org.dinky.data.dto.TaskRollbackVersionDTO;
import org.dinky.data.enums.JobLifeCycle;
import org.dinky.data.enums.JobStatus;
import org.dinky.data.enums.TaskOperatingSavepointSelect;
import org.dinky.data.model.Task;
import org.dinky.data.result.ProTableResult;
import org.dinky.data.result.Result;
import org.dinky.function.pool.UdfCodePool;
import org.dinky.job.JobResult;
import org.dinky.service.TaskService;
import org.dinky.utils.TaskOneClickOperatingUtil;
import org.dinky.utils.UDFUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务 Controller
 *
 * @since 2021-05-24
 */
@Slf4j
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /** 新增或者更新 */
    @PutMapping
    public Result<Void> saveOrUpdate(@RequestBody Task task) throws Exception {
        if (taskService.saveOrUpdateTask(task)) {
            return Result.succeed("保存成功");
        } else {
            return Result.failed("保存失败");
        }
    }

    /** 动态查询列表 */
    @PostMapping
    public ProTableResult<Task> listTasks(@RequestBody JsonNode para) {
        return taskService.selectForProTable(para);
    }

    /** 批量删除 */
    @DeleteMapping
    public Result<Void> deleteMul(@RequestBody JsonNode para) {
        if (para.size() > 0) {
            boolean isAdmin = false;
            List<Integer> error = new ArrayList<>();
            for (final JsonNode item : para) {
                Integer id = item.asInt();
                if (!taskService.removeById(id)) {
                    error.add(id);
                }
            }
            CompletableFuture.runAsync(
                    () ->
                            UdfCodePool.registerPool(
                                    taskService.getAllUDF().stream()
                                            .map(UDFUtils::taskToUDF)
                                            .collect(Collectors.toList())));
            if (error.size() == 0 && !isAdmin) {
                return Result.succeed("删除成功");
            } else {
                return Result.succeed("删除部分成功，但" + error + "删除失败，共" + error.size() + "次失败。");
            }
        } else {
            return Result.failed("请选择要删除的记录");
        }
    }

    /** 批量执行 */
    @PostMapping(value = "/submit")
    public Result<List<JobResult>> submit(@RequestBody JsonNode para) {
        if (para.size() > 0) {
            List<JobResult> results = new ArrayList<>();
            List<Integer> error = new ArrayList<>();
            for (final JsonNode item : para) {
                Integer id = item.asInt();
                JobResult result = taskService.submitTask(id);
                if (!result.isSuccess()) {
                    error.add(id);
                }
                results.add(result);
            }
            if (error.size() == 0) {
                return Result.succeed(results, "执行成功");
            } else {
                return Result.succeed(
                        results, "执行部分成功，但" + error + "执行失败，共" + error.size() + "次失败。");
            }
        } else {
            return Result.failed("请选择要执行的记录");
        }
    }

    /** 获取指定ID的信息 */
    @GetMapping
    public Result<Task> getOneById(@RequestParam Integer id) {
        Task task = taskService.getTaskInfoById(id);
//        if(id.equals(90)){
//            String str =
//                    "{\"cells\":[{\"position\":{\"x\":30,\"y\":180},\"size\":{\"width\":70,\"height\":50},\"view\":\"react-shape-view\",\"attrs\":{\"body\":{\"rx\":7,\"ry\":6},\"text\":{\"text\":\"MysqlSourceOperator\",\"fontSize\":2}},\"shape\":\"MysqlSourceOperator\",\"ports\":{\"groups\":{\"outputs\":{\"zIndex\":1,\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":4,\"magnet\":true,\"stroke\":\"#818181\",\"strokeWidth\":1,\"fill\":\"#b2a2e9\",\"style\":{\"visibility\":\"hidden\"}}}},\"inputs\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":4,\"magnet\":true,\"stroke\":\"#818181\",\"strokeWidth\":1,\"fill\":\"#915dac\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"outputs\",\"id\":\"output_0\",\"attrs\":{\"text\":{\"text\":\"output_0\",\"style\":{\"visibility\":\"hidden\",\"fontSize\":10,\"fill\":\"#3B4351\"}},\"circle\":{\"style\":{\"visibility\":\"hidden\"}}},\"label\":{\"position\":\"bottom\"}}]},\"id\":\"79d1b140-21df-4d7e-b062-71095ed84ad7\",\"zIndex\":1,\"data\":{\"parameters\":[{\"tableName\":\"test1\",\"connector\":\"source\",\"url\":\"url123\",\"username\":\"root\",\"password\":\"123456\",\"columns\":[{\"name\":\"id\",\"type\":\"STRING\"},{\"name\":\"taskId\",\"type\":\"STRING\"},{\"name\":\"longitude\",\"type\":\"STRING\"},{\"name\":\"task_time\",\"type\":\"TIMESTAMP\"},{\"name\":\"gbu_time\",\"type\":\"TIMESTAMP\"}]}]}},{\"position\":{\"x\":250,\"y\":180},\"size\":{\"width\":70,\"height\":50},\"view\":\"react-shape-view\",\"attrs\":{\"body\":{\"rx\":7,\"ry\":6},\"text\":{\"text\":\"MysqlSinkOperator\",\"fontSize\":2}},\"shape\":\"MysqlSinkOperator\",\"ports\":{\"groups\":{\"outputs\":{\"zIndex\":1,\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":4,\"magnet\":true,\"stroke\":\"#818181\",\"strokeWidth\":1,\"fill\":\"#b2a2e9\",\"style\":{\"visibility\":\"hidden\"}}}},\"inputs\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":4,\"magnet\":true,\"stroke\":\"#818181\",\"strokeWidth\":1,\"fill\":\"#915dac\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"inputs\",\"id\":\"input_0\",\"zIndex\":10,\"attrs\":{\"text\":{\"text\":\"input_0\",\"style\":{\"visibility\":\"hidden\",\"fontSize\":10,\"fill\":\"#3B4351\"}},\"circle\":{\"style\":{\"visibility\":\"hidden\"}}},\"label\":{\"position\":\"bottom\"}}]},\"id\":\"1ad7ef69-ba33-4997-88d9-51183432c50b\",\"zIndex\":2,\"data\":{\"parameters\":[{\"tableName\":\"test2\",\"connector\":\"sink\",\"url\":\"url123\",\"username\":\"root\",\"password\":\"123456\",\"columns\":[{\"name\":\"id\",\"type\":\"STRING\"}]}]}},{\"position\":{\"x\":132,\"y\":180},\"size\":{\"width\":70,\"height\":50},\"view\":\"react-shape-view\",\"attrs\":{\"body\":{\"rx\":7,\"ry\":6},\"text\":{\"text\":\"CommWindowFunctionOperator\",\"fontSize\":2}},\"shape\":\"CommWindowFunctionOperator\",\"ports\":{\"groups\":{\"outputs\":{\"zIndex\":1,\"position\":\"right\",\"attrs\":{\"circle\":{\"r\":4,\"magnet\":true,\"stroke\":\"#818181\",\"strokeWidth\":1,\"fill\":\"#b2a2e9\",\"style\":{\"visibility\":\"hidden\"}}}},\"inputs\":{\"position\":\"left\",\"attrs\":{\"circle\":{\"r\":4,\"magnet\":true,\"stroke\":\"#818181\",\"strokeWidth\":1,\"fill\":\"#915dac\",\"style\":{\"visibility\":\"hidden\"}}}}},\"items\":[{\"group\":\"inputs\",\"id\":\"input_0\",\"zIndex\":10,\"attrs\":{\"text\":{\"text\":\"input_0\",\"style\":{\"visibility\":\"hidden\",\"fontSize\":10,\"fill\":\"#3B4351\"}},\"circle\":{\"style\":{\"visibility\":\"hidden\"}}},\"label\":{\"position\":\"bottom\"}},{\"group\":\"outputs\",\"id\":\"output_0\",\"attrs\":{\"text\":{\"text\":\"output_0\",\"style\":{\"visibility\":\"hidden\",\"fontSize\":10,\"fill\":\"#3B4351\"}},\"circle\":{\"style\":{\"visibility\":\"hidden\"}}},\"label\":{\"position\":\"bottom\"}}]},\"id\":\"0b2148e2-a81c-4ff4-8d80-76ce5c950d1c\",\"zIndex\":3,\"data\":{\"parameters\":[{\"partition\":\"taskId\",\"order\":\"task_time\",\"group\":{\"aggregation\":\"group\",\"column\":\"c1\",\"windowsStart\":\"\",\"windowEnd\":\"\"},\"where\":\"task_time>now()\",\"window\":{\"windowFunction\":\"TUMBLE\",\"table\":\"windowTable\",\"descriptor\":\"gbuStartTime\",\"size\":{\"timeSpan\":0,\"timeUnit\":\"SECOND\"},\"slide\":{\"timeSpan\":\"\",\"timeUnit\":\"SECOND\"},\"step\":{\"timeSpan\":10,\"timeUnit\":\"SECOND\"}},\"columns\":[{\"functionName\":\"DISTINDT\",\"name\":\"startTaskStatus\",\"parameters\":[\"A.taskStatus\"]},{\"functionName\":\"endTaskStatus\",\"name\":\"LAST\",\"parameters\":[\"taskStatus\"]},{\"functionName\":\"gbuStartTime\",\"name\":\"FIRST\",\"parameters\":[\"gbu_time1\",\"gbu_time2\"]},{\"functionName\":\"va\",\"name\":\"LAST\",\"parameters\":[\"A.va\"]}]}]}},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#b2a2e9\",\"targetMarker\":{\"name\":\"classic\",\"size\":10}}},\"id\":\"41b0c8ce-c011-4e24-abb9-447216898e11\",\"source\":{\"cell\":\"79d1b140-21df-4d7e-b062-71095ed84ad7\",\"port\":\"output_0\"},\"target\":{\"cell\":\"0b2148e2-a81c-4ff4-8d80-76ce5c950d1c\",\"port\":\"input_0\"},\"zIndex\":4},{\"shape\":\"edge\",\"attrs\":{\"line\":{\"stroke\":\"#b2a2e9\",\"targetMarker\":{\"name\":\"classic\",\"size\":10}}},\"id\":\"1252b16e-d754-4cc3-90be-2220f1ab579a\",\"source\":{\"cell\":\"0b2148e2-a81c-4ff4-8d80-76ce5c950d1c\",\"port\":\"output_0\"},\"target\":{\"cell\":\"1ad7ef69-ba33-4997-88d9-51183432c50b\",\"port\":\"input_0\"},\"zIndex\":5}]}";
//            task.setGraphJson(str);
//        }
        return Result.succeed(task, "获取成功");
    }

    /** 获取所有可用的 FlinkSQLEnv */
    @GetMapping(value = "/listFlinkSQLEnv")
    public Result<List<Task>> listFlinkSQLEnv() {
        return Result.succeed(taskService.listFlinkSQLEnv(), "获取成功");
    }

    /** 导出 sql */
    @GetMapping(value = "/exportSql")
    public Result<String> exportSql(@RequestParam Integer id) {
        return Result.succeed(taskService.exportSql(id), "获取成功");
    }

    /** 发布任务 */
    @GetMapping(value = "/releaseTask")
    public Result<Void> releaseTask(@RequestParam Integer id) {
        return taskService.releaseTask(id);
    }

    @PostMapping("/rollbackTask")
    public Result<Void> rollbackTask(@RequestBody TaskRollbackVersionDTO dto) {
        return taskService.rollbackTask(dto);
    }

    /** 维护任务 */
    @GetMapping(value = "/developTask")
    public Result<Boolean> developTask(@RequestParam Integer id) {
        return Result.succeed(taskService.developTask(id), "操作成功");
    }

    /** 上线任务 */
    @GetMapping(value = "/onLineTask")
    public Result<JobResult> onLineTask(@RequestParam Integer id) {
        return taskService.onLineTask(id);
    }

    /** 下线任务 */
    @GetMapping(value = "/offLineTask")
    public Result<Void> offLineTask(@RequestParam Integer id, @RequestParam String type) {
        return taskService.offLineTask(id, type);
    }

    /** 注销任务 */
    @GetMapping(value = "/cancelTask")
    public Result<Void> cancelTask(@RequestParam Integer id) {
        return taskService.cancelTask(id);
    }

    /** 恢复任务 */
    @GetMapping(value = "/recoveryTask")
    public Result<Boolean> recoveryTask(@RequestParam Integer id) {
        return Result.succeed(taskService.recoveryTask(id), "操作成功");
    }

    /** 重启任务 */
    @GetMapping(value = "/restartTask")
    public Result<JobResult> restartTask(@RequestParam Integer id, @RequestParam Boolean isOnLine) {
        if (isOnLine) {
            return taskService.reOnLineTask(id, null);
        } else {
            return Result.succeed(taskService.restartTask(id, null), "重启成功");
        }
    }

    /** 选择保存点重启任务 */
    @GetMapping(value = "/selectSavePointRestartTask")
    public Result<JobResult> selectSavePointRestartTask(
            @RequestParam Integer id,
            @RequestParam Boolean isOnLine,
            @RequestParam String savePointPath) {
        if (isOnLine) {
            return taskService.reOnLineTask(id, savePointPath);
        } else {
            return Result.succeed(taskService.restartTask(id, savePointPath), "重启成功");
        }
    }

    /** 获取当前的 API 的地址 */
    @GetMapping(value = "/getTaskAPIAddress")
    public Result<String> getTaskAPIAddress() {
        return Result.succeed(taskService.getTaskAPIAddress(), "重启成功");
    }

    /** 导出json */
    @GetMapping(value = "/exportJsonByTaskId")
    public Result<String> exportJsonByTaskId(@RequestParam Integer id) {
        return Result.succeed(taskService.exportJsonByTaskId(id), "获取成功");
    }

    /** 导出json数组 */
    @PostMapping(value = "/exportJsonByTaskIds")
    public Result<String> exportJsonByTaskIds(@RequestBody JsonNode para) {
        return Result.succeed(taskService.exportJsonByTaskIds(para), "获取成功");
    }

    /** json文件上传 导入task */
    @PostMapping(value = "/uploadTaskJson")
    public Result<Void> uploadTaskJson(@RequestParam("file") MultipartFile file) throws Exception {
        return taskService.uploadTaskJson(file);
    }

    /**
     * 查询所有目录
     *
     * @return {@link Result}<{@link Tree}<{@link Integer}>>
     */
    @GetMapping("/queryAllCatalogue")
    public Result<Tree<Integer>> queryAllCatalogue() {
        return taskService.queryAllCatalogue();
    }

    /**
     * 查询对应操作的任务列表
     *
     * @param operating 操作
     * @param catalogueId 目录id
     * @return {@link Result}<{@link List}<{@link Task}>>
     */
    @GetMapping("/queryOnClickOperatingTask")
    public Result<List<Task>> queryOnClickOperatingTask(
            @RequestParam("operating") Integer operating,
            @RequestParam("catalogueId") Integer catalogueId) {
        if (operating == null) {
            return Result.failed("操作不正确");
        }
        switch (operating) {
            case 1:
                return taskService.queryOnLineTaskByDoneStatus(
                        Collections.singletonList(JobLifeCycle.RELEASE),
                        JobStatus.getAllDoneStatus(),
                        true,
                        catalogueId);
            case 2:
                return taskService.queryOnLineTaskByDoneStatus(
                        Collections.singletonList(JobLifeCycle.ONLINE),
                        Collections.singletonList(JobStatus.RUNNING),
                        false,
                        catalogueId);
            default:
                return Result.failed("操作不正确");
        }
    }

    /**
     * 一键操作任务
     *
     * @param operating 操作
     * @return {@link Result}<{@link Void}>
     */
    @PostMapping("/onClickOperatingTask")
    public Result<Void> onClickOperatingTask(@RequestBody JsonNode operating) {
        if (operating == null || operating.get("operating") == null) {
            return Result.failed("操作不正确");
        }
        switch (operating.get("operating").asInt()) {
            case 1:
                final JsonNode savepointSelect = operating.get("taskOperatingSavepointSelect");
                return TaskOneClickOperatingUtil.oneClickOnline(
                        TaskOneClickOperatingUtil.parseJsonNode(operating),
                        TaskOperatingSavepointSelect.valueByCode(
                                savepointSelect == null ? 0 : savepointSelect.asInt()));
            case 2:
                return TaskOneClickOperatingUtil.onClickOffline(
                        TaskOneClickOperatingUtil.parseJsonNode(operating));
            default:
                return Result.failed("操作不正确");
        }
    }

    /**
     * 查询一键操作任务状态
     *
     * @return {@link Result}<{@link Dict}>
     */
    @GetMapping("/queryOneClickOperatingTaskStatus")
    public Result<Dict> queryOneClickOperatingTaskStatus() {
        return TaskOneClickOperatingUtil.queryOneClickOperatingTaskStatus();
    }
}
