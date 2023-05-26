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
import org.dinky.common.result.Result;
import org.dinky.constant.DirConstant;
import org.dinky.model.FileNode;
import org.dinky.service.SystemService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * SystemController
 *
 * @since 2022/10/15 19:20
 */
@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
@Api(value = "/api/system", tags = "文件和日志读取接口")
public class SystemController {

    private final SystemService systemService;

    @GetMapping("/listLogDir")
    @ApiOperation(value = "从根目录下加载文件节点", notes = "从根目录下加载文件节点")
    public Result<List<FileNode>> listLogDir() {
        return Result.data(systemService.listLogDir());
    }

    @GetMapping("/getRootLog")
    @ApiOperation(value = "从日志根目录下读取文件", notes = "从日志根目录下读取文件")
    public Result<String> getRootLog() {
        return Result.data(systemService.readFile(DirConstant.ROOT_LOG_PATH));
    }

    @GetMapping("/listDirByPath")
    @ApiOperation(value = "返回指定路径所有文件节点", notes = "返回指定路径所有文件节点")
    public Result<List<FileNode>> listDirByPath(@RequestParam String path) {
        return Result.data(systemService.listDirByPath(path));
    }

    @GetMapping("/readFile")
    @ApiOperation(value = "读取指定路径所有文件", notes = "读取指定路径所有文件")
    public Result<String> readFile(@RequestParam String path) {
        return Result.data(systemService.readFile(path));
    }
}
